package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfer> listAllTransfers() {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * FROM tenmo_transfer;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        while (rowSet.next()) {
            Transfer transfer = mapRowToTransfer(rowSet);
            transfers.add(transfer);
        }
        return transfers;
    }

    @Override
    public void create(Transfer transfer) {
        String sql =
                "INSERT INTO tenmo_transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, transfer.getTransferId(), transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
    }

    @Override
    public Transfer getByTransferId(int transferId) {
        String sql = "SELECT * FROM tenmo_transfer WHERE transfer_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transferId);
        Transfer transfer = null;

        if (rowSet.next()) {
            transfer = mapRowToTransfer(rowSet);
        }
        return transfer;
    }

    @Override
    public List<Transfer> getAllTransfersForUserId(int userId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql =
                "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM tenmo_transfer " +
                "JOIN tenmo_account ON tenmo_account.account_id = tenmo_transfer.account_from OR tenmo_account.account_id = tenmo_transfer.account_to " +
                "WHERE user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        while (rowSet.next()) {
            transfers.add(mapRowToTransfer(rowSet));
        }
        return transfers;
    }

    @Override
    public void update(Transfer transfer) {
        String sql =
                "UPDATE tenmo_transfer " +
                "SET transfer_status_id = ? " +
                "WHERE transfer_id = ?;";
        jdbcTemplate.update(sql, transfer.getTransferStatusId(), transfer.getTransferId());
    }

    @Override
    public void delete(int transferId) {
        String sql =
                "DELETE FROM tenmo_transfer " +
                "WHERE transfer_id = ?;";
        jdbcTemplate.update(sql, transferId);
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setTransferTypeId(rowSet.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rowSet.getInt("transfer_status_id"));
        transfer.setAccountFrom(rowSet.getInt("account_from"));
        transfer.setAccountTo(rowSet.getInt("account_to"));
        transfer.setAmount(rowSet.getDouble("amount"));
        return transfer;
    }

}
