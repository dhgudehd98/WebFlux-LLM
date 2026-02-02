package com.sh.webflux.service.llmClient;

import com.sh.webflux.exception.CustomErrorType;
import com.sh.webflux.exception.ErrorTypeException;
import com.sh.webflux.model.llmclient.LlmChatRequestDto;
import com.sh.webflux.model.llmclient.LlmChatResponseDto;
import com.sh.webflux.model.llmclient.LlmType;
import com.sh.webflux.model.llmclient.gemini.request.GeminiChatRequestDto;
import com.sh.webflux.model.llmclient.gemini.response.GeminiChatResponseDto;
import com.sh.webflux.model.llmclient.gpt.request.GptChatReqeustDto;
import com.sh.webflux.model.llmclient.gpt.response.GptChatResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiWebClientService implements LlmWebClientService{

    private final WebClient webClient;

    @Value("${llm.gemini.key}")
    private String geminiApiKey;
    @Override
    public Mono<LlmChatResponseDto> getChatCompletion(LlmChatRequestDto llmChatRequestDto) {
        GeminiChatRequestDto geminiChatRequestDto = new GeminiChatRequestDto(llmChatRequestDto); // 이걸 이용해서 요청
        AtomicInteger atomicInteger = new AtomicInteger(0);
        return webClient.post()
                .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + geminiApiKey)
                .bodyValue(geminiChatRequestDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (clientResponse -> {
                    return clientResponse.bodyToMono(String.class).flatMap(body -> {
                        log.error("Error Response : {}", body);
                        return Mono.error(new ErrorTypeException("API 요청 실패 : " + body, CustomErrorType.GEMINI_RESPONSE_ERROR));
                    });
                }))
                .bodyToMono(GeminiChatResponseDto.class)
                .map(LlmChatResponseDto::new);
    }

    @Override
    public LlmType getLlmType() {
        return LlmType.GEMINI;
    }

    @Override
    public Flux<LlmChatResponseDto> getChatCompletionStream(LlmChatRequestDto requestDto) {
        GeminiChatRequestDto geminiChatRequestDto = new GeminiChatRequestDto(requestDto); // 이걸 이용해서 요청
        return webClient.post()
                .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:streamGenerateContent?key=" + geminiApiKey)
                .bodyValue(geminiChatRequestDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (clientResponse -> {
                    return clientResponse.bodyToMono(String.class).flatMap(body -> {
                        log.error("Error Response : {}", body);
                        return Mono.error(new ErrorTypeException("API 요청 실패 : " + body, CustomErrorType.GEMINI_RESPONSE_ERROR));
                    });
                }))
                .bodyToFlux(GeminiChatResponseDto.class)
                .map(LlmChatResponseDto::new);
    }
}