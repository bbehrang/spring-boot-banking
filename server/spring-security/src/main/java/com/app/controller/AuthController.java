package com.app.controller;


import com.app.contstants.ApiConstants;
import com.app.dto.request.CustomerRequestDto;
import com.app.dto.request.LoginRequestDto;
import com.app.facade.CustomerFacade;
import com.app.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;


@RestController
@RequestMapping(value = "/api/" + ApiConstants.API_VERSION + "/auth")
public class AuthController {

    @Autowired
    private CustomerFacade customerFacade;

    @PostMapping("/register")
    public void signUp(@Valid @RequestBody CustomerRequestDto customerRequestDto){
        customerFacade.save(customerRequestDto);
    }

/*    @PostMapping("/login")
    public void signIn(@Valid @RequestBody LoginRequestDto loginRequestDto){

    }*/



   /* @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String githubClientId;

    @PostMapping
    public void loginLocal(@Valid @RequestBody LoginRequestDto loginRequestDto){

    }

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
            return null;
    }*/
}
