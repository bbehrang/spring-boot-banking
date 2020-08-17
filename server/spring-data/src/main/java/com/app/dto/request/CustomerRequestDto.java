package com.app.dto.reponse;

import com.app.dto.reponse.views.customer.CustomerFull;
import com.app.dto.reponse.views.customer.CustomerList;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDto{
    @Size(min = 2)
    private String name;
    @Email
    private String email;
    @NotBlank(message = "Phone number should be provided")
    @Pattern(regexp = "^[+][(][0-9]{1,3}[)][0-9]{6,12}$",
            message = "Phone number should match the format: `+(CountryCode)Number`")
    private String phone;
    @Min(18)
    private Integer age;
}
