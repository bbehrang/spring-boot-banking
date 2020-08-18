package com.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDto {
    @NotBlank(message = "Sender account number cannot be empty")
    private String sender;
    @NotBlank(message = "Receiver account number cannot be empty")
    private String receiver;
    @Positive(message = "Transfer amount should be greater than 0")
    private Double amount;
}
