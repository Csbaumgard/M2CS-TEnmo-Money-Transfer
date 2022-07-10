package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {
    private final TransferDao transferDao;
    private final TransferStatusDao transferStatusDao;
    private final TransferTypeDao transferTypeDao;
    private final AccountDao accountDao;

    public TransferController(TransferDao transferDao, TransferStatusDao transferStatusDao, TransferTypeDao transferTypeDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.transferStatusDao = transferStatusDao;
        this.transferTypeDao = transferTypeDao;
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "/tenmo_transfer", method = RequestMethod.GET)
    public List<Transfer> listAllTransfers() {
        return transferDao.listAllTransfers();
    }

    @RequestMapping(path = "/tenmo_transfer/user_id/{userId}", method = RequestMethod.GET)
    public List<Transfer> getAllTransfersForUserId(@PathVariable int userId) {
        return transferDao.getAllTransfersForUserId(userId);
    }

    @RequestMapping(path = "/transfer_id/{id}", method = RequestMethod.GET)
    public Transfer getByTransferId(@PathVariable int id) {
        return transferDao.getByTransferId(id);
    }

    @RequestMapping(path = "/transfer_type/{id}", method = RequestMethod.GET)
    public TransferType getTransferTypeFromId(@PathVariable int id) {
        return transferTypeDao.getTransferTypeFromId(id);
    }

    @RequestMapping(path = "/transfer_type/", method = RequestMethod.GET)
    public TransferType getTransferTypeFromDesc(@RequestParam String desc) {
        return transferTypeDao.getTransferTypeFromDesc(desc);
    }

    @RequestMapping(path = "/transfer_status/{id}", method = RequestMethod.GET)
    public TransferStatus getTransferStatusFromId(@PathVariable int id) {
        return transferStatusDao.getTransferStatusFromId(id);
    }

    @RequestMapping(path = "/transfer_status/{desc}", method = RequestMethod.GET)
    public TransferStatus getTransferStatusFromDesc(@RequestParam String desc) {
        return transferStatusDao.getTransferStatusFromDesc(desc);
    }

    //@ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/tenmo_transfer", method = RequestMethod.POST) //i think we need to delete {id}
    public void create(@RequestBody Transfer transfer) {
        int accountFrom = transfer.getAccountFrom();
        int accountTo = transfer.getAccountTo();
        double transferAmount = transfer.getAmount();

        accountDao.withdraw(accountDao.getAccountByAccountId(accountFrom), transferAmount);
        accountDao.deposit(accountDao.getAccountByAccountId(accountTo), transferAmount);

        transferDao.create(transfer);
    }
}
