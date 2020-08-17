package com.app.controller;

import com.app.contstants.ApiConstants;
import com.app.dto.reponse.AccountResponseDto;
import com.app.dto.request.SimpleBalanceRequestDto;
import com.app.dto.request.TransferRequestDto;
import com.app.facade.AccountFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/" + ApiConstants.API_VERSION + "/accounts")
public class AccountController {

    @Autowired
    private AccountFacade accountFacade;

    @GetMapping(value = "/{number}")
    public AccountResponseDto getAccount(@PathVariable("number") String number,
                                         HttpServletResponse response){
        AccountResponseDto account = accountFacade.getAccountByNumber(number);
        if(account == null) response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return account;
    }
    @PutMapping(value = "/withdrawal/{number}")
    public AccountResponseDto withdraw(@PathVariable("number") String number,
                            @RequestBody Map<String, Double> amount,
                            HttpServletResponse response) throws JsonProcessingException {

        AccountResponseDto account = accountFacade.withdraw(number, amount.get("amount"));
        if(account == null) response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return account;
    }
    @PutMapping(value = "/top-up/{number}")
    public AccountResponseDto topUp(@PathVariable("number") String number,
                                    @RequestBody SimpleBalanceRequestDto simpleBalanceRequestDto,
                                    HttpServletResponse response) {

        AccountResponseDto account = accountFacade.topUp(number, simpleBalanceRequestDto.getAmount());
        if(account == null) response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return account;
    }
    @PutMapping(value = "/transfer")
    public AccountResponseDto topUp(@RequestBody TransferRequestDto transferRequestDto,
                                    HttpServletResponse response) {

        AccountResponseDto account = accountFacade.transfer(transferRequestDto.getSender(),
                transferRequestDto.getReceiver(), transferRequestDto.getAmount());
        if(account == null) response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return account;
    }
}
