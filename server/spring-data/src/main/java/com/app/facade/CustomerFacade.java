package com.app.facade;

import com.app.dto.reponse.AccountResponseDto;
import com.app.dto.request.CustomerRequestDto;
import com.app.dto.reponse.CustomerResponseDto;
import com.app.dto.request.AccountRequestDto;
import com.app.dto.request.EmployerRequestDto;
import com.app.models.Account;
import com.app.models.Customer;
import com.app.models.Employer;
import com.app.services.CustomerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CustomerFacade {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private ModelMapper modelMapper;

    public List<CustomerResponseDto> findAll() {
        return modelMapper.map((List<Customer>) customerService.findAll(),
                new TypeToken<List<CustomerResponseDto>>(){}.getType());
    }

    public CustomerResponseDto findById(Long id) {
        Customer customer = customerService.findById(id);
        CustomerResponseDto  cs= modelMapper.map(customer, CustomerResponseDto.class);
        return cs;
    }
    public List<AccountResponseDto> getAccounts(Long id){
        return modelMapper.map(customerService.getAccounts(id),
                new TypeToken<List<AccountResponseDto>>(){}.getType());
    }

    public void delete(CustomerRequestDto customer) {
        customerService.delete(modelMapper.map(customer, Customer.class));
    }

    public void deleteById(Long id) {
        customerService.deleteById(id);
    }

    public void deleteAll(List<CustomerRequestDto> customers) {
        customerService.deleteAll(modelMapper.map(customers,
                new TypeToken<List<Customer>>(){}.getType()));
    }

    public CustomerResponseDto save(CustomerRequestDto customerCandidate) {
        return modelMapper.map(customerService.save(modelMapper.map(customerCandidate, Customer.class)),
                CustomerResponseDto.class);
    }

    public CustomerResponseDto updateById(Long id, CustomerRequestDto customerCandidate) {
        return modelMapper.map(customerService.updateById(id, modelMapper.map(customerCandidate, Customer.class)),
                CustomerResponseDto.class);
    }

    public CustomerResponseDto addCustomerEmployer(long customerId, EmployerRequestDto employer) {
        return modelMapper.map(customerService.addCustomerEmployer(customerId, modelMapper.map(employer, Employer.class)),
                CustomerResponseDto.class);
    }

    public AccountResponseDto addCustomerAccount(long customerId, AccountRequestDto accountCandidate) {
        return modelMapper.map(customerService.addCustomerAccount(customerId, modelMapper.map(accountCandidate, Account.class)),
                AccountResponseDto.class);
    }
}
