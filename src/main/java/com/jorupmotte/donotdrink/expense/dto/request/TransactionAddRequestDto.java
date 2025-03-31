package com.jorupmotte.donotdrink.expense.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionAddRequestDto {
    @NotBlank
    public String datetime;

    @NotNull
    public Long categoryId;

    @NotBlank
    public String name;

    @NotNull
    public int amount;

    public String description;

}
