package com.sh.webflux.service.user.chat;

import com.sh.webflux.model.user.chat.UserChatRequestDto;
import com.sh.webflux.model.user.chat.UserChatResponseDto;
import reactor.core.publisher.Mono;

public interface UserChatService {
    // UserChatReqeustDto > UserResponseDto 로 변경 사용자가 요청한 메세지를 응답 메세지로 변경을 해줘야 함.
    Mono<UserChatResponseDto> getOneShotChat(UserChatRequestDto userChatRequestDto);
}