package com.sh.webflux.model.llmclient;


import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public enum LLmModel {

    GPT_4o("gpt-4o", LlmType.GPT),
    GEMINI_2_0_FLASH("gemini-2.0-flash", LlmType.GEMINI);

    private final String code;
    private final LlmType llmType;

    @JsonValue
    @Override
    public String toString() {
        return code;
    }
}
