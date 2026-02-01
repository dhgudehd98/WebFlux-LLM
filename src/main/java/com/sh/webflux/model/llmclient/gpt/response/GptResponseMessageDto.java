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
public class GptResponseMessageDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -918798967347227321L;

    private String content;
}