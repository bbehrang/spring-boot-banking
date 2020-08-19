package com.app.dto.request;


import com.app.dto.request.groups.customer.CustomerNew;
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
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
            message = "Password should contain at least 1 lowercase and 1 uppercase letter" +
                        " and be at least 8 characters long")
    private String password;
    @NotBlank(message = "Email cannot be empty", groups = {CustomerNew.class})
    @Email(message = "Invalid email address", groups = {CustomerNew.class})
    private String email;
    @NotBlank(message = "Phone number should be provided", groups = {CustomerNew.class})
    @Pattern(regexp = "^[+][(][0-9]{1,3}[)][0-9]{6,12}$",
            message = "Phone number should match the format: `+(CountryCode)Number`",
            groups = {CustomerNew.class})
    private String phone;
    @Min(value = 18, message = "You should be at least 18 years old to continue.")
    private Integer age;
}
