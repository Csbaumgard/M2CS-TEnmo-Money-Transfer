package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatus;

public interface TransferStatusDao {

    TransferStatus getTransferStatusFromDesc(String desc);

    TransferStatus getTransferStatusFromId(int transferStatusId);
}
