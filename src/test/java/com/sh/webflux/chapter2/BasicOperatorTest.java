package com.sh.webflux.chapter2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
public class BasicOperatorTest {

    /**
     * Mono로부터 데이터를 시작할 수 있는 방법 
     * 1. just
     * 2. empty
     * 3. 함수(fromCallable() , defer())
     */
    @Test

    void startMonoFromData() {
        Mono.just(1).subscribe(data -> System.out.println("Just Data : " + data));
        // empty는 사소한 에러가 발생했을 때 로그를 남기고 empty의 Mono를 전파 
        Mono.empty().subscribe(data -> System.out.println("Empty data : " + data)); // 데이터가 아무것도 출력되지 않음
    }

    /**
     * fromCallable -> 동기적인 객체 , Mono / Flux를 자유롭게 반환 가능 (어떠한 객체를 반환 가능) 
     * defer -> Mono를 반환하고 싶을때 사용한다.
     */
    @Test
    @DisplayName("")
    void startMonoFromFunction() {
        Mono.fromCallable(() -> {
                // 1. 로직 실행 
                // 여기에서 return 하는 값은 실제로 Mono<String>에 대한 객체값이 반환이 됨.
                return "안녕";
        });
        // restTemplate, JPA 를 사용할 때 즉 , 블로킹이 발생하는 라이브러릴 사용할때 Mono로 스레드를 분리하여 처리 

        Mono<String> deferReturnData = Mono.defer(() -> {
            return callWebClient("안녕");
        });

        // just로 설정되어 있기 떄문에 구독하지 않아도 해당 데이터가 설정됨.
        // defer로 설정된 경우 Mono.defer()에 대한 부분은 구독을 해야만 해당 코드가 실행된다.
        Mono<String> monoFromJust = callWebClient("안녕!");
    }

    @Test
    @DisplayName("")
    void testDeferNecessity() {
        // abc를 만드는 로직도 Mono의 흐름 안에서 관리하고 싶다
        // 하나의 큰 흐름을 하나의 Mono에서 관리하고 싶을때
        Mono<String> defer = Mono.defer(() -> {
            String a = "안녕";
            String b = "하세";
            String c = "요";
            return callWebClient(a + b + c);
        });
    }

    private Mono<String> callWebClient(String request) {
        return Mono.just(request);
    }
    /**
     * Mono의 흐름 시작 방법
     * 1. 데이터로부터 시작 -> 일반적인 경우 just / 특이한 상황일 떄 empty (Optional.empty()를 반환해야되는 함수랑 비슷하다고 생각)
     * 2. 함수로부터 시작 -> 동기적인 객체를 Mono로 반환하고 싶을 때 fromCallable / 코드의 흐름을 Mono 안에서 관리하면서 Mono를 반환하고 싶을 때 defer
     */

    @Test
    @DisplayName("")
    void monoToFlux() {
        Mono<Integer> one = Mono.just(1);
        Flux<Integer> integerFlux = one.flatMapMany(data -> {
            return Flux.just(data, data + 1, data + 2);
        });

        integerFlux.subscribe(data -> System.out.println("data : " + data));
    }
    // 초기에는 Mono로 데이터를 구성했지만 데이터가 많아지는 경우 FlatMapMany로 Mono의 데이터를 Flux로 변환하면 여러개의 데이터를 방출하도록 변경해준다.
}