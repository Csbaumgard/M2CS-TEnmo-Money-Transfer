package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.List;

public class TransferService {
    private String baseUrl = "http://localhost:8080";
    private RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public TransferService(AuthenticatedUser loggedInUser, String baseUrl) {
        this.baseUrl = baseUrl;
        //httpEntity = new HttpEntity(loggedInUser); //need the token somehow
    }


    public void createTransfer(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        HttpEntity<Transfer> entity = new HttpEntity(transfer, headers);

        String url = baseUrl + "/tenmo_transfer";

        try {
            restTemplate.exchange(url, HttpMethod.POST, entity, Transfer.class);
        } catch(RestClientResponseException e) {
            if (e.getMessage().contains("You're broke, bud")) {
                System.out.println("You don't have enough money for that transaction.");
            } else {
                System.out.println("Could not complete request. Code: " + e.getRawStatusCode());
            }
        } catch(ResourceAccessException e) {
            System.out.println("Could not complete request due to server network issue. Please try again.");
        }
    }

    public TransferService() {
    }

    public Transfer[] listTransfersForUserID(AuthenticatedUser currentUser) {
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "/tenmo_user/" + currentUser.getUser().getId() + "/tenmo_transfer", HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
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

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
