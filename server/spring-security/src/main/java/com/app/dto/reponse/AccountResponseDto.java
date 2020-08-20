package com.app.dto.reponse;

import com.app.models.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountResponseDto extends AbstractResponseDto{
    private String number;
    private Currency currency;
    private Double balance;
}
