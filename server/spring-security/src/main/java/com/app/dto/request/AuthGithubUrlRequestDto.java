package com.app.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthGithubUrlRequestDto implements Serializable {
    @NotBlank
    private String redirectUrl;
    @NotBlank
    private String state;
    @NotBlank
    private String scope;
}
