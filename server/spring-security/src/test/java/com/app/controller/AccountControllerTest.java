package com.app.controller;

import com.app.dto.reponse.AccountResponseDto;
import com.app.dto.request.AccountRequestDto;
import com.app.dto.request.SimpleBalanceRequestDto;
import com.app.exception.InsufficientFundsException;
import com.app.facade.AccountFacade;
import com.app.models.Currency;
import com.app.security.UserDetailsServiceImpl;
import com.app.services.AccountService;
import com.app.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import javax.persistence.EntityNotFoundException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static com.app.contstants.ApiConstants.API_VERSION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureJsonTesters
@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    @TestConfiguration
    static class AccountControllerTestConfiguration {
        @Bean
        public UserDetailsServiceImpl userDetailsService() {
            return new UserDetailsServiceImpl();
        }

    }

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AccountFacade accountFacade;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private CustomerService customerService;


    private JacksonTester<AccountResponseDto> accountJSON;
    private JacksonTester<SimpleBalanceRequestDto> balanceJSON;

    @Before
    public void setup() {
        ObjectMapper mapper = new ObjectMapper();
        JacksonTester.initFields(this, mapper);
    }

    @WithMockUser(value = "username")
    @Test
    public void getAccountById() throws Exception {
        AccountResponseDto responseDto = new AccountResponseDto("1", Currency.UAH, 1000.0);
        final String accountDto = accountJSON.write(responseDto).getJson();
        given(accountFacade.getAccountByNumber(responseDto.getNumber())).willReturn(responseDto);

        mvc.perform(get("/api/" + API_VERSION + "/accounts/{number}",
                "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(accountDto))
                .andReturn();
    }

    @WithMockUser(value = "username")
    @Test
    public void getAccountById_notFound() throws Exception {
        given(accountFacade.getAccountByNumber("2")).willThrow(EntityNotFoundException.class);

        mvc.perform(get("/api/" + API_VERSION + "/accounts/{number}",
                "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @WithMockUser(value = "username")
    @Test
    public void withdraw() throws Exception {
        AccountResponseDto responseDto = new AccountResponseDto("1", Currency.UAH, 1000.0);
        SimpleBalanceRequestDto balanceRequestDto = new SimpleBalanceRequestDto(150.0);
        responseDto.setBalance(responseDto.getBalance() - balanceRequestDto.getAmount());
        final String accountDto = accountJSON.write(responseDto).getJson();
        final String amountDto = balanceJSON.write(balanceRequestDto).getJson();

        when(accountFacade.
                withdraw(responseDto.getNumber(), 150.0))
                .thenReturn(responseDto);

        MvcResult mvcResult = mvc.perform(put("/api/" + API_VERSION + "/accounts/withdrawal/{number}",
                "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(amountDto))
                .andExpect(status().isOk())
                .andExpect(content().json(accountDto))
                .andReturn();
    }
    @WithMockUser(value = "username")
    @Test
    public void withdraw_insufficientFunds() throws Exception {
        AccountResponseDto responseDto = new AccountResponseDto("1", Currency.UAH, 1000.0);
        SimpleBalanceRequestDto balanceRequestDto = new SimpleBalanceRequestDto(2000.0);
        final String accountDto = accountJSON.write(responseDto).getJson();
        final String amountDto = balanceJSON.write(balanceRequestDto).getJson();

        given(accountFacade.
                withdraw(responseDto.getNumber(), balanceRequestDto.getAmount()))
                .willThrow(InsufficientFundsException.class);

        MvcResult mvcResult = mvc.perform(put("/api/" + API_VERSION + "/accounts/withdrawal/{number}",
                "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(amountDto))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @WithMockUser(value = "username")
    @Test
    public void topUp() throws Exception {
        AccountResponseDto responseDto = new AccountResponseDto("1", Currency.UAH, 1000.0);
        SimpleBalanceRequestDto balanceRequestDto = new SimpleBalanceRequestDto(150.0);
        responseDto.setBalance(responseDto.getBalance() + balanceRequestDto.getAmount());
        final String accountDto = accountJSON.write(responseDto).getJson();
        final String amountDto = balanceJSON.write(balanceRequestDto).getJson();

        when(accountFacade.
                topUp(responseDto.getNumber(), 150.0))
                .thenReturn(responseDto);

        MvcResult mvcResult = mvc.perform(put("/api/" + API_VERSION + "/accounts/top-up/{number}",
                "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(amountDto))
                .andExpect(status().isOk())
                .andExpect(content().json(accountDto))
                .andReturn();
    }
}
