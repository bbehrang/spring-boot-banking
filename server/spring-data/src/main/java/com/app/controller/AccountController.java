package com.app.controller;

import com.app.contstants.ApiConstants;
import com.app.models.Account;
import com.app.services.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/" + ApiConstants.API_VERSION + "/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/{number}")
    public Account getAccount(@PathVariable("number") String number, HttpServletResponse response){
        Account account = accountService.getByNumber(number);
        if(account == null) response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return account;
    }
    @PutMapping(value = "/withdrawal/{number}")
    public Account withdraw(@PathVariable("number") String number,
                            @RequestBody Map<String, Double> amount,
                            HttpServletResponse response) throws JsonProcessingException {

        Account account = accountService.withdraw(number, amount.get("amount"));
        if(account == null) response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return account;
    }
    @PutMapping(value = "/top-up/{number}")
    public Account topUp(@PathVariable("number") String number,
                                         @RequestBody Map<String, Double> amount,
                                         HttpServletResponse response) {

        Account account = accountService.topUp(number, amount.get("amount"));
        if(account == null) response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return account;
    }
    @PutMapping(value = "/transfer/{sender}/{receiver}")
    public Account topUp(@PathVariable("sender") String sender,
                                         @PathVariable("receiver") String receiver,
                                         @RequestBody Map<String, Double> amount,
                                         HttpServletResponse response) {

        Account account = accountService.transfer(sender, receiver, amount.get("amount"));
        if(account == null) response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return account;
    }
}
