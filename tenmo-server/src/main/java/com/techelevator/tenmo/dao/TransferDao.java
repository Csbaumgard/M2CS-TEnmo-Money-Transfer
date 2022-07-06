package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    List<Transfer> list();

    void create(Transfer transfer);

    Transfer get(int transferId);

    Transfer update(Transfer transfer, int transferId);

    void delete(int transferId);

}
