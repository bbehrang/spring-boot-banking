package com.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployerRequestDto {
    @Size(min = 3, message = "Employer number should consist at least of 3 characters")
    private String number;
    @Size(min = 3, message = "Employer address should consist at least of 3 characters")
    private String address;
}
