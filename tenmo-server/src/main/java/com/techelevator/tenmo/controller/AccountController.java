package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    private JdbcAccountDao jdbcAccountDao;

    public AccountController(JdbcAccountDao jdbcAccountDao) {
        this.jdbcAccountDao = jdbcAccountDao;
    }

    @RequestMapping(value = "/balance/{userId}", method = RequestMethod.GET)
    public double getBalance(@PathVariable int userId) {
        return jdbcAccountDao.getBalanceByUserId(userId);
    }

}