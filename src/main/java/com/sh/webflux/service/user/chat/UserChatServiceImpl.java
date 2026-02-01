package com.sh.webflux.service.user.chat;

import com.sh.webflux.model.llmclient.LlmChatRequestDto;
import com.sh.webflux.model.llmclient.LlmChatResponseDto;
import com.sh.webflux.model.llmclient.LlmType;
import com.sh.webflux.model.user.chat.UserChatRequestDto;
import com.sh.webflux.model.user.chat.UserChatResponseDto;
import com.sh.webflux.service.llmClient.LlmWebClientService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserChatServiceImpl implements UserChatService{


    private final Map<LlmType, LlmWebClientService> llmWebClientServiceMap;
    /**
     * 웹 클라이언트를 사용하여 GPT API를 호출하고, 요청 온 응답을 가공해서 전달
     */
    @Override
    public Mono<UserChatResponseDto> getOneShotChat(UserChatRequestDto userChatRequestDto) {
        LlmChatRequestDto llmChatRequestDto = new LlmChatRequestDto(userChatRequestDto, "요청에 적절히 응답");
        log.info("Get LLM Model : " + userChatRequestDto.getLlmModel());
        Mono<LlmChatResponseDto> chatCompletionMono = llmWebClientServiceMap.get(userChatRequestDto.getLlmModel().getLlmType())
                .getChatCompletion(llmChatRequestDto);
        return chatCompletionMono.map(UserChatResponseDto::new);
    }
}