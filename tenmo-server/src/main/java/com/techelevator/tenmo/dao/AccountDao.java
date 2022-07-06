package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;


public interface AccountDao {

    public List<Account> findAllAccounts();

    public void withdraw(Account account, double transferAmount);

    public void deposit(Account account, double transferAmount);

    double getBalanceByUserId(int userId);
}
