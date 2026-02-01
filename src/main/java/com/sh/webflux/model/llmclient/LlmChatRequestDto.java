package com.sh.webflux.model.llmclient;


import com.sh.webflux.model.user.chat.UserChatRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LlmChatRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 2841747232106676797L;

    private String userRequest; // 사용자가 입력한 요청 값

    /**
     * systemPrompt : LLM이 응답할 떄 지켜야 되는 행동 강령
     * - systemPrompt는 userRequest에 포함되는 내용보다 더 높은 강제성과 우선순위를 가짐.
     */
    private String systemPrompt;
    private boolean useJson; //  systemPrompt보다 더 높은 강제성을 가지면서 LLM 응답형식이 JSON 형식으로 고정이 되게 됨 . LLM이 응답이 올떄 JSON 형식으로 값을 변경하고 싶으면 userJson 사용하기
    private LLmModel llmModel;

    public LlmChatRequestDto(UserChatRequestDto userChatRequestDto,  String systemPrompt) {
        this.userRequest = userChatRequestDto.getRequest();
        this.llmModel = userChatRequestDto.getLlmModel();
        this.systemPrompt = systemPrompt;

    }
}