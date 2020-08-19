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
import javax.validation.Valid;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/" + ApiConstants.API_VERSION + "/accounts")
public class AccountController {

    @Autowired
    private AccountFacade accountFacade;

    @GetMapping(value = "/{number}")
    public AccountResponseDto getAccount(@PathVariable("number") String number) {
        return accountFacade.getAccountByNumber(number);
    }

    @PutMapping(value = "/withdrawal/{number}")
    public AccountResponseDto withdraw(@PathVariable("number") String number,
                                       @Valid @RequestBody SimpleBalanceRequestDto balanceRequestDto) {
        return accountFacade.withdraw(number, balanceRequestDto.getAmount());
    }

    @PutMapping(value = "/top-up/{number}")
    public AccountResponseDto topUp(@PathVariable("number") String number,
                                    @Valid @RequestBody SimpleBalanceRequestDto simpleBalanceRequestDto) {
        return accountFacade.topUp(number, simpleBalanceRequestDto.getAmount());
    }

    @PutMapping(value = "/transfer")
    public AccountResponseDto topUp(@Valid @RequestBody TransferRequestDto transferRequestDto) {
        return accountFacade.transfer(transferRequestDto.getSender(),
                transferRequestDto.getReceiver(), transferRequestDto.getAmount());
    }
}
