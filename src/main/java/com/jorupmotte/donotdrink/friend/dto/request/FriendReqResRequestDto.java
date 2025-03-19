package com.jorupmotte.donotdrink.friend.dto.request;

import com.jorupmotte.donotdrink.common.type.FriendStatusType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FriendReqResRequestDto {
    @NotNull
    private FriendStatusType status;
}
