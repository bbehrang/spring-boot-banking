package com.app.services;

import com.app.models.Account;
import com.app.models.Currency;
import com.app.models.Customer;
import com.app.models.Employer;
import com.app.repository.AccountDao;
import com.app.repository.CustomerDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private AccountDao accountDao;

    @Transactional(readOnly = true)
    public Set<Customer> findAll() {
        return (Set<Customer>) customerDao.findAll();
    }

    @Transactional(readOnly = true)
    public Customer findById(Long id) {
        return customerDao.findById(id).orElse(null);
    }
    public Set<Account> getAccounts(Long id){
        Customer customer = customerDao.findById(id).orElse(null);
        return customer.getAccounts();
    }


    public void delete(Customer customer) {
        customerDao.delete(customer);
    }

    public void deleteById(Long id) {
        customerDao.deleteById(id);
    }

    public void deleteAll(Set<Customer> customers) {
        customerDao.deleteAll(customers);
    }

    public Customer save(Customer customer) {
        return customerDao.save(customer);
    }

    public Customer updateById(Long id, Customer customerCandidate) {
        Customer customer = customerDao.findById(customerCandidate.getId()).orElse(null);
        if (customer == null) return null;
        customer.setName(customerCandidate.getName());
        customer.setEmail(customerCandidate.getEmail());
        customer.setAge(customerCandidate.getAge());
        return customerDao.save(customer);
    }

    public Customer addCustomerEmployer(long customerId, Employer employer) {
        Customer customer = customerDao.findById(customerId).orElse(null);
        if (customer == null) return null;
        customer.addEmployer(employer);
        return customerDao.save(customer);
    }

    public Account addCustomerAccount(long customerId, Account accountCandidate) {
        Customer customer = customerDao.findById(customerId).orElse(null);
        if (customer == null) return null;
        Account account = new Account(accountCandidate.getCurrency(), customer);
        account.setBalance(accountCandidate.getBalance());
        return accountDao.save(account);
    }

}
