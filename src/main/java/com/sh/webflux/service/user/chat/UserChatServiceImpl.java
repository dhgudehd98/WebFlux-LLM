package com.sh.webflux.service.user.chat;

import com.sh.webflux.model.llmclient.LlmChatRequestDto;
import com.sh.webflux.model.llmclient.LlmChatResponseDto;
import com.sh.webflux.model.user.chat.UserChatRequestDto;
import com.sh.webflux.model.user.chat.UserChatResponseDto;
import com.sh.webflux.service.llmClient.LlmWebClientService;
import lombok.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserChatServiceImpl implements UserChatService{


    private final LlmWebClientService llmWebClientService;
    /**
     * 웹 클라이언트를 사용하여 GPT API를 호출하고, 요청 온 응답을 가공해서 전달
     */
    @Override
    public Mono<UserChatResponseDto> getOneShotChat(UserChatRequestDto userChatRequestDto) {
        LlmChatRequestDto llmChatRequestDto = new LlmChatRequestDto(userChatRequestDto, "요청에 적절히 응답");
        Mono<LlmChatResponseDto> chatCompletionMono = llmWebClientService.getChatCompletion(llmChatRequestDto);
        return chatCompletionMono.map(UserChatResponseDto::new);
    }
}