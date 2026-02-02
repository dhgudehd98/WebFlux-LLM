package com.sh.webflux.model.llmclient.gemini.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeminiCandidate implements Serializable {
    @Serial
    private static final long serialVersionUID = 3239509794899199003L;

    private GeminiContent content;
    private String finishReason;
}