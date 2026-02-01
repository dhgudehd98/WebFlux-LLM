package com.sh.webflux.controller.user.chat;


import com.sh.webflux.model.user.chat.UserChatRequestDto;
import com.sh.webflux.model.user.chat.UserChatResponseDto;
import com.sh.webflux.service.user.chat.UserChatService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serial;
import java.io.Serializable;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@Slf4j
public class UserChatController implements Serializable {

    private final UserChatService userChatService;

    @PostMapping("/oneshot")
    public Mono<UserChatResponseDto> oneShotChat(@RequestBody UserChatRequestDto userChatRequestDto) {
        // 서비스에서 Request 가공해서 response로 돌려줘야 함.
        log.info("요청 데이터 : " + userChatRequestDto.getRequest());
        log.info("요청 모델 : " + userChatRequestDto.getLlmModel());

        return userChatService.getOneShotChat(userChatRequestDto);
    }

    /**
     * Stream으로 서비스를 구현해서 응답 데이터가 여러개 올 수 있도록 Mono -> Flux로 변경
     * @param userChatRequestDto
     * @return
     */
    @PostMapping("/oneshot/stream")
    public Flux<UserChatResponseDto> oneShotChatStreams(@RequestBody UserChatRequestDto userChatRequestDto) {
        // 서비스에서 Request 가공해서 response로 돌려줘야 함.
        log.info("요청 데이터 : " + userChatRequestDto.getRequest());
        log.info("요청 모델 : " + userChatRequestDto.getLlmModel());

        return userChatService.getOneShotChatStream(userChatRequestDto);
    }

}