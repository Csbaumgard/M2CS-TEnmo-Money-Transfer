package com.techelevator.tenmo.dao;
import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{
    private final JdbcTemplate jdbcTemplate;
    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Account> findAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM tenmo_account";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Account account = mapRowToAccount(results);
            accounts.add(account);
        }
        return accounts;
    }

    private Account mapRowToAccount(SqlRowSet results) {
        Account account = new Account();
        account.setAccountId(results.getInt("account_id"));
        account.setUserId(results.getInt("user_id"));
        account.setBalance(results.getDouble("balance"));
        return account;
    }

    @Override
    public void withdraw(Account account, double transferAmount) {
        String sql = "UPDATE tenmo_account SET balance = balance - ? WHERE user_id = ?";
        jdbcTemplate.update(sql, transferAmount, account.getUserId());
    }

    @Override
    public void deposit(Account account, double transferAmount) {
        String sql = "UPDATE tenmo_account SET balance = balance + ? WHERE user_id = ?";
        jdbcTemplate.update(sql, transferAmount, account.getUserId());
    }

    @Override
    public double getBalanceByUserId(int userId) { //might need to refactor to use user.name and Principal? idfk
        String sql = "SELECT balance FROM tenmo_account WHERE user_id = ?";
        double balance = jdbcTemplate.queryForObject(sql, double.class, userId);
        return balance;
    }

    @Override
    public Account getAccountByAccountId(int accountId) {
        String sql = "SELECT * FROM tenmo_account WHERE account_id = ?";
        return mapRowToAccount((jdbcTemplate.queryForRowSet(sql, accountId)));
    }

    @Override
    public int getAccountIdByUserId(int id) {
        String sql = "SELECT * " +
                "FROM tenmo_account " +
                "JOIN tenmo_user USING (user_id) " +
                "WHERE user_id = ?;";
        Account account = mapRowToAccount(jdbcTemplate.queryForRowSet(sql, Account.class, id));
        return account.getAccountId();
    }

    @Override
    public void updateAccount(Account account) {
        String sql = "UPDATE tenmo_account " +
                "SET balance = ? " +
                "WHERE account_id = ?";

        jdbcTemplate.update(sql, account.getBalance(), account.getAccountId());
    }
}
