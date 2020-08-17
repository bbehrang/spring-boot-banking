package com.app.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDto{
    @Size(min = 2)
    private String name;
    @Size(min = 2)
    private String password;
    @Email
    private String email;
    @NotBlank(message = "Phone number should be provided")
    @Pattern(regexp = "^[+][(][0-9]{1,3}[)][0-9]{6,12}$",
            message = "Phone number should match the format: `+(CountryCode)Number`")
    private String phone;
    @Min(18)
    private Integer age;
}
