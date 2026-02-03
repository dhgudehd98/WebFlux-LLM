package com.sh.webflux.chapter1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OneToNinie {

    @Test
    @DisplayName("")
    void oneToNineList() {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
            }
            list.add(i);
        }

        for (Integer integer : list) {
            System.out.println("숫자 : " + integer);
        }

    }

    @Test
    @DisplayName("")
    void oneToNineFlux() {
        Flux<Integer> intFlux = Flux.create(data -> {
            for (int i = 0; i < 10; i++) {
               data.next(i);
                System.out.println("Thread Name : " + Thread.currentThread().getName());
            }
            data.complete();
        });

        intFlux.subscribe(data -> System.out.println("SubScribing WebFlux : " + data));
        System.out.println("이벤트 루프 스레드로 복귀" + Thread.currentThread().getName());
    }

}