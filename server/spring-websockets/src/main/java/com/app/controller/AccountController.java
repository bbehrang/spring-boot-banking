package com.app.controller;

import com.app.contstants.ApiConstants;
import com.app.dto.reponse.AccountResponseDto;
import com.app.dto.reponse.NotificationResponseDto;
import com.app.dto.reponse.OperationType;
import com.app.dto.request.SimpleBalanceRequestDto;
import com.app.dto.request.TransferRequestDto;
import com.app.facade.AccountFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/" + ApiConstants.API_VERSION + "/accounts")
public class AccountController {

    @Autowired
    private AccountFacade accountFacade;
    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping(value = "/{number}")
    public AccountResponseDto getAccount(@PathVariable("number") String number) {
        log.info("Getting account by number {}", number);
        return accountFacade.getAccountByNumber(number);
    }

    @PutMapping(value = "/withdrawal/{number}")
    public AccountResponseDto withdraw(@PathVariable("number") String number,
                                       @Valid @RequestBody SimpleBalanceRequestDto balanceRequestDto,
                                       Principal principal) {
        log.info("Withdrawing from account with number {}, amount {}", number, balanceRequestDto.getAmount());
        AccountResponseDto accountResponse = accountFacade.withdraw(number, balanceRequestDto.getAmount());
        template.convertAndSend("/notifications/data",
                new NotificationResponseDto(principal.getName(), null, balanceRequestDto.getAmount(),
                        OperationType.withdraw));
        return accountResponse;
    }

    @PutMapping(value = "/top-up/{number}")
    public AccountResponseDto topUp(@PathVariable("number") String number,
                                    @Valid @RequestBody SimpleBalanceRequestDto simpleBalanceRequestDto,
                                    Principal principal) {
        log.info("Topping account  with number {}, amount {}", number, simpleBalanceRequestDto.getAmount());
        AccountResponseDto response =  accountFacade.topUp(number, simpleBalanceRequestDto.getAmount());
        template.convertAndSend("/notifications/data",
                new NotificationResponseDto(principal.getName(), null,simpleBalanceRequestDto.getAmount(), OperationType.topUp));
        return response;
    }

    @PutMapping(value = "/transfer")
    public AccountResponseDto topUp(@Valid @RequestBody TransferRequestDto transferRequestDto,
                                    Principal principal) {
        log.info("Transferring from  account with number {}, amount {}, " +
                "to account with number {}", transferRequestDto.getSender(),
                transferRequestDto.getAmount(), transferRequestDto.getReceiver());
        AccountResponseDto accountResponse =  accountFacade.transfer(transferRequestDto.getSender(),
                transferRequestDto.getReceiver(), transferRequestDto.getAmount());
        template.convertAndSend("/notifications/data",
                new NotificationResponseDto(principal.getName(), transferRequestDto.getReceiver(),transferRequestDto.getAmount(), OperationType.topUp));
        return accountResponse;
    }
}
