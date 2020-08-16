package com.app.facade;


import com.app.dto.reponse.AccountResponseDto;
import com.app.models.Account;
import com.app.services.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountFacade {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccountService accountService;


    public AccountResponseDto getAccountByNumber(String number){
        return modelMapper.map(accountService.getByNumber(number), AccountResponseDto.class);
    }
    public AccountResponseDto topUp(String number, double amount){
        return modelMapper.map(accountService.topUp(number, amount), AccountResponseDto.class);
    }
    public AccountResponseDto withdraw(String number, double amount){
        return modelMapper.map(accountService.withdraw(number, amount), AccountResponseDto.class);
    }
    public AccountResponseDto transfer(String sender, String receiver, double amount){
        return modelMapper.map(accountService.transfer(sender, receiver, amount), AccountResponseDto.class);
    }
}
