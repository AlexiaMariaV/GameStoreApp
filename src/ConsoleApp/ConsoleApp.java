package ConsoleApp;

import Controller.AccountController;
import Repository.InMemoryRepository;
import Service.AccountService;
import Model.User;
import java.util.Scanner;


public class ConsoleApp {
    private final AccountController accountController;
    private final Scanner scanner;

    public ConsoleApp() {
        AccountService accountService = new AccountService(new InMemoryRepository<>());
        this.accountController = new AccountController(accountService);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("1. Sign Up");
            System.out.println("2. Log In");
            System.out.println("3. Log Out");
            System.out.println("4. Exit");
            System.out.print("Select option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> handleSignUp();
                case 2 -> handleLogIn();
                case 3 -> handleLogOut();
                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void handleSignUp() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (accountController.signUp(username, email, password)) {
            System.out.println("Sign-up successful!");
        } else {
            System.out.println("Sign-up failed! Email may already be in use.");
        }
    }

    private void handleLogIn() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (accountController.logIn(email, password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    private void handleLogOut() {
        accountController.logOut();
    }

    public static void main(String[] args) {
        new ConsoleApp().start();
    }
}

