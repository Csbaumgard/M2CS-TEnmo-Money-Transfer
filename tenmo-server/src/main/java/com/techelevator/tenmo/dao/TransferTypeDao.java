package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferType;

public interface TransferTypeDao {

    TransferType getTransferTypeFromDesc(String desc);

    TransferType getTransferTypeFromId(int transferId);
}
