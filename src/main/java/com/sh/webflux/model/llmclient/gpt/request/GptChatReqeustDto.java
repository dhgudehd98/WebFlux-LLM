package com.sh.webflux.model.llmclient.gpt.request;

import com.sh.webflux.model.llmclient.LLmModel;
import com.sh.webflux.model.llmclient.LlmChatRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GptChatReqeustDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -4750406794562510930L;

    /**
     * 메세지를 List의 형태로 보내는 이유는 이전 대화까지 컨텍스트를 유지하면서 대화를 이어 나가기 위해서
     *  - 메세지 안에 담기는 값은 실제로 GPT 할 때 보내는 메시지를 GPT 형식에 맞춰서 전달
     */
    private List<GptCompletionRequestDto> messages; // 메세지를 List의 형태로 보내느 이유는 이전 대화까지 컨텍스트를 유지하면서 대화를 이어 나가기 위해서
    private LLmModel model;
    private Boolean stream; // 스트림 통신으로 응답을 받을지 결정하는 옵션
    private GptResponseFormat response_format;

    // LLMChatRequestDto -> GPT 요청 API 맞게 변경을 해줘야 함.
    public GptChatReqeustDto(LlmChatRequestDto llmChatRequestDto) {
        if (llmChatRequestDto.isUseJson()){
            this.response_format = new GptResponseFormat("json_object");
        }
        this.messages = List.of(
                new GptCompletionRequestDto(GptMessageRole.SYSTEM, llmChatRequestDto.getSystemPrompt()),
                new GptCompletionRequestDto(GptMessageRole.USER, llmChatRequestDto.getUserRequest())
        );

        this.model = llmChatRequestDto.getLlmModel();
    }

    @Override
    public String toString() {
        return "GptChatReqeustDto{" +
                "messages=" + messages +
                ", model=" + model +
                ", stream=" + stream +
                ", response_format=" + response_format +
                '}';
    }
}