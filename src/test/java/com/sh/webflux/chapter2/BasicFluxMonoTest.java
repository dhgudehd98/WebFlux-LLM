package com.sh.webflux.chapter2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
public class BasicFluxMonoTest {

    @Test
    @DisplayName("")
    void testBasicFluxMono() {
        /**
         * Flux / Mono를 시작
         * 1. 빈 함수로 시작 -> fromcallable
         * 2. 데이터로 시작 -> just
         *
         * Flux vs Mono
         * - Flux : 0개 이상의 데이터를 방출
         * - Mono : 0 ~ 1개의 데이터만 방출 ( 1개의 데이터만 방출할 때는 Mono를 사용)
         *
         * Spring WebFlux 를 사용할 떄는 Mono / Flux의 데이터 흐름부터 실행하도록 구현해야됨.
         */
        Flux.<Integer>just(1, 2, 3, 4, 5)
                .map(data -> data * 2)
                .filter(data -> data * 2 == 0)
                .subscribe(data -> System.out.println("Flex가 구독한 data : " + data));

        Mono.<Integer>just( 2)
                .map(data -> data * 2)
                .filter(data -> data * 2 == 0)
                .subscribe(data -> System.out.println("Mono가 구독한 data : " + data));
        // 1. just를 사용하여 데이터로부터 흐름 시작
        // 2. map / filter와 같은 데이터 가공
        // 3. subscribe 하면서 데이터 방출

    }

}