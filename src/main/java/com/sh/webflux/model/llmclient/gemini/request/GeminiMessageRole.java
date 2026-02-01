package com.sh.webflux.model.llmclient.gemini.request;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GeminiMessageRole {

    USER,
    Model
    ;

    @JsonValue
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
