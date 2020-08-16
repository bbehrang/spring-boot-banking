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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/" + ApiConstants.API_VERSION + "/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;
    
    @GetMapping(value = "/{number}")
    public Account getAccount(@PathVariable("number") String number){
        return accountService.getByNumber(number);
    }
    @PutMapping(value = "/withdrawal/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> withdraw(@PathVariable("number") String number,
                                           @RequestBody Map<String, Double> amount) throws JsonProcessingException {

        Account account = accountService.withdraw(number, amount.get("amount"));
        if(account == null){
            return ResponseEntity.badRequest().build();
        }
        else{
            return ResponseEntity.ok(account);
        }
    }
    @PutMapping(value = "/top-up/{number}")
    public ResponseEntity<Account> topUp(@PathVariable("number") String number,
                                            @RequestBody Map<String, Double> amount) {

        Account account = accountService.topUp(number, amount.get("amount"));
        if(account == null){
            return ResponseEntity.badRequest().build();
        }
        else{
            return ResponseEntity.ok(account);
        }
    }
    @PutMapping(value = "/transfer/{sender}/{receiver}")
    public ResponseEntity<Account> topUp(@PathVariable("sender") String sender,
                                         @PathVariable("receiver") String receiver,
                                         @RequestBody Map<String, Double> amount) {

        Account account = accountService.transfer(sender, receiver, amount.get("amount"));
        if(account == null){
            return ResponseEntity.badRequest().build();
        }
        else{
            return ResponseEntity.ok(account);
        }
    }
}
