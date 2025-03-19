package com.jorupmotte.donotdrink.friend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FriendReqRequestDto {
    @NotNull
    private String receiverAccountId;
}
