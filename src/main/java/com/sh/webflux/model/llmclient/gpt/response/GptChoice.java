package com.sh.webflux.model.llmclient.gpt.response;

import com.sh.webflux.model.llmclient.gpt.request.GptCompletionRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GptChoice implements Serializable {
    @Serial
    private static final long serialVersionUID = 6508275603679969428L;

    private String finish_reason; // 응답이 어떤 이유로 끝났는지 응답해주는 부분
    private GptResponseMessageDto message;
    /**
     * Gpt에서 Stream을 사용하는 경우에는 delta에 담아서 전달해줌.
     */
    private GptResponseMessageDto delta;
}