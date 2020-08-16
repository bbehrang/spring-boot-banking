package com.app.services;

import com.app.models.Account;
import com.app.models.Currency;
import com.app.models.Customer;
import com.app.repository.AccountDao;
import com.app.repository.CustomerDao;
import com.app.repository.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private AccountDao accountDao;


    @PostConstruct
    private void init(){
        for (int i = 0; i < 5; i++) {
            Customer customer = new Customer("name" + i, "email"+i, i);
            Account account = new Account(Currency.UAH, customer);
            Account account1 = new Account(Currency.USD, customer);
            Account account2 = new Account(Currency.GBP, customer);
            customer.getAccounts().add(account);
            customer.getAccounts().add(account1);
            customer.getAccounts().add(account2);
            customerDao.save(customer);
            accountDao.save(account);
            accountDao.save(account1);
            accountDao.save(account2);
        }
    }

    public Customer save(Customer customerCandidate){
        Customer customer = new Customer(customerCandidate.getName(),
                customerCandidate.getEmail(), customerCandidate.getAge());
        return customerDao.save(customer);
    }
    public Customer update(Long id, Customer customer){
        return customerDao.update(id, customer);
    }
    public boolean delete(Customer customer){
        return customerDao.delete(customer);
    }
    public void deleteAll(List<Customer> customers){
        customerDao.deleteAll(customers);
    }
    public void saveAll(List<Customer> customers){
        customerDao.saveAll(customers);
    }
    public  List<Customer> findAll(){
        return customerDao.findAll();
    }
    public boolean deleteById(long id){
        return customerDao.deleteById(id);
    }
    public Customer getOne(long id){
        return customerDao.getOne(id);
    }
    public List<Account> getCustomerAccounts(long id){
        return customerDao.getOne(id).getAccounts();
    }
    public Account getCustomerAccountById(long customerId, long accountId){
        Customer customer = customerDao.getOne(customerId);
        return customer
                .getAccounts()
                .stream()
                .filter(account -> account.getId().equals(accountId))
                .findFirst()
                .orElse(null);
    }
    public Account addCustomerAccount(long id, Account account){
        Customer customer = customerDao.getOne(id);
        Account accountToAdd = new Account(account.getCurrency(), customer);
        customer.getAccounts().add(accountToAdd);
        return accountDao.save(accountToAdd);
    }

    public boolean deleteCustomerAccount(long id, long accountId) {
        return customerDao.deleteAccount(id, accountId);
    }
}
