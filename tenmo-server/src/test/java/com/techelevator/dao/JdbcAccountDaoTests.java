package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class JdbcAccountDaoTests extends BaseDaoTests{

    //need to create some test Accounts
    private JdbcTemplate jdbcTemplate;
    private JdbcAccountDao sut;

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void list_accounts_given_user_id() {
        List<Account> foundAccount= sut.findAllAccounts();

        String sql = ""


    }
}
