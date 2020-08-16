package com.app.services;

import com.app.models.Account;
import com.app.repository.AccountDao;
import com.app.repository.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountService {
    @Autowired
    private AccountDao accountDao;

    public boolean delete(long accountId){
        Account account = accountDao.getOne(accountId);
        if(account != null){
            accountDao.delete(account);
            return true;
        }
        return false;
    }

    public Account getByNumber(String number){
        return accountDao.getByNumber(number);
    }

    public Account withdraw(String number, double amount){
        if(amount > 0){
            Account account = accountDao.getByNumber(number);
            if(account != null && account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                return accountDao.save(account);
            }
        }
        return null;
    }

    public Account topUp(String number, double amount){
        if(amount > 0){
            Account account = accountDao.getByNumber(number);
            if (account != null) {
                account.setBalance(account.getBalance() + amount);
                return accountDao.save(account);
            }
        }
        return null;
    }

    public Account transfer(String senderNumber, String receiverNumber, double amount){
        if(amount > 0) {
            Account sender = accountDao.getByNumber(senderNumber);
            if(sender != null && amount > 0 && sender.getBalance() >= amount){
                Account receiver = accountDao.getByNumber(receiverNumber);
                sender.setBalance(sender.getBalance() - amount);
                receiver.setBalance(receiver.getBalance() + amount);
                accountDao.save(receiver);
                return accountDao.save(sender);
            }
        }
        return null;
    }




}
