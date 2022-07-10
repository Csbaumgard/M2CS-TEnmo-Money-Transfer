package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
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
        double returned = 0;

        try {
            ResponseEntity<Double> currentBalance = restTemplate.exchange(baseUrl + "balance/", HttpMethod.GET, entity, Double.class);
            returned = currentBalance.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return returned;
    }

    public User[] listOfUsers() {
        User[] users = null;
        try {
            ResponseEntity<User[]> response = restTemplate.exchange(baseUrl + "tenmo_user", HttpMethod.GET,
                    makeAuthEntity(), User[].class);
            users = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    public Account[] listOfAccounts() {
        Account[] accounts = null;
        try {
            ResponseEntity<Account[]> response = restTemplate.exchange(baseUrl + "tenmo_account", HttpMethod.GET,
                    makeAuthEntity(), Account[].class);
            accounts = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return accounts;
    }

    public Account getAccountByUserId(int id) {
        Account account = null;
        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(baseUrl + "tenmo_user/" + id + "/tenmo_account",
                            HttpMethod.GET, makeAuthEntity(), Account.class);
            account = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }
}
