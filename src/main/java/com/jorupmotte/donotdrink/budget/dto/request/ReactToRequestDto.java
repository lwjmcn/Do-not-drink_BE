package com.jorupmotte.donotdrink.budget.dto.request;


import com.jorupmotte.donotdrink.common.type.ReactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReactToRequestDto {
    @NotNull
    private ReactionType reactionType;

    @NotNull
    private int count;
}
