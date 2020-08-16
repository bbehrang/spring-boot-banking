package com.app.services;

import com.app.models.Account;
import com.app.repository.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service

public class AccountService {
    @Autowired
    private AccountDao accountDao;

    public Account save(Account accountCandidate) {
        Account account = accountDao.findByNumber(accountCandidate.getNumber()).orElse(null);
        if (account == null) return null;
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
        return accountDao.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Set<Account> getAll() {
        return (Set<Account>) accountDao.findAll();
    }

    @Transactional(readOnly = true)
    public Account getByNumber(String number) {
        return accountDao.findByNumber(number).orElse(null);
    }

    public Account withdraw(String number, double amount) {
        Account account = accountDao.findByNumber(number).orElse(null);
        if (account == null) return null;
        if (account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
        }
        return accountDao.save(account);
    }

    public Account topUp(String number, Double amount) {
        Account account = accountDao.findByNumber(number).orElse(null);
        if (account == null) return null;
        account.setBalance(account.getBalance() + amount);
        return accountDao.save(account);
    }

    public Account transfer(String senderNumber, String receiverNumber, Double amount) {
        Account sender = accountDao.findByNumber(senderNumber).orElse(null);
        if (sender == null) return null;
        if (sender.getBalance() >= amount) {
            Account receiver = accountDao.findByNumber(receiverNumber).orElse(null);
            if (receiver == null) return null;
            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);
            accountDao.save(receiver);
        }
        return accountDao.save(sender);
    }
}
