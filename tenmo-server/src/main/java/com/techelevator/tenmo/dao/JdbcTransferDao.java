package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JdbcTransferDao implements TransferDao {

    private List<Transfer> transfers = new ArrayList<>();


    @Override
    public List<Transfer> list() {
        return Collections.unmodifiableList(transfers);
    }

    @Override
    public void create(Transfer transfer) {
        transfers.add(transfer);
    }

    @Override
    public Transfer get(int transferId) {
        for (Transfer transfer : transfers) {
            if (transfer.getTransferId() == transferId) {
                return transfer;
            }
        }
        return null;
    }

    @Override
    public Transfer update(Transfer transfer, int transferId) {
        Transfer result = transfer;
        boolean finished = false;

        for (int i = 0; i < transfers.size(); i++) {
            if (transfers.get(i).getTransferId() == transferId) {
                if (result.getTransferId() == 0) {
                    result.setTransferId(transferId);
                }
                transfers.set(i, result);
                finished = true;
                break;
            }
        }
//        if (!finished) {
//            throw new Exception();
//        }


        return null;
    }

    @Override
    public void delete(int transferId) {

    }
}
