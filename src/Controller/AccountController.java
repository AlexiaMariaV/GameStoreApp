package Controller;

import Service.AccountService;
import Model.User;

public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public boolean signUp(String username, String email, String password) {
        return accountService.signUp(username, email, password);
    }

    public boolean logIn(String email, String password) {
        return accountService.logIn(email, password);
    }

    public void logOut() {
        accountService.logOut();
    }

    public User getLoggedInUser() {
        return accountService.getLoggedInUser();
    }

}
