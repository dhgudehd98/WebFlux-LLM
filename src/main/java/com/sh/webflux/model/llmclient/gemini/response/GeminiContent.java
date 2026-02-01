package com.sh.webflux.model.llmclient.gemini.response;

import com.sh.webflux.model.llmclient.gemini.request.GeminiMessageRole;
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
public class GeminiContent implements Serializable {
    @Serial
    private static final long serialVersionUID = -4238121852044154500L;

    private List<GeminiPart> parts;
    private GeminiMessageRole role;

    public GeminiContent(List<GeminiPart> parts) {
        this.parts = parts;
    }
}