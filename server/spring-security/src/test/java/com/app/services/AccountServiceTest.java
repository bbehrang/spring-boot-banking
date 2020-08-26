package com.app.services;

import com.app.models.Account;
import com.app.models.Currency;
import com.app.repository.AccountDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AccountServiceTest {
    @TestConfiguration
    static class AccountServiceTestConfiguration{
        @Bean
        public AccountService accountService(){
            return new AccountService();
        }
    }
    @MockBean
    private AccountDao accountDao;
    @Autowired
    private AccountService accountService;




    @Test
    public void topUpTest(){
        Double balance = 2000.0;
        Double amount = 150.0;
        Account account = new Account("1", Currency.UAH, balance, null);
        given(accountDao.findByNumber(account.getNumber())).willReturn(java.util.Optional.of(account));
        when(accountDao.save(any())).then(acc -> acc.getArguments()[0]);
        Account saved = accountService.topUp("1", amount);
        assertThat(saved.getBalance()).isEqualTo(balance + amount);
    }
    @Test
    public void withdrawTest(){
        Double balance = 2000.0;
        Double amount = 150.0;
        Account account = new Account("1", Currency.UAH, balance, null);
        given(accountDao.findByNumber(account.getNumber())).willReturn(java.util.Optional.of(account));
        when(accountDao.save(any())).then(acc -> acc.getArguments()[0]);
        Account saved = accountService.withdraw("1", amount);
        assertThat(saved.getBalance()).isEqualTo(balance - amount);
    }

    @Test
    public void transfer(){
        Double balance1 = 2000.0;
        Double balance2 = 1000.0;
        Double amount = 150.0;
        Account account1 = new Account("1", Currency.UAH, balance1, null);
        Account account2 = new Account("2", Currency.UAH, balance2, null);

        given(accountDao.findByNumber(account1.getNumber())).willReturn(java.util.Optional.of(account1));
        given(accountDao.findByNumber(account2.getNumber())).willReturn(java.util.Optional.of(account2));

        when(accountDao.save(account1)).then(acc -> acc.getArguments()[0]);
        when(accountDao.save(account2)).then(acc -> {
            account2.setBalance(acc.getArgument(0, Account.class).getBalance());
            return acc.getArguments()[0];
        });
        Account result = accountService.transfer("1","2", amount);
        assertThat(result.getBalance()).isEqualTo(balance1 - amount);
        assertThat(account2.getBalance()).isEqualTo(balance2 + amount);
    }


}
