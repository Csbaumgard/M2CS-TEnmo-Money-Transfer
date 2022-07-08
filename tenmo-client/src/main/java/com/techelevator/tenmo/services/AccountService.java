package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class AccountService {
    private String authToken;

    //    private AuthenticatedUser currentUser;
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    private String baseUrl = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();


    public double viewCurrentBalance() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Double> currentBalance = restTemplate.exchange(baseUrl + "balance/", HttpMethod.GET, entity, Double.class);
            return currentBalance.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return Double.parseDouble(null);

    }

}
