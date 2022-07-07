package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    List<Transfer> listAllTransfers();

    void create(Transfer transfer);

    Transfer getByTransferId(int transferId);

    List<Transfer> getAllTransfersForUserId(int userId);

    void update(Transfer transfer);

    void delete(int transferId);

}
