package com.app.repository;


import com.app.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountDao implements Dao<Account> {

    private final List<Account> accounts = new ArrayList<>();

    @Override
    public Account save(Account accountCandidate) {
      accounts.stream()
              .filter(account -> account.getId().equals(accountCandidate.getId()))
              .findFirst()
              .ifPresentOrElse(account -> {
                  account = accountCandidate;
                }, () -> {
                  accounts.add(accountCandidate);
                });
      return accountCandidate;
    }

    @Override
    public Account update(long id, Account obj) {
        return null;
    }

    @Override
    public boolean delete(Account account) {
       return accounts.remove(account);
    }

    @Override
    public void deleteAll(List entities) {
        accounts.clear();
    }

    @Override
    public void saveAll(List entities) {
        accounts.addAll(entities);
    }

    @Override
    public List findAll() {
        return accounts;
    }

    @Override
    public boolean deleteById(long id) {
        return accounts.removeIf(account -> account.getId().equals(id));
    }

    @Override
    public Account getOne(long id) {
        return accounts
                .stream()
                .filter(acc -> acc.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Account getByNumber(String number){
        Account account = accounts.stream()
                .filter(account1 -> account1.getNumber().equals(number))
                .findFirst()
                .orElse(null);
        return account;
    }
}
