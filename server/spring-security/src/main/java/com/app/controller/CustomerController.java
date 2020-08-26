package com.app.controller;

import com.app.contstants.ApiConstants;
import com.app.dto.reponse.AccountResponseDto;
import com.app.dto.request.CustomerRequestDto;
import com.app.dto.reponse.CustomerResponseDto;
import com.app.dto.reponse.views.customer.CustomerList;
import com.app.dto.request.AccountRequestDto;
import com.app.dto.request.EmployerRequestDto;
import com.app.dto.request.ValidListDto;
import com.app.dto.request.groups.customer.CustomerListDelete;
import com.app.facade.AccountFacade;
import com.app.facade.CustomerFacade;
import com.app.models.Account;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/" + ApiConstants.API_VERSION + "/customers")
public class CustomerController {

    @Autowired
    private CustomerFacade customerFacade;
    @Autowired
    private AccountFacade accountFacade;

    @JsonView({CustomerList.class})
    @GetMapping
    public List<CustomerResponseDto> getCustomers(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            HttpServletResponse response) {
        Page<CustomerResponseDto> customers = customerFacade.findAll(page, size);
        if(customers != null)
            response.setHeader("totalElements", customers.getTotalElements() + "");
        if(customers != null)
            response.setHeader("totalPages", customers.getTotalPages() + "");
        if (customers != null && customers.hasContent()) return customers.getContent();
        else throw new EntityNotFoundException("Requested page does not exist");

    }

    @PostMapping
    public CustomerResponseDto saveCustomer(@Valid @RequestBody CustomerRequestDto customer) {
        return customerFacade.save(customer);
    }

    @PostMapping("/all")
    public List<CustomerResponseDto> saveCustomersList(@Valid @RequestBody ValidListDto<CustomerRequestDto> customers) {
        return customerFacade.save(customers.getList());
    }


    @DeleteMapping
    public void deleteAllCustomers(@Validated(CustomerListDelete.class) @RequestBody ValidListDto<CustomerRequestDto> customers) {
        customerFacade.deleteAll(customers.getList());
    }

    @GetMapping(value = "/{id}")
    public CustomerResponseDto getCustomerById(@PathVariable("id") long id) {
        return customerFacade.findById(id);
    }

    @PutMapping("/{id}")
    public CustomerResponseDto updateCustomer(@Valid @RequestBody CustomerRequestDto customer,
                                              @PathVariable("id") long id) {
        return customerFacade.updateById(id, customer);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCustomerById(@PathVariable("id") long id) {
        customerFacade.deleteById(id);
    }

    @GetMapping(value = "/{id}/accounts")
    public List<AccountResponseDto> getCustomerAccounts(@PathVariable("id") long id) {
        return customerFacade.getAccounts(id);
    }

    @PostMapping(value = "/{id}/accounts")
    public AccountResponseDto addCustomerAccount(@PathVariable("id") long id,
                                                 @Valid @RequestBody AccountRequestDto account) {
        return customerFacade.addCustomerAccount(id, account);
    }

    @DeleteMapping(value = "/{id}/accounts/{accountId}")
    public void deleteCustomerAccount(@PathVariable("id") Long id,
                                      @PathVariable("accountId") Long accountId) {
        accountFacade.deleteById(accountId);
    }

    @PostMapping(value = "/{id}/employers")
    public CustomerResponseDto addCustomerEmployer(@PathVariable("id") long id,
                                                   @Valid @RequestBody EmployerRequestDto employer) {
        return customerFacade.addCustomerEmployer(id, employer);
    }

    @DeleteMapping(value = "/{customerId}/employers/{employerId}")
    public CustomerResponseDto deleteCustomerEmployer(@PathVariable("customerId") long customerId,
                                                      @PathVariable("employerId") long employerId) {
        return customerFacade.deleteCustomerEmployer(customerId, employerId);
    }
}
