package com.sh.webflux.chapter2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
public class FluxMonoErrorAndSignalTest {

    @Test
    @DisplayName("")
    void testBasicSignal() {
        Flux.just(1, 2, 3, 4) // operator가 선언된 위쪽에 있는 부분을 upStream이라고 함.
                .doOnNext(publishdedData -> System.out.println("publishdedData = " + publishdedData)) // 얘보다 이전 스트림에서 방출되는 데이터들을 포착해서 출력해주는 함수 = 이전의 스트림 Flux.just(1,2,3,4)
                .doOnComplete(() -> System.out.println("Stream END"))
                .doOnError(ex -> {
                    System.out.println("에러 사항 발생 : " + ex.getMessage());
                })
                .subscribe(data -> System.out.println("data = " + data));
    }

    @Test
    @DisplayName("")
    void testFluxMonoError() {
        Flux.just(1, 2, 3, 4) // operator가 선언된 위쪽에 있는 부분을 upStream이라고 함.
                .map(data -> {
                    if (data == 3) {
                        throw new RuntimeException();
                        // 해당 에러가 발생해도 스트림 안에서 발생한 에러는 스트림 안에서 처리하고, 외부로 방출하지 않음. 실제로 이 코드를 실행하면 에러가 터졌는데도 테스트는 성공한걸로 처리됨. 물론 에러가 발생하면 그 후에 데이터처리는진행하지 않음
                    }
                    return data * 2;
                })
                .subscribe(data -> System.out.println("data = " + data));
    }

    @Test
    @DisplayName("")
    void fetFluxMonoDotError() {
        Flux.just(1, 2, 3, 4)
                .flatMap(data -> {
                    if (data != 3) {
                        return Mono.just(data);
                    } else {
//                        throw new RuntimeException();
                        return Mono.error(new RuntimeException());
                    }
                }).subscribe(data -> System.out.println("data = " + data));
    }
}