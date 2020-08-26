package com.app.controller;

import com.app.dto.reponse.AccountResponseDto;
import com.app.dto.reponse.EmployerResponseDto;
import com.app.dto.request.AccountRequestDto;
import com.app.dto.request.EmployerRequestDto;
import com.app.dto.request.SimpleBalanceRequestDto;
import com.app.dto.request.ValidListDto;
import com.app.exception.InsufficientFundsException;
import com.app.facade.AccountFacade;
import com.app.facade.EmployerFacade;
import com.app.models.Currency;
import com.app.models.Employer;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static com.app.contstants.ApiConstants.API_VERSION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureJsonTesters
@WebMvcTest(EmployerController.class)
public class EmployerControllerTest {
    @TestConfiguration
    static class EmployerControllerTestConfiguration {
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
    private EmployerFacade employerFacade;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private CustomerService customerService;

    private JacksonTester<ValidListDto> employerListJSON;
    private JacksonTester<EmployerResponseDto> employerJSON;
    private JacksonTester<EmployerRequestDto> employerRequestJSON;

    @Before
    public void setup() {
        ObjectMapper mapper = new ObjectMapper();
        JacksonTester.initFields(this, mapper);
    }

    @Test
    @WithMockUser(value = "username")
    public void getAll() throws Exception {
        ValidListDto<EmployerResponseDto> employers = new ValidListDto<>();
        for (int i = 0; i < 5; i++) {
            employers.add(new EmployerResponseDto("number" + i, "addr" + i));
        }

        final String employerJson = employerListJSON.write(employers).getJson();

        when(employerFacade.findAll()).thenReturn(employers);

        mvc.perform(get("/api/" + API_VERSION + "/employers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(employerJson))
                .andReturn();
    }
    @Test
    @WithMockUser(value = "username")
    public void getById() throws Exception {
        EmployerResponseDto employerResponse = new EmployerResponseDto("1", "addr");
        final String employerJson = employerJSON.write(employerResponse).getJson();
        when(employerFacade.findById(1L)).thenReturn(employerResponse);

        mvc.perform(get("/api/" + API_VERSION + "/employers/{id}",
                "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(employerJson))
                .andReturn();
    }
    @Test
    @WithMockUser(value = "username")
    public void getById_notFound() throws Exception {
        given(employerFacade.findById(1L)).willThrow(EntityNotFoundException.class);

        mvc.perform(get("/api/" + API_VERSION + "/employers/{id}",
                "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }
    @Test
    @WithMockUser(value = "username")
    public void updateEmployer() throws Exception {
        EmployerResponseDto employerResponse = new EmployerResponseDto("1", "addr");
        EmployerRequestDto employerRequestDto = new EmployerRequestDto(1L, "1234", "addr");
        final String employerJson = employerJSON.write(employerResponse).getJson();
        final String employerRequestJson = employerRequestJSON.write(employerRequestDto).getJson();
        when(employerFacade.updateById(1L, employerRequestDto)).thenReturn(employerResponse);

        mvc.perform(put("/api/" + API_VERSION + "/employers/{id}",
                "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employerRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(employerJson))
                .andReturn();
    }
}
