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
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/" + ApiConstants.API_VERSION + "/accounts")
public class AccountController {

    @Autowired
    private AccountFacade accountFacade;

    @GetMapping(value = "/{number}")
    public AccountResponseDto getAccount(@PathVariable("number") String number) {
        log.info("Getting account by number {}", number);
        return accountFacade.getAccountByNumber(number);
    }

    @PutMapping(value = "/withdrawal/{number}")
    public AccountResponseDto withdraw(@PathVariable("number") String number,
                                       @Valid @RequestBody SimpleBalanceRequestDto balanceRequestDto) {
        log.info("Withdrawing from account with number {}, amount {}", number, balanceRequestDto.getAmount());
        return accountFacade.withdraw(number, balanceRequestDto.getAmount());
    }

    @PutMapping(value = "/top-up/{number}")
    public AccountResponseDto topUp(@PathVariable("number") String number,
                                    @Valid @RequestBody SimpleBalanceRequestDto simpleBalanceRequestDto) {
        log.info("Topping account  with number {}, amount {}", number, simpleBalanceRequestDto.getAmount());

        AccountResponseDto response =  accountFacade.topUp(number, simpleBalanceRequestDto.getAmount());
        this.topUpNotification(simpleBalanceRequestDto, number);
        return response;
    }

    @PutMapping(value = "/transfer")
    public AccountResponseDto topUp(@Valid @RequestBody TransferRequestDto transferRequestDto) {
        log.info("Transferring from  account with number {}, amount {}, " +
                "to account with number {}", transferRequestDto.getSender(),
                transferRequestDto.getAmount(), transferRequestDto.getReceiver());
        return accountFacade.transfer(transferRequestDto.getSender(),
                transferRequestDto.getReceiver(), transferRequestDto.getAmount());
    }
    @MessageMapping("/top-up")
    @SendTo("/notifications/data")
    private NotificationResponseDto topUpNotification(@Valid @RequestBody SimpleBalanceRequestDto simpleBalanceRequestDto,
                                                      String number){
        //log.info("web socket opened for top up for ");
        return NotificationResponseDto
                .builder()
                .amount(simpleBalanceRequestDto.getAmount())
                .operation(OperationType.topUp)
                .build();
    }
}
