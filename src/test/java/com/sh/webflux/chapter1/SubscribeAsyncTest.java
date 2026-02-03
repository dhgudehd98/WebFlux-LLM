package com.sh.webflux.chapter1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@SpringBootTest
public class SubscribeAsyncTest {

    @Test
    @DisplayName("")
    void oneToNineFlux() {
        Flux<Integer> intFlux = Flux.<Integer>create(data -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                    System.out.println("Thread Name : " + Thread.currentThread().getName());
                } catch (Exception e) {
                }
                data.next(i);
            }
            data.complete();
        }).subscribeOn(Schedulers.boundedElastic());

        intFlux.subscribe(data -> System.out.println("SubScribing WebFlux : " + data));
        System.out.println("Main Thread ");
    }
}