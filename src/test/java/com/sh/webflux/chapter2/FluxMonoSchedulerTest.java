package com.sh.webflux.chapter2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@SpringBootTest
public class FluxMonoSchedulerTest {

    /**
     * BoundedElastic / Parallel
     *
     * BoundedElastic
     * - 생성 방법
     *  - 유저가 스레드를 요청 할 때 마다 스레드를 탄력적으로 생성해서 할당
     *  - 이미 만들어둔 스레드가 있으면 해당 스레드를 할당
     *  - 생성 가능한 스레드 제한이 있다.
     *  - 스레드 제한이 걸리면 스레드를 할당 받지 못한 작업은 큐에서 대기 시킨다.
     * - 생성 주기
     *  - 한번 만들어지면 일정 시간 동안 스레드를 삭제하지 않고 유지하고 있다가 일정 시간 동안 사용되지 않으면 삭제됨.
     * - 용도
     *  - 블로킹 작업 처리에 사용된다.
     *
     * Parallel
     * - 생성방법
     *  - 유저가 최초 한번 호출 하면 물리 스레드와 같은 양의 스레드를 생성하고 , 삭제하지 않음
     * - 생성주기
     *  - 처음에 한번 생성되고 나면 계속 유지된다.
     * - 용도
     *  - CPU 작업을 병렬로 처리할 때 사용된다.
     */

    @Test
    @DisplayName("")
    void testBasicFluxMono() {
        Mono.<Integer>just(2)
                .map(data -> {
                    System.out.println("Map Thread Name : " + Thread.currentThread().getName());
                    return 2;
                })
                .publishOn(Schedulers.parallel()) // 스레드 할당은 Publisher / SubsCribe 둘다 할당 할 수 있음.
                .filter(data -> {
                    System.out.println("Filter Thread Name : " + Thread.currentThread().getName());
                    return data % 4 == 0;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(data -> System.out.println("Mono Subscribe data = " + data));

    }
}