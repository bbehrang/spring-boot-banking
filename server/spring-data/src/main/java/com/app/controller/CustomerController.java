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
import org.springframework.validation.annotation.Validated;
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
    public CustomerResponseDto saveCustomer(@Valid @RequestBody CustomerRequestDto customer){
        return customerFacade.save(customer);
    }
    @DeleteMapping
    public void deleteAllCustomers(@Valid @RequestBody List<CustomerRequestDto> customers){
        customerFacade.deleteAll(customers);
    }
    @GetMapping(value = "/{id}")
    public CustomerResponseDto getCustomerById(@PathVariable("id") long id){
        return customerFacade.findById(id);
    }
    @PutMapping("/{id}")
    public CustomerResponseDto updateCustomer(@Valid @RequestBody CustomerRequestDto customer,
                                              @PathVariable("id") long id){
        return customerFacade.updateById(id, customer);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCustomerById(@PathVariable("id") long id){
         customerFacade.deleteById(id);
    }

    @GetMapping(value = "/{id}/accounts")
    public List<AccountResponseDto> getCustomerAccounts(@PathVariable("id") long id){
        return customerFacade.getAccounts(id);
    }
    @PostMapping(value = "/{id}/accounts")
    public AccountResponseDto addCustomerAccount(@PathVariable("id") long id,
                                      @Valid @RequestBody AccountRequestDto account){
        return customerFacade.addCustomerAccount(id, account);
    }
    @DeleteMapping(value = "/{id}/accounts/{accountId}")
    public void deleteCustomerAccount(@PathVariable("id") Long id,
                                      @PathVariable("accountId") Long accountId){
        accountFacade.deleteById(accountId);
    }

    @PostMapping(value = "/{id}/employers")
    public CustomerResponseDto addCustomerEmployer(@PathVariable("id") long id,
                                        @Valid @RequestBody EmployerRequestDto employer){
        return customerFacade.addCustomerEmployer(id, employer);
    }
}
