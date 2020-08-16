package com.app.repository;

import com.app.models.Account;
import com.app.models.Customer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerDao implements Dao<Customer> {

    private final List<Customer> customers = new ArrayList<>();

    @Override
    public Customer update(long id, Customer customerCandidate) {
        Customer foundCustomer =  this.getOne(id);
        if(foundCustomer == null) {
            return null;
        }
        else{
            if(customerCandidate.getAge() != null){
                foundCustomer.setAge(customerCandidate.getAge());
            }
            if(customerCandidate.getEmail() != null){
                foundCustomer.setEmail(customerCandidate.getEmail());
            }
            if(customerCandidate.getName() != null){
                foundCustomer.setName(customerCandidate.getName());
            }
            return foundCustomer;
        }
    }

    @Override
    public Customer save(Customer customer) {
        customers.add(customer);
        return customer;
    }

    @Override
    public boolean delete(Customer customer) {
        return customers.remove(customer);
    }
    @Override
    public void deleteAll(List<Customer> entities) {
        customers.removeAll(entities);
    }

    @Override
    public void saveAll(List<Customer> entities) {
        customers.addAll(entities);
    }

    @Override
    public List findAll() {
        return customers;
    }

    @Override
    public boolean deleteById(long id) {
        return customers.removeIf(customer -> customer.getId().equals(id));
    }

    @Override
    public Customer getOne(long id) {
        return customers
                .stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    //Should this function be in service as is, or should it be in repository?
/*    public Account addAccount(long id, Account account){
        Customer customerToUpdate = this.getOne(id);
        if(customerToUpdate == null) return null;
        else {
            customerToUpdate.getAccounts().add(account);
            return account;
        }
    }*/

    public boolean deleteAccount(long id, long accountId) {
        Customer customerToUpdate = this.getOne(id);
        if(customerToUpdate == null) return false;
        else {
            List<Account> customerAccounts = customerToUpdate.getAccounts();
            return customerAccounts.removeIf(account -> account.getId().equals(accountId));
        }
    }
}
