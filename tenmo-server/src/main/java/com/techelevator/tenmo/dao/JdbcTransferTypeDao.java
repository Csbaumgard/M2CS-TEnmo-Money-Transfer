package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcTransferTypeDao implements TransferTypeDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferTypeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public TransferType getTransferTypeFromDesc(String desc) {
        TransferType transferType = null;
        String sql =
                "SELECT transfer_type_id, transfer_type_desc " +
                "FROM transfer_type " +
                "WHERE transfer_type_desc = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, desc);

        if (rowSet.next()) {
            int transferTypeId = rowSet.getInt("transfer_type_id");
            String transferTypeDesc = rowSet.getString("transfer_type_desc");
            transferType = new TransferType(transferTypeId, transferTypeDesc);
        }
        return transferType;
    }

    @Override
    public TransferType getTransferTypeFromId(int transferId) {
        TransferType transferType = null;
        String sql =
                "SELECT transfer_type_id, transfer_type_desc " +
                "FROM transfer_type " +
                "WHERE transfer_type_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transferId);

        if (rowSet.next()) {
            int transferTypeId = rowSet.getInt("transfer_type_id");
            String transferTypeDesc = rowSet.getString("transfer_type_desc");
            transferType = new TransferType(transferTypeId, transferTypeDesc);
        }
        return transferType;
    }
}
