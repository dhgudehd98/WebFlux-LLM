package com.sh.webflux.service.llmClient;

import com.sh.webflux.model.llmclient.LlmChatRequestDto;
import com.sh.webflux.model.llmclient.LlmChatResponseDto;
import com.sh.webflux.model.llmclient.LlmType;
import com.sh.webflux.model.llmclient.gpt.request.GptChatReqeustDto;
import com.sh.webflux.model.llmclient.gpt.response.GptChatResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class GptWebClientService implements  LlmWebClientService{

    private final WebClient webClient;
    @Value("${llm.gpt.key}")
    private String gptApiKey;

    @Override
    public Mono<LlmChatResponseDto> getChatCompletion(LlmChatRequestDto llmChatRequestDto) {
        GptChatReqeustDto gptChatReqeustDto = new GptChatReqeustDto(llmChatRequestDto);
        log.info("바디 요청 데이터 화인하기 -> gptChat Request Dto : " + gptChatReqeustDto.toString());
        return webClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + gptApiKey)
                .bodyValue(gptChatReqeustDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (clientResponse -> {
                    return clientResponse.bodyToMono(String.class).flatMap(body -> {
                        log.error("Error Response : {}", body);
                        return Mono.error(new RuntimeException("API 요청 실패 : " + body));
                    });
                }))
                .bodyToMono(GptChatResponseDto.class)
                .map(LlmChatResponseDto::new);
    }

    @Override
    public LlmType getLlmType() {
        return null;
    }
}