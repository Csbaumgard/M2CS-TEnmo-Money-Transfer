package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.UserDao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated")
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
}
