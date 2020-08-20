package com.app.services;

import com.app.models.Account;
import com.app.models.Currency;
import com.app.models.Customer;
import com.app.models.Employer;
import com.app.repository.AccountDao;
import com.app.repository.CustomerDao;

import com.app.repository.EmployerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private EmployerDao employerDao;

    @Transactional(readOnly = true)
    public Page<Customer> findAll(int page, int size) {
        return customerDao.findAll(PageRequest.of(page, size));
    }

    @Transactional(readOnly = true)
    public Customer findById(Long id) {
        return customerDao
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found for id={" + id + "}"));

    }

    public Set<Account> getAccounts(Long id) {
        Customer customer = customerDao
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found for id={" + id + "}"));
        return customer.getAccounts();
    }


    public void delete(Customer customer) {
        customerDao.delete(customer);
    }

    public void deleteById(Long id) {
        customerDao.deleteById(id);
    }

    public void deleteAll(List<Customer> customers) {
        customers.forEach(customer -> customerDao.deleteById(customer.getId()));
    }

    public Customer save(Customer customer) {
        return customerDao.save(customer);
    }

    public List<Customer> saveAll(List<Customer> customers) {
        return (List<Customer>) customerDao.saveAll(customers);
    }

    public Customer updateById(Long id, Customer customerCandidate) {
        Customer customer = customerDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found for id={" + id + "}"));
        if (customerCandidate.getName() != null)
            customer.setName(customerCandidate.getName());
        if (customerCandidate.getEmail() != null)
            customer.setEmail(customerCandidate.getEmail());
        if (customerCandidate.getAge() != null)
            customer.setAge(customerCandidate.getAge());
        if (customerCandidate.getPassword() != null)
            customer.setPassword(customerCandidate.getPassword());
        if (customerCandidate.getPhone() != null)
            customer.setPhone(customerCandidate.getPhone());
        return customerDao.save(customer);
    }

    public Customer addCustomerEmployer(long customerId, Employer employerCandidate) {
        Customer customer = customerDao
                .findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found for id={" + customerId + "}"));
       /* Employer employer = employerDao
                .findById(employerCandidate.getId())
                .orElseThrow(() -> new EntityNotFoundException("Employer not found for id={" + customerId + "}"));*/

        customer.addEmployer(employerCandidate);
        //employer.getCustomers().add(customer);
        return customerDao.save(customer);
    }

    public Account addCustomerAccount(long customerId, Account accountCandidate) {
        Customer customer = customerDao
                .findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found for id={" + customerId + "}"));

        Account account = new Account(accountCandidate.getCurrency(), customer);
        account.setBalance(accountCandidate.getBalance());
        return accountDao.save(account);
    }
    public Customer deleteCustomerEmployer(Long customerId, Long employerId){
        Customer customer = customerDao
                .findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found for id={" + customerId + "}"));

        customer.getEmployers().removeIf(employer -> employer.getId().equals(employerId));
        return customerDao.save(customer);
    }

}
