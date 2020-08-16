package com.app.controller;

import com.app.contstants.ApiConstants;
import com.app.models.Account;
import com.app.models.Customer;
import com.app.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/" + ApiConstants.API_VERSION + "/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "")
    public List<Customer> getAll(){
        return customerService.findAll();
    }
    @PostMapping(value = "")
    public ResponseEntity<Customer> save(@RequestBody Customer customer) throws URISyntaxException {
        Customer savedCustomer = customerService.save(customer);
        if(customer == null){
            return ResponseEntity.notFound().build();
        }
        else{
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedCustomer.getId())
                    .toUri();

            return ResponseEntity.created(uri)
                    .body(savedCustomer);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id){
        Customer customer = customerService.getOne(id);
        if(customer == null){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(customer);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id,
                                         @RequestBody Customer customer){
        Customer updatedCustomer = customerService.update(id, customer);
        if(updatedCustomer == null) {
            return ResponseEntity.notFound().build();
        } else{
            return ResponseEntity.ok(updatedCustomer);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteCustomer(@PathVariable("id") long id){
        boolean isDeleted =  customerService.deleteById(id);
        if(!isDeleted){
            return ResponseEntity.notFound().build();
        }
        else return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}/accounts")
    public ResponseEntity<List<Account>> getCustomerAccountById(@PathVariable("id") long id){
        List<Account> accounts = customerService.getCustomerAccounts(id);
        if(accounts == null) {
            return ResponseEntity.notFound().build();
        } else{
            return ResponseEntity.ok(accounts);
        }

    }
    @GetMapping(value = "/{id}/accounts/{accountId}")
    public ResponseEntity<Account> getCustomerAccount(@PathVariable("id") long id,
                                      @PathVariable("accountId") long accountId){
        Account account = customerService.getCustomerAccountById(id, accountId);
        if(account == null) {
            return ResponseEntity.notFound().build();
        } else{
            return ResponseEntity.ok(account);
        }
    }
    @PostMapping(value = "/{id}/accounts")
    public ResponseEntity<Account> addCustomerAccount(@PathVariable("id") long id,
                                  @RequestBody Account account) throws URISyntaxException{
        Account addedAccount =  customerService.addCustomerAccount(id, account);
        if(addedAccount == null){
            return ResponseEntity.notFound().build();
        }
        else{
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(addedAccount.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(addedAccount);
        }
    }
    @DeleteMapping(value = "/{id}/accounts/{accountId}")
    public ResponseEntity<Boolean> deleteCustomerAccount(@PathVariable("id") long id,
                                      @PathVariable("accountId") long accountId){
        boolean isDeleted =  customerService.deleteCustomerAccount(id, accountId);;
        if(!isDeleted){
            return ResponseEntity.notFound().build();
        }
        else return ResponseEntity.noContent().build();
    }
}
