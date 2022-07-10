package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
//@PreAuthorize("isAuthenticated()")
public class AccountController {
    private AccountDao accountDao;
    private UserDao userDao; //had to add this so we could use the method userDao.findIdByUsername built for us already

    public AccountController(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public double getBalance(Principal principal) { //Principal principal, THEN use user method to get user id wheeeee
        String principalUserName = principal.getName();
        int userId = userDao.findIdByUsername(principalUserName);
        return accountDao.getBalanceByUserId(userId);
    }

    @RequestMapping(path = "/tenmo_user", method = RequestMethod.GET)
    public List<User> findAll() {
        return userDao.findAll();
    }

    @RequestMapping(path = "/tenmo_user/{id}/tenmo_account/account_id", method = RequestMethod.GET)
    public int getAccountIdByUserId(@PathVariable int id) {
        return accountDao.getAccountIdByUserId(id);
    }

    @RequestMapping(path = "/tenmo_account/{id}", method = RequestMethod.PUT)
    public void updateAccount(@RequestBody Account account, @PathVariable int id) {
        accountDao.updateAccount(account);
    }
}
