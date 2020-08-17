package com.app.controller;

import com.app.contstants.ApiConstants;
import com.app.dto.reponse.AccountResponseDto;
import com.app.dto.request.CustomerRequestDto;
import com.app.dto.reponse.CustomerResponseDto;
import com.app.dto.reponse.views.customer.CustomerList;
import com.app.dto.request.AccountRequestDto;
import com.app.dto.request.EmployerRequestDto;
import com.app.facade.AccountFacade;
import com.app.facade.CustomerFacade;
import com.app.models.Account;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/" + ApiConstants.API_VERSION + "/customers")
public class CustomerController {

    @Autowired
    private CustomerFacade customerFacade;
    @Autowired
    private AccountFacade accountFacade;

    @JsonView({CustomerList.class})
    @GetMapping
    public List<CustomerResponseDto> getCustomers(){
        return customerFacade.findAll();
    }
    @PostMapping
    public CustomerResponseDto saveCustomer(@Valid @RequestBody CustomerRequestDto customerCandidate,
                                            HttpServletResponse response){
        CustomerResponseDto customer = customerFacade.save(customerCandidate);
        if(customer == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else{
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
        return customer;
    }
    @DeleteMapping
    public void deleteAllCustomers(@Valid @RequestBody List<CustomerRequestDto> customers){
        customerFacade.deleteAll(customers);
    }
    @GetMapping(value = "/{id}")
    public CustomerResponseDto getCustomerById(@PathVariable("id") long id, HttpServletResponse response){
        CustomerResponseDto customer = customerFacade.findById(id);
        if(customer == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return customer;
    }
    @PutMapping("/{id}")
    public CustomerResponseDto updateCustomer(@Valid @RequestBody CustomerRequestDto customerCandidate,
                                              @PathVariable("id") long id,
                                              HttpServletResponse response){
        CustomerResponseDto customer = customerFacade.updateById(id, customerCandidate);
        if(customer == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return customer;
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCustomerById(@PathVariable("id") long id, HttpServletResponse response){
         customerFacade.deleteById(id);
    }

    @GetMapping(value = "/{id}/accounts")
    public List<AccountResponseDto> getCustomerAccounts(@PathVariable("id") long id){
        return customerFacade.getAccounts(id);
    }
    @PostMapping(value = "/{id}/accounts")
    public AccountResponseDto addCustomerAccount(@PathVariable("id") long id,
                                      @Valid @RequestBody AccountRequestDto account,
                                      HttpServletResponse response){
        AccountResponseDto addedAccount = customerFacade.addCustomerAccount(id, account);
        if (addedAccount == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
        return addedAccount;
    }
    @DeleteMapping(value = "/{id}/accounts/{accountId}")
    public void deleteCustomerAccount(@PathVariable("id") Long id,
                                      @PathVariable("accountId") Long accountId,
                                      HttpServletResponse response){
        accountFacade.deleteById(accountId);
    }

    @PostMapping(value = "/{id}/employers")
    public CustomerResponseDto addCustomerEmployer(@PathVariable("id") long id,
                                        @Valid @RequestBody EmployerRequestDto employer,
                                        HttpServletResponse response){
        CustomerResponseDto customer = customerFacade.addCustomerEmployer(id, employer);
        if(customer == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
        return customer;

    }
}
