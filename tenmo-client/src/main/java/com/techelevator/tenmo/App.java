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

import java.security.Principal;
import java.text.NumberFormat;
import java.util.Arrays;

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
		// TODO Auto-generated method stub
        double balance = accountService.viewCurrentBalance();
        String formattedAmount = NumberFormat.getCurrencyInstance().format(balance);
        System.out.println("Your current balance: " + formattedAmount);
	}

	private void viewTransferHistory() {
        // TODO Auto-generated method stub
        Transfer[] transfers = transferService.listTransfersForUserID(currentUser);
        if (transfers == null) {
            System.out.println("You have no transfers.");
        } else {
            for (Transfer transfer : transfers) {
                System.out.println(transfer);
            }
        }
    }

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
        // System.out.println(transferService.listPendingTransfersForUser(currentUser));
	}

	private void sendBucks() {
		// TODO Auto-generated method stub
        User[] users = accountService.listOfUsers();
        for (User user : users) {
            System.out.println("User ID - " + user.getId() + " Username - " + user.getUsername());
        }
        Transfer transfer = new Transfer();
        int currentUserId = Math.toIntExact((currentUser.getUser().getId()));
        transfer.setAccountFrom(accountService.getAccountIdByUserId(currentUserId));
        int destinationUserId = consoleService.promptForUserId("Please enter the destination User ID");
        transfer.setAccountTo(accountService.getAccountIdByUserId(destinationUserId));
        transfer.setAmount(consoleService.promptForTransferAmount("Please enter the amount to transfer."));
        System.out.println("You got this far!");
        transferService.createTransfer(transfer);
        }



        //transferService.createTransfer(currentUser, );
/*
        double amountToTransfer = 0;

        Transfer transfer = new Transfer();
        transfer.setAccountFrom(currentUser.getUser().getId()); //provided long, need int - should we change our types?
        transfer.setAccountTo(/* method we build in account service to filter NOT current user*);
        transfer.setAmount(amountToTransfer);
*/

	private void requestBucks() {
		// TODO Auto-generated method stub
    }
}
