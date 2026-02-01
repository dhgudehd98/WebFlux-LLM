package com.sh.webflux.model.llmclient.gemini.request;

import com.sh.webflux.model.llmclient.gpt.request.GptMessageRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeminiCompletionRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 2142163353057550939L;

    private GeminiMessageRole role; // 보내는 채팅내역이 AI가 이전에 응답했던건지 아니면 유저가 응답을 한건지  시스템 프롬프트인지 구별하기 위해서 사용
    private String content;

    public GeminiCompletionRequestDto(String content) {
        this.content = content;
    }
}