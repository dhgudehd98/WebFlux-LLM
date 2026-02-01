package com.sh.webflux.model.user.chat;


import com.sh.webflux.model.llmclient.LLmModel;
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
public class UserChatRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 2623298527642453278L;

    private String request;
    private LLmModel llmModel;
}