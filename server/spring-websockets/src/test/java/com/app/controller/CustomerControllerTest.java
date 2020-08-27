package com.app.controller;

import com.app.dto.reponse.AccountResponseDto;
import com.app.dto.reponse.CustomerResponseDto;
import com.app.dto.reponse.EmployerResponseDto;
import com.app.dto.request.*;
import com.app.exception.InsufficientFundsException;
import com.app.facade.AccountFacade;
import com.app.facade.CustomerFacade;
import com.app.facade.EmployerFacade;
import com.app.models.Currency;
import com.app.models.Customer;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @TestConfiguration
    static class CustomerServiceTestConfiguration {
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
    private CustomerFacade customerFacade;
    @MockBean
    private AccountFacade accountFacade;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private CustomerService customerService;

    private JacksonTester<List<CustomerResponseDto>> customerListJSON;
    private JacksonTester<CustomerResponseDto> customerResponseJSON;
    private JacksonTester<CustomerRequestDto> customerRequestJSON;

    @Before
    public void setup() {
        ObjectMapper mapper = new ObjectMapper();
        JacksonTester.initFields(this, mapper);
    }

    @Test
    @WithMockUser(value = "username")
    public void getAll() throws Exception {
        List<CustomerResponseDto> customers = new ArrayList<>();
        int count = 10;
        for (int i = 0; i < count; i++) {
            customers.add(new CustomerResponseDto(1L, "name" + i, "email" + i,
                    22, "phone" + i,
                    null, null));
        }
        Pageable pageable = PageRequest.of(0, 5);
        Page<CustomerResponseDto> pages = new PageImpl<>(customers, pageable, 10);
        when(customerFacade.findAll(0, 5)).thenReturn(pages);

        final String customerListResponse = customerListJSON.write(pages.getContent()).getJson();
        mvc.perform(get("/api/" + API_VERSION + "/customers?page=0&size=5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(value = "username")
    public void getById() throws Exception {
        CustomerResponseDto customerResponseDto = new CustomerResponseDto(1L, "1",
                "email", 22, "phone23", null, null);
        final String customerJsonResult = customerResponseJSON.write(customerResponseDto).getJson();
        when(customerFacade.findById(1L)).thenReturn(customerResponseDto);

        mvc.perform(get("/api/" + API_VERSION + "/customers/{id}",
                "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(customerJsonResult))
                .andReturn();
    }

    @Test
    @WithMockUser(value = "username")
    public void getById_notFound() throws Exception {
        given(customerFacade.findById(1L)).willThrow(EntityNotFoundException.class);

        mvc.perform(get("/api/" + API_VERSION + "/customers/{id}",
                "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @WithMockUser(value = "username")
    public void updateCustomer() throws Exception {
        CustomerResponseDto customerResponse = new CustomerResponseDto(1L, "1",
                "email", 22, "phone23", null, null);
        CustomerRequestDto customerRequestDto = new CustomerRequestDto(1L, "name", "Password1234", "email@email.com",
                "+(380)999999999", 22);
        final String resultJson = customerResponseJSON.write(customerResponse).getJson();
        final String requestJson = customerRequestJSON.write(customerRequestDto).getJson();
        when(customerFacade.updateById(1L, customerRequestDto)).thenReturn(customerResponse);

        mvc.perform(put("/api/" + API_VERSION + "/customers/{id}",
                "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(resultJson))
                .andReturn();
    }
    @Test
    @WithMockUser(value = "username")
    public void save() throws Exception {
        CustomerResponseDto customerResponse = new CustomerResponseDto(1L, "1",
                "email", 22, "phone23", null, null);
        CustomerRequestDto customerRequestDto = new CustomerRequestDto(1L, "name", "Password1234", "email@email.com",
                "+(380)999999999", 22);
        final String resultJson = customerResponseJSON.write(customerResponse).getJson();
        final String requestJson = customerRequestJSON.write(customerRequestDto).getJson();
        when(customerFacade.save(customerRequestDto)).thenReturn(customerResponse);

        mvc.perform(post("/api/" + API_VERSION + "/customers",
                "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(resultJson))
                .andReturn();
    }
    @Test
    @WithMockUser(value = "username")
    public void save_invalidBody() throws Exception {
        CustomerRequestDto customerRequestDto = new CustomerRequestDto(1L, "n", "Pass", "emailcom",
                "+(380345", 12);
        final String requestJson = customerRequestJSON.write(customerRequestDto).getJson();
        mvc.perform(post("/api/" + API_VERSION + "/customers",
                "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
