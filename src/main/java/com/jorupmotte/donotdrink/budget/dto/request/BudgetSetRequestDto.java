package com.jorupmotte.donotdrink.budget.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BudgetSetRequestDto {
    @NotNull
    private Long budget;
}
