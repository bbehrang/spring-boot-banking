package com.app.dto.request;

import com.app.models.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDto {
    @NotBlank
    private String number;
    @NotBlank
    private Currency currency;
    @PositiveOrZero
    private Double balance;
}
