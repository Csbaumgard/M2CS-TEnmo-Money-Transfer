package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.List;

public class TransferService {
    private String baseUrl = "http://localhost:8080";
    private RestTemplate restTemplate = new RestTemplate();
    //private HttpEntity httpEntity;

    public TransferService(AuthenticatedUser loggedInUser, String baseUrl) {
        this.baseUrl = baseUrl;
        //httpEntity = new HttpEntity(loggedInUser); //need the token somehow
    }


    public void createTransfer(AuthenticatedUser authenticatedUser, Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);

        String url = baseUrl + "/tenmo_transfer/" + transfer.getTransferId();

        restTemplate.exchange(url, HttpMethod.POST, entity, Transfer.class);
    }

    public TransferService() {
    }

    public Transfer[] listTransfersForUser(AuthenticatedUser currentUser) {
        Transfer[] transfers = null;
        try {
            transfers = restTemplate.getForObject(baseUrl + "/tenmo_account/" + currentUser.getUser().getId() + "/tenmo_transfer", Transfer[].class);
        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

//    public Transfer[] listPendingTransfersForUser(AuthenticatedUser currentUser) {
//        Transfer[] transfers = null;
//        try {
//            transfers = restTemplate.getForObject(baseUrl + "/tenmo_account/" + currentUser.getUser().getId() + "/tenmo_transfer", Transfer[].class);
//        } catch (Exception e) {
//            BasicLogger.log(e.getMessage());
//        }
//        return transfers;
//    }

    public Transfer getTransferById(int id) {
        Transfer transfer = null;
        try {
            transfer = restTemplate.getForObject(baseUrl + "/tenmo_transfer/" + id, Transfer.class);
        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }
}
