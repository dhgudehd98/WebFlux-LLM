package com.sh.webflux.chapter2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.util.context.Context;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
public class BasicFluxOperatorTest {

    /**
     * Flux
     * 데이터 : just , empty , from~
     * 함수 : defer , create
     */
    @Test
    @DisplayName("")
    void testFluxFromData() {

        // 자바에서 Iterable을 사용하는 모든 객체들을 사용할 수 있음.
        List<Integer> dataList = List.of(1, 2, 3, 4, 5);
        Flux.fromIterable(dataList)
                .subscribe(data -> System.out.println("List Data : " + dataList));
    }

    /**
     * Flux defer()-> Flux 객체를 반환
     * Flux create() -> 동기적인 객체를 반환
     */
    @Test
    @DisplayName("")
    void testFromFluxFunction () {
        Flux.defer(() -> {
            return Flux.just(1, 2);
        }).subscribe(data -> System.out.println("Flux defer data : " + data));

        Flux.create(sink -> {
            sink.next(1);
            sink.next(2);
            sink.next(3);
            sink.complete();
        }).subscribe(data -> System.out.println("Data From Sink : " + data));

    }

    @Test
    @DisplayName("")
    void testSinkDetail() {
        // create에서 sync 를 사용하는 시점  -> 복잡한 로직안에서 방출하고 싶은 데이터에서 방출 타이밍을 제어하고 싶을 때 sink를 사용
        Flux.<String>create(sink -> {
                    recursiveFunction(sink);
                })
                .contextWrite(Context.of("counter", new AtomicInteger(0))) // new AtomicInteger에 대한 값을 context에서 (키-값)형태로 저장하고 sink에서 context에 저장된 값을 가져와서 사용할 수 있음.
                .subscribe(data -> System.out.println("data from recursive : " + data));
    }

    // AtomicInteger : int랑 동일하지만 여러 스레드가 접근해도 안전하게 사용할 수 있는 자료형
    public void recursiveFunction(FluxSink<String> sink) {
        AtomicInteger count = sink.contextView().get("counter");
        if(count.incrementAndGet() < 10){ // ++int
            sink.next("sink count : " + count);
            recursiveFunction(sink);
        }else{
            sink.complete();
        }
    }
    /**
     * Flux의 흐름 시작 방법
     * 1. 데이터로부터 : just , empty , from시리즈
     * 2. 함수로부터 : defer(Flux 객체를 return) , create(동기적인 객체를 return - next)
     */

    @Test
    @DisplayName("")
    void testFluxCollectList() {
        Flux.<Integer>just(1, 2, 3, 4, 5)
                .map(data -> data * 2)
                .filter(data -> data % 2 == 0)
                .collectList() // collectList는 Flux에 설정되어 있는 여러가지 값을 하나로 즉 , List로 묶어서 데이터를 한번에 전달하고 싶을 때 사용한다. 실제 반환 값은 Mono<List<Integer>> , Flux를 Mono로 변경해줄 수 있음
                .subscribe(data -> System.out.println("Flux data : " +  data));
    }
    /**
     * Mono -> Flux 변환 : flatMapMany
     * - Mono에서는 데이터가 0~1개 이상만 방출 할 수 있는데 여러개의 데이터가 존재하는 경우에는 Flux로 변환하기 위해서 flatMapMany를 사용
     * Flux -> Mono 변환 : collectList
     * - Flux에서 데이터를 언제 방출해줄지 모르니까 collectList를 사용하면 한번에 묶어서 전달해줌
     */
}