package com.sh.webflux.service.llmClient;

import com.sh.webflux.model.llmclient.LlmChatRequestDto;
import com.sh.webflux.model.llmclient.LlmChatResponseDto;
import com.sh.webflux.model.llmclient.LlmType;
import reactor.core.publisher.Mono;

public interface LlmWebClientService {
    Mono<LlmChatResponseDto> getChatCompletion(LlmChatRequestDto llmChatRequestDto);

    LlmType getLlmType();
    //
}
