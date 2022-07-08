package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

public class TransferService {
    private String baseUrl = "http://localhost:8080";
    private RestTemplate restTemplate = new RestTemplate();
    private HttpEntity httpEntity;

    public TransferService(AuthenticatedUser loggedInUser, String baseUrl) {
        this.baseUrl = baseUrl;
        httpEntity = new HttpEntity(loggedInUser); //need the token somehow
    }
}
