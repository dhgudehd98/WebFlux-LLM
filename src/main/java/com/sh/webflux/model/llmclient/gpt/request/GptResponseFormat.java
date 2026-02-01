package com.sh.webflux.model.llmclient.gpt.request;

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
public class GptResponseFormat implements Serializable {

    @Serial
    private static final long serialVersionUID = -1710575607323738165L;
    private String type;
}