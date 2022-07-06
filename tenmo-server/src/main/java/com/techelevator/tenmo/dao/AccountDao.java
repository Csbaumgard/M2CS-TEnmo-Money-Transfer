package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;


public interface AccountDao {

    List<Account> findAllAccounts();

    Account getAccountById(int accountId) throws AccountNotFoundException;

    double getBalanceByUserId(int userId);
}
