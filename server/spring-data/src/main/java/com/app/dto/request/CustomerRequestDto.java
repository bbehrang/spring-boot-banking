package com.app.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDto implements Serializable {
    @Size(min = 2, message = "Name should be at least 2 characters long")
    private String name;
    @Size(min = 2, message = "Name should be at least 2 characters long")
    private String password;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "Phone number should be provided")
   @Pattern(regexp = "^[+][(][0-9]{1,3}[)][0-9]{6,12}$",
            message = "Phone number should match the format: `+(CountryCode)Number`")
    private String phone;
    @Min(value = 18, message = "You should be at least 18 years old to continue.")
    private Integer age;
}
