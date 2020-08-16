package com.app.controller;

import com.app.contstants.ApiConstants;
import com.app.models.Account;
import com.app.models.Customer;
import com.app.models.Employer;
import com.app.services.AccountService;
import com.app.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/" + ApiConstants.API_VERSION + "/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccountService accountService;

    @GetMapping
    public Set<Customer> getCustomers(){
        return customerService.findAll();
    }
    @PostMapping
    public Customer saveCustomer(@RequestBody Customer customerCandidate, HttpServletResponse response){
        Customer customer = customerService.save(customerCandidate);
        if(customer == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else{
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
        return customer;
    }
    @DeleteMapping
    public void deleteAllCustomers(@RequestBody Set<Customer> customers){
        customerService.deleteAll(customers);
    }
    @GetMapping(value = "/{id}")
    public Customer getCustomerById(@PathVariable("id") long id, HttpServletResponse response){
        Customer customer = customerService.findById(id);
        if(customer == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return customer;
    }
    @PutMapping("/{id}")
    public Customer updateCustomer(@RequestBody Customer customerCandidate, @PathVariable("id") long id,
                                   HttpServletResponse response){
        Customer customer = customerService.updateById(id, customerCandidate);
        if(customer == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        else if(customer == customerCandidate){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        return customer;
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCustomerById(@PathVariable("id") long id, HttpServletResponse response){
         customerService.deleteById(id);
    }

    @GetMapping(value = "/{id}/accounts")
    public Set<Account> getCustomerAccounts(@PathVariable("id") long id){
        return customerService.getAccounts(id);
    }
    @PostMapping(value = "/{id}/accounts")
    public Account addCustomerAccount(@PathVariable("id") long id,
                                      @RequestBody Account account,
                                      HttpServletResponse response){
        Account addedAccount = customerService.addCustomerAccount(id, account);
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
        accountService.deleteById(accountId);
    }

    @PostMapping(value = "/{id}/employers")
    public Customer addCustomerEmployer(@PathVariable("id") long id,
                                        @RequestBody Employer employer,
                                        HttpServletResponse response){
        Customer customer = customerService.addCustomerEmployer(id, employer);
        if(customer == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
        return customer;

    }
}
