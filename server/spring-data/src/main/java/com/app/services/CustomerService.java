package com.app.services;

import com.app.models.Account;
import com.app.models.Currency;
import com.app.models.Customer;

import com.app.models.Employer;
import com.app.repository.AccountDao;
import com.app.repository.CustomerDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;
    @Autowired AccountDao accountDao;

    public Customer getOne(long id) {
        return customerDao.getOne(id);
    }

    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    public List<Account> getAccounts(long id){
        return customerDao.getOne(id).getAccounts();
    }

    public Account getCustomerAccount(long accountId){
        return accountDao.getOne(accountId);
    }

    public Account addCustomerAccount(long customerId, Account accountCandidate){
        Customer customer = customerDao.getOne(customerId);
        if(customer != null){
            Account account = new Account(accountCandidate.getCurrency(), customer);
            account.setBalance(accountCandidate.getBalance());
            return accountDao.save(account);
        }
        return null;
    }

    public Customer save(Customer customer) {
        if(customer.getAge() != null
                && customer.getEmail() != null
                && customer.getName() != null
        ){
            return customerDao.save(customer);
        }
        return null;

    }
    public Customer update(long id, Customer customerCandidate) {

        if(customerCandidate.getAge() != null
                && customerCandidate.getEmail() != null
                && customerCandidate.getName() != null
        ){
            Customer foundCustomer =  this.getOne(id);
            if(foundCustomer == null) {
                return null;
            }
            foundCustomer.setAge(customerCandidate.getAge());
            foundCustomer.setEmail(customerCandidate.getEmail());
            foundCustomer.setName(customerCandidate.getName());
            return customerDao.save(foundCustomer);
        }
        return customerCandidate; // Used for bad request response until validation is implemented
    }



    public void saveAll(List<Customer> customers) {
        customerDao.saveAll(customers);
    }

    public boolean deleteById(long id) {
        Customer customer = customerDao.getOne(id);
        if(customer == null) return false;
        customerDao.delete(customer);
        return true;
    }

    public void deleteAll(List<Customer> customers) {
        customerDao.deleteAll(customers);
    }

    public Customer addCustomerEmployer(long customerId, Employer employer){
        Customer customer = customerDao.getOne(customerId);
        if(customer == null) return null;
        customer.addEmployer(employer);
        return customerDao.save(customer);
    }

}
