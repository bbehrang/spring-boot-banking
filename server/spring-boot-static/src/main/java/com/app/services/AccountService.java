package com.app.services;

import com.app.models.Account;
import com.app.repository.AccountDao;
import com.app.repository.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountDao accountDao;


    public Account save(Account account){
        return accountDao.save(account);
    }
    public boolean delete(Account account){
        return accountDao.delete(account);
    }
    public void deleteAll(List<Account> accounts){
        accountDao.deleteAll(accounts);
    }
    public void saveAll(List<Account> accounts){
        accountDao.saveAll(accounts);
    }
    public List<Account> findAll(){
        return accountDao.findAll();
    }
    public boolean deleteById(long id){
        return accountDao.deleteById(id);
    }
    public Account getOne(long id){
        return accountDao.getOne(id);
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
