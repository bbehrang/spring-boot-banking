package com.app.dto.request;

import com.app.dto.request.groups.employer.EmployerUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployerRequestDto {
    @NotNull(groups = {EmployerUpdate.class})
    private Long id;
    @Size(min = 3, message = "Employer number should consist at least of 3 characters")
    private String number;
    @Size(min = 3, message = "Employer address should consist at least of 3 characters")
    private String address;
}
