package com.sh.webflux.model.llmclient.gpt.response;

import com.sh.webflux.exception.CustomErrorType;
import com.sh.webflux.exception.ErrorTypeException;
import com.sh.webflux.model.llmclient.gpt.request.GptCompletionRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GptChatResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 6694545262909440703L;

    private List<GptChoice> choices;

    public GptChoice getSingleChoice() {
        return choices.stream().findFirst().orElseThrow(() -> new ErrorTypeException("[GPT Response] There is no choices. ", CustomErrorType.GPT_RESPONSE_ERROR));
    }

}