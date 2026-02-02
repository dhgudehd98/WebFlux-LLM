package com.sh.webflux.model.llmclient.gemini.response;

import com.sh.webflux.exception.CustomErrorType;
import com.sh.webflux.exception.ErrorTypeException;
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
public class GeminiChatResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -1836673775081551904L;

    private List<GeminiCandidate> candidates;

    public String getSingleText() {
        return candidates.stream()
                .findFirst()
                .flatMap(candidate ->
                        candidate.getContent()
                                .getParts()
                                .stream()
                                .findFirst()
                                .map(parts -> parts.getText())
                )
                .orElseThrow((() -> new ErrorTypeException("[GEMINI Response] There is no choices. ", CustomErrorType.GEMINI_RESPONSE_ERROR)));

    }
}