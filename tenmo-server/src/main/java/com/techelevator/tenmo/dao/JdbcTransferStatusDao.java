package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcTransferStatusDao implements TransferStatusDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferStatusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public TransferStatus getTransferStatusFromDesc(String desc) {
        TransferStatus transferStatus = null;
        String sql =
                "SELECT transfer_status_id, transfer_status_desc " +
                "FROM transfer_status " +
                "WHERE transfer_status_desc = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, desc);

        if (rowSet.next()) {
            int transferStatusId = rowSet.getInt("transfer_status_id");
            String transferStatusDesc = rowSet.getString("transfer_status_desc");
            transferStatus = new TransferStatus(transferStatusId, transferStatusDesc);
        }
        return transferStatus;
    }

    @Override
    public TransferStatus getTransferStatusFromId(int transferStatusId) {
        TransferStatus transferStatus = null;
        String sql =
                "SELECT transfer_status_id, transfer_status_desc " +
                "FROM transfer_status " +
                "WHERE transfer_status_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transferStatusId);

        if (rowSet.next()) {
            int newTransferStatusId = rowSet.getInt("transfer_status_id");
            String transferStatusDesc = rowSet.getString("transfer_status_desc");
            transferStatus = new TransferStatus(newTransferStatusId, transferStatusDesc);
        }
        return transferStatus;
    }
}
