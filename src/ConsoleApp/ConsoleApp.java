package ConsoleApp;

import Controller.AccountController;
import Controller.GameController;
import Repository.InMemoryRepository;
import Service.AccountService;
import Service.GameService;
import Model.User;
import Model.Game;
import Model.GameGenre;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ConsoleApp {
    private final AccountController accountController;
    private final GameController gameController;
    private final Scanner scanner;

    public ConsoleApp() {
        AccountService accountService = new AccountService(new InMemoryRepository<>());
        GameService gameService = new GameService(new InMemoryRepository<>());
        this.accountController = new AccountController(accountService);
        this.gameController = new GameController(gameService);
        this.scanner = new Scanner(System.in);
        initializeGames();
    }

    private void initializeGames() {
        List<Game> sampleGames = List.of(
                new Game(1, "Cyber Adventure", "Explore a cyberpunk city filled with secrets.", GameGenre.ADVENTURE, 59.99f, new ArrayList<>()),
                new Game(2, "Space Warfare", "A space-themed shooter with intergalactic battles.", GameGenre.SHOOTER, 49.99f, new ArrayList<>()),
                new Game(3, "Mystic Quest", "Solve mysteries in a fantasy world.", GameGenre.RPG, 39.99f, new ArrayList<>()),
                new Game(4, "Farm Builder", "Create and manage your own virtual farm.", GameGenre.SIMULATION, 19.99f, new ArrayList<>()),
                new Game(5, "Puzzle Challenge", "Solve various puzzles to progress through levels.", GameGenre.PUZZLE, 9.99f, new ArrayList<>())
        );

        for (Game game : sampleGames) {
            gameController.addGame(game);
        }
    }

    public void start() {
        while (true) {
            System.out.println("1. Sign Up");
            System.out.println("2. Log In");
            System.out.println("3. Log Out");
            System.out.println("4. Delete Account");
            System.out.println("5. Add Game");
            System.out.println("6. View Game");
            System.out.println("7. List All Games");
            System.out.println("8. Exit");
            System.out.print("Select option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> handleSignUp();
                case 2 -> handleLogIn();
                case 3 -> handleLogOut();
                case 4 -> handleDeleteAccount();
                case 5 -> handleAddGame();
                case 6 -> handleViewGame();
                case 7 -> handleListAllGames();
                case 8 -> {
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

    private void handleDeleteAccount() {
        System.out.print("Are you sure you want to delete your account? (yes/no): ");
        String confirmation = scanner.nextLine().toLowerCase();

        if ("yes".equals(confirmation)) {
            if (accountController.deleteAccount()) {
                System.out.println("Your account has been deleted.");
            } else {
                System.out.println("Account deletion failed. No user is logged in.");
            }
        } else {
            System.out.println("Account deletion canceled.");
        }
    }

    private void handleAddGame() {
        System.out.print("Game Name: ");
        String gameName = scanner.nextLine();
        System.out.print("Game Description: ");
        String gameDescription = scanner.nextLine();
        System.out.print("Game Genre (e.g., ACTION, ADVENTURE): ");
        String genreInput = scanner.nextLine();
        GameGenre gameGenre = GameGenre.valueOf(genreInput.toUpperCase());
        System.out.print("Price: ");
        float price = scanner.nextFloat();
        scanner.nextLine();

        Game game = new Game(null, gameName, gameDescription, gameGenre, price, new ArrayList<>());
        gameController.addGame(game);
    }

    private void handleViewGame() {
        System.out.print("Enter Game ID: ");
        Integer gameId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Game game = gameController.getGameById(gameId);
        if (game != null) {
            System.out.println("Game Details: " + game);
        } else {
            System.out.println("Game not found.");
        }
    }

    private void handleListAllGames() {
        List<Game> games = gameController.getAllGames();
        if (games.isEmpty()) {
            System.out.println("No games available.");
        } else {
            System.out.println("Available Games:");
            for (Game game : games) {
                System.out.println(game);
            }
        }
    }


    public static void main(String[] args) {
        new ConsoleApp().start();
    }
}

