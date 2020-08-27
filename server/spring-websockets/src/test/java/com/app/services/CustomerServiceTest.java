package com.app.services;

import com.app.models.Account;
import com.app.models.Customer;
import com.app.repository.AccountDao;
import com.app.repository.CustomerDao;
import com.app.repository.EmployerDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CustomerServiceTest {
    @TestConfiguration
    static class CustomerServiceTestConfiguration{
        @Bean
        public CustomerService customerService(){
            return new CustomerService();
        }
        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder(){
            return new BCryptPasswordEncoder();
        }
    }
    @MockBean
    private AccountDao accountDao;
    @MockBean
    private CustomerDao customerDao;
    @MockBean
    private EmployerDao employerDao;

    @Autowired
    private CustomerService customerService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Test
    public void save(){
        Customer customer = new Customer("nameTest", "emailTest",
                22, "pass", "1234", null, null);
        when(customerDao.save(customer)).thenReturn(customer);
        assertThat(customerService.save(customer)).isEqualTo(customer);
    }
    @Test
    public void saveAll(){
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            customers.add(new Customer("name" + i, "email" + i,
                    22, "pass", "1234", null, null));
        }
        when(customerDao.saveAll(customers)).thenReturn(customers);
        assertThat(customerService.saveAll(customers)).isEqualTo(customers);
    }
    @Test
    public void findByEmail(){
        Customer customer = new Customer("nameTest", "emailTest",
                22, "pass", "1234", null, null);
        when(customerDao.findByEmail("emailTest"))
                .thenReturn(java.util.Optional.of(customer));
        assertThat(customerDao.findByEmail("emailTest")).isEqualTo(java.util.Optional.of(customer));
    }
    @Test
    public void findAll(){
        List<Customer> customers = new ArrayList<>();
        int count = 10;
        for (int i = 0; i < count; i++) {
            customers.add(new Customer("name" + i, "email" + i,
                    22, "pass" + i, "1234" + i,
                    null, null));
        }
        Pageable pageable = PageRequest.of(1, 5);
        Page<Customer> pages = new PageImpl<>(customers, pageable, count);
        when(customerDao.findAll(pageable)).thenReturn(pages);

        Page<Customer> result = customerService.findAll(1,5 );
        assertThat(result.getTotalElements()).isEqualTo(count);
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getContent().containsAll(customers));

    }
    @Test
    public void update(){
        Customer customer = new Customer("nameTest", "emailTest",
                22, "pass", "1234", null, null);
        when(customerDao.findById(1L))
                .thenReturn(java.util.Optional.of(customer));
        when(customerDao.save(customer)).then(res -> res.getArgument(0, Customer.class));
        assertThat(customerService.updateById(1L, customer)).isEqualTo(customer);
    }
}
