package com.app.services;

import com.app.exception.InsufficientFundsException;
import com.app.models.Account;
import com.app.repository.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
public class AccountService {
    @Autowired
    private AccountDao accountDao;

    public Account update(Long id, Account accountCandidate) {
        Account account = accountDao
                .findById(accountCandidate.getId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found for id={"+id+"}"));
        account.setBalance(accountCandidate.getBalance());
        account.setCurrency(accountCandidate.getCurrency());
        return accountDao.save(account);
    }

    public void delete(Account account) {
        accountDao.delete(account);
    }

    public void deleteById(Long accountId) {
        accountDao.deleteById(accountId);
    }

    public void deleteAll(List<Account> accounts) {
        accountDao.deleteAll(accounts);
    }

    @Transactional(readOnly = true)
    public Account getById(Long id) {
        return accountDao
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found for id={"+id+"}"));
    }

    @Transactional(readOnly = true)
    public Set<Account> getAll() {
        return (Set<Account>) accountDao.findAll();
    }

    @Transactional(readOnly = true)
    public Account getByNumber(String number) {
        return accountDao
                .findByNumber(number)
                .orElseThrow(() -> new EntityNotFoundException("Account not found for number={"+number+"}"));
    }

    public Account withdraw(String number, double amount) {
        Account account = accountDao
                .findByNumber(number)
                .orElseThrow(() -> new EntityNotFoundException("Account not found for number={"+number+"}"));
        if (account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
        }
        return accountDao.save(account);
    }

    public Account topUp(String number, Double amount) {
        Account account = accountDao
                .findByNumber(number)
                .orElseThrow(() -> new EntityNotFoundException("Account not found for number={"+number+"}"));

        account.setBalance(account.getBalance() + amount);
        return accountDao.save(account);
    }

    public Account transfer(String senderNumber, String receiverNumber, Double amount) {
        Account sender = accountDao
                .findByNumber(senderNumber)
                .orElseThrow(() -> new EntityNotFoundException("Sender account not found " +
                                                                "for number={"+senderNumber+"}"));

        if (sender.getBalance() >= amount) {
            Account receiver = accountDao
                    .findByNumber(receiverNumber)
                    .orElseThrow(() -> new EntityNotFoundException("Receiver account not found " +
                                                                     "for number={"+receiverNumber+"}"));
            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);
            accountDao.save(receiver);
        }
        else throw new InsufficientFundsException("Insufficient funds");
        return sender;
    }
}
