package com.sh.webflux.chapter2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.annotation.Schedules;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class OperatorFlatMapTest {
    /**
     * FlatMap의 역할 아래와 같은 구조에서 Mono에 대한 부분을 제거
     * - Mono<Flux<T>> -> Flux<T>
     * - Mono<Mono<T>> -> Mono<T>
     * - Flux<Mono<T>> -> Flux<T>
     *
     * Mono<Mono<T>> -> Mono<String> 으로 변환
     * Flux<Mono<T>> -> Flux<String> 으로 변환
     * -> 위와 같은 구조는 안에 있는 Mono는 flatMap, merge를 통해서 Mono에 대한 부분을 벗겨낼 수 있다
     * -> 다만 flatMap, merge만 사용하면 순서를 보장하지 않기 때문에 순서를 보장하기 위해서는 flatMapSequential , mergeSequential
     *
     * Mono<Flux<T>> -> Flux<T>
     *     - flatMapMany를 통해서 Mono에 대한 부분을 벗겨 낼 수 있음. 이와 같은 경우에는 sequential을 사용하지 않아도 순서가 보장됨.
     *
     * Flux<Flux<T>>
     *     - 이 경우에도 flatMapMany로 벗겨낼 수 있지만, 맨 앞에 있는 가장 큰 Flux를 이차원 배열의 형태를 유지하고 싶으면 .collectList에서 Flux<Mono<T>> 에 대한 형태로 변경한 뒤 , flatMap을 사용하면 2중 배열의 구조를 유지할 수 있음.
     *
     *
     * Flux, Mono 안에서 외부 API 호출 , DB 호출 등 비동기 작업 흐름을 시작하면 Flux, Mono 안에 Flux, Mono가 중첩된 구조가 형성된다.
     * 모든 Flux, Mono 구조는 flatMap, merge, collectList 등으로 평탄화 할 수 있다.
     */

    @Test
    @DisplayName("")
    void monoToFlux() {
        Mono<Integer> one = Mono.just(1);

        /**
         * 현재 이 구조는 비동기(Mono) + 비동기(Flux)인 구조로 비동기가 2개인 구조로 이루어져있는데 비동기 1개로 평탄화 시켜주는것이 FlatMap이다.
         */
        Flux<Integer> integerFlux = one.flatMapMany(data -> {
            return Flux.just(data, data + 1, data + 2);
        });
        integerFlux.subscribe(data -> System.out.println("data : " + data));
    }

    @Test
    @DisplayName("")
    void testWebClientFlatMap() {
        //just 사용
        Flux<String> stringFlux = Flux.just(
                   callWebClient("1단계 문제 이해하기 ", 1500),
                   callWebClient("2단계 문제 단계별로 풀어가기", 1000),
                   callWebClient("3단계 최종 응답 ", 500))
                .flatMap(monoData -> {
                    return monoData;
                });
//        stringFlux.subscribe(data -> System.out.println("Just Flux Data : " + data));

        /**
         * merge는 just에 대한 코드 보면 flatMap 쪽에 값을 아무런 가공 없이 return 하는 경우에 merge를 사용하면 더 간단하게 코드를 구현할 수 있음.
         * - 스레드에서 merge에 대한 부분을 실행할 때 1, 2, 3 단계에 대한 부분을 한꺼번에 실행하게 됨.
         * mergeSequential은 한꺼번에 실행시키고 , 마지막에 순서를 정렬하는 식으로 설정되어 있음.
         */
        Flux<String> merge = Flux.mergeSequential(
                        callWebClient("1단계 문제 이해하기 ", 1500),
                        callWebClient("2단계 문제 단계별로 풀어가기", 1000),
                        callWebClient("3단계 최종 응답 ", 500));
                        //        .map() : merge()를 사용했지만 map을 사용하여 데이터를 가공하면 flatmap을 사용하는 구조랑 동일함.
        merge.subscribe(data -> System.out.println("merge Data : " + data));

        /**
         * concat에 대한 부분은 merge와 다르게 1단계에 대한 코드가 다 완료되고 , 2단계로 넘어가고, 3단계로 넘어가는 구조로 되어 있음.
         * - 매우 비효율적인 동작
         * - concat , concatMap은 쓰지말자.
         */
        Flux<String> concat = Flux.concat(
                callWebClient("1단계 문제 이해하기 ", 1500),
                callWebClient("2단계 문제 단계별로 풀어가기", 1000),
                callWebClient("3단계 최종 응답 ", 500));


        //create() 사용
        Flux<String> monoFlux = Flux.<Mono<String>>create(sink -> {
            sink.next(callWebClient("1단계 문제 이해하기 ", 1500));
            sink.next(callWebClient("2단계 문제 단계별로 풀어가기", 1000));
            sink.next(callWebClient("3단계 최종 응답 ", 500));
            sink.complete();
        }).flatMapSequential(monodata -> { // flatMapSequential : Mono에 대한 데이터를 가고앟면서 요청한 순서 보장 Sequenctial을 작성하지 않으면 순서 없이 delay가 짧은 순서대로 방출이 됨
            return monodata;
        });

//        monoFlux.subscribe(data -> System.out.println("Process Data : " + data));

        try {
            Thread.sleep(5000);
        } catch (Exception e) {

        }
    }

    public Mono<String> callWebClient(String request , long delay) {
        return Mono.defer(() -> {
            try{
                Thread.sleep(delay);
                return Mono.just(request + " -> Delay Time : " +  delay);
            }catch(Exception e){
                return Mono.empty();
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

}