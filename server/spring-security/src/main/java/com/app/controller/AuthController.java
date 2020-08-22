package com.app.controller;


import com.app.contstants.ApiConstants;
import com.app.dto.request.AuthGithubUrlRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/" + ApiConstants.API_VERSION + "/auth")
public class AuthController {

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String githubClientId;


    @GetMapping("/github/url")
    public String auth(@RequestParam(value = "redirect_uri", required = true) String redirect_uri,
                       @RequestParam(value = "scope", required = true) String scope,
                       @RequestParam(value = "state", required = true) String state) {
        if (githubClientId != null && githubClientId.length() > 0) {
            return "https://github.com/login/oauth/authorize?" +
                    "client_id="+ githubClientId +
                    "&redirect_uri=" + redirect_uri +
                    "&scope=" + scope +
                    "&state=" + state;
        } else throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/github/exchange")
    public String login(@RequestParam(value = "code", required = true) String code,
                        @RequestParam(value = "state", required = true) String state){
            
    }
}
