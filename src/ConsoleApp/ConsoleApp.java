package ConsoleApp;

import Repository.InMemoryRepository;
import Service.AccountService;
import Model.User;
import java.util.Scanner;


public class ConsoleApp {
    private final AccountService accountService;
    private final Scanner scanner;

    public ConsoleApp() {
        this.accountService = new AccountService(new InMemoryRepository<>());
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

        accountService.signUp(username, email, password);
    }

    private void handleLogIn() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        accountService.logIn(email, password);
    }

    private void handleLogOut() {
        accountService.logOut();
    }

    public static void main(String[] args) {
        new ConsoleApp().start();
    }
}

