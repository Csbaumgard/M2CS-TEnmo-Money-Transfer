package com.techelevator.tenmo.services;

import org.springframework.web.client.RestTemplate;

public class AccountService {

    private String baseUrl = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();
}
