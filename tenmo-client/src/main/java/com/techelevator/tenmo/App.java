package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import java.security.Principal;
import java.text.NumberFormat;
import java.util.Arrays;

@SuppressWarnings("EmptyMethod")
public class App {

    private AuthenticatedUser currentUser;
    private static final String API_BASE_URL = "http://localhost:8080/";
    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService();
    private final TransferService transferService = new TransferService();



    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }  else {         //if not null, then we need to use currentUser accountservice.setAuthToken(currentuser.gettoken) to prove user is logged in
            accountService.setAuthToken(currentUser.getToken());
            transferService.setAuthToken(currentUser.getToken());
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }
    //String formattedAmount = NumberFormat.getCurrencyInstance().format(amount);
	private void viewCurrentBalance() {
		handleViewBalance();
	}

	private void viewTransferHistory() {
        handleListTransfers();
    }

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
        // System.out.println(transferService.listPendingTransfersForUser(currentUser));
	}

	private void sendBucks() {
        handleSendBucks();
    }
	private void requestBucks() {
		// TODO Auto-generated method stub
    }

    private void handleSendBucks() {
        User[] users = accountService.listOfUsers();
        for (User user : users) {
            System.out.println("User ID - " + user.getId() + " Username - " + user.getUsername());
        }
        try {
            Transfer transfer = new Transfer();
            transfer.setTransferId(transfer.getTransferId());
            transfer.setTransferTypeId(transfer.getTransferTypeId());
            transfer.setTransferStatusId(transfer.getTransferStatusId());
            transfer.setAccountFrom(accountService.getAccountIdByUserId(Math.toIntExact(currentUser.getUser().getId())));
            //if (accountService.getAccountIdByUserId(consoleService.promptForUserId("Provide User ID: ")) == transfer.getAccountFrom) {
            // System.out.println("You cannot send money to yourself.");

            transfer.setAccountTo(accountService.getAccountIdByUserId(consoleService.promptForUserId("Provide User ID: ")));
            transfer.setAmount(consoleService.promptForTransferAmount("Provide transfer amount: "));
            transferService.createTransfer(transfer); //calling this doesn't create the whole transfer
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }

    private void handleListTransfers() {
        Transfer[] transfers = transferService.listTransfersForUserID(currentUser.getUser().getId());
        if (transfers == null) {
            System.out.println("You have no transfers.");
        } else {
            for (Transfer transfer : transfers) {
                System.out.println(transfer);
            }
        }
    }

    private void handleViewBalance() {
        double balance = accountService.viewCurrentBalance();
        String formattedAmount = NumberFormat.getCurrencyInstance().format(balance);
        System.out.println("Your current balance: " + formattedAmount);
    }
}
