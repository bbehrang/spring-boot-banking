package com.app.dto.reponse;

import com.app.dto.reponse.views.customer.CustomerFull;
import com.app.dto.reponse.views.customer.CustomerList;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerResponseDto extends AbstractResponseDto{
    @JsonView({CustomerList.class})
    private Long id;
    @JsonView({CustomerList.class})
    private String name;
    @JsonView({CustomerList.class})
    private String email;
    @JsonView({CustomerList.class})
    private Integer age;
    @JsonView({CustomerList.class})
    private String phone;
    @JsonView({CustomerFull.class})
    private Set<AccountResponseDto> accounts;
    @JsonView({CustomerFull.class})
    private Set<EmployerResponseDto> employers;
}
