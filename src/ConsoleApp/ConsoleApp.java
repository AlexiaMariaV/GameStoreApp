package ConsoleApp;

import Controller.AccountController;
import Controller.GameController;
import Controller.AdminController;
import Controller.DeveloperController;
import Model.Developer;
import Repository.InMemoryRepository;
import Service.AccountService;
import Service.GameService;
import Service.AdminService;
import Service.DeveloperService;
import Model.User;
import Model.Game;
import Model.GameGenre;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ConsoleApp {
    private final AccountController accountController;
    private final GameController gameController;
    private final AdminController adminController;
    private final Scanner scanner;
    private final DeveloperController developerController;

    public ConsoleApp() {
        InMemoryRepository<Game> repository = new InMemoryRepository<>();
        AccountService accountService = new AccountService(new InMemoryRepository<>());
        GameService gameService = new GameService(repository);
        AdminService adminService = new AdminService(repository);
        DeveloperService developerService = new DeveloperService(repository, null);
        this.accountController = new AccountController(accountService);
        this.gameController = new GameController(gameService);
        this.adminController = new AdminController(adminService);
        this.developerController = new DeveloperController(developerService);
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
            System.out.println("5. Add Game (Developer Only)");
            System.out.println("6. View Game");
            System.out.println("7. List All Games");
            System.out.println("8. Delete Game (Admin Only)");
            System.out.println("9. Modify Game (Developer Only)");
            System.out.println("10. Exit");
            System.out.print("Select option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> handleSignUp();
                case 2 -> handleLogIn();
                case 3 -> handleLogOut();
                case 4 -> handleDeleteAccount();
                case 5 -> handlePublishGame();
                case 6 -> handleViewGame();
                case 7 -> handleListAllGames();
                case 8 -> handleDeleteGame();
                case 9 -> handleModifyGame();
                case 10 -> {
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

    private void handlePublishGame(){
        User loggedUser = accountController.getLoggedInUser();
        if (loggedUser == null || !loggedUser.getRole().equals("Developer")) {
            System.out.println("Nu aveți permisiunea de a adauga jocuri.");
            return;
        }

        System.out.print("Nume joc: ");
        String gameName = scanner.nextLine();
        System.out.print("Descriere joc: ");
        String gameDescription = scanner.nextLine();
        System.out.print("Gen joc (ex: ACTION, ADVENTURE): ");
        String genreInput = scanner.nextLine();
        GameGenre gameGenre = GameGenre.valueOf(genreInput.toUpperCase());
        System.out.print("Preț: ");
        float price = scanner.nextFloat();
        scanner.nextLine();

        Game game = new Game(null, gameName, gameDescription, gameGenre, price, List.of());

        Developer developer = (Developer) loggedUser;
        developerController.setDeveloper(developer);
        developerController.publishGame(game);
    }

    private void handleViewGame() {
        System.out.print("Enter Game ID: ");
        Integer gameId = scanner.nextInt();
        scanner.nextLine();

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

    private void handleDeleteGame() {
        if (accountController.getLoggedInUser() == null || !accountController.getLoggedInUser().getRole().equals("Admin")) {
            System.out.println("Nu aveți permisiunea de a șterge jocuri.");
            return;
        }

        System.out.print("Enter the game ID you want to delete: ");
        int gameId = scanner.nextInt();
        scanner.nextLine();

        adminController.deleteGame(gameId);
    }
    private void handleModifyGame() {
        User loggedUser = accountController.getLoggedInUser();
        if (loggedUser == null || !loggedUser.getRole().equals("Developer")) {
            System.out.println("You are not logged in.");
            return;
        }
        System.out.print("Game ID: ");
        int gameID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("New Name: ");
        String newName = scanner.nextLine();
        System.out.print("New Game description: ");
        String newDescription = scanner.nextLine();
        System.out.println("New Game genre: ");
        String newGenre = scanner.nextLine();
        System.out.print("New Price: ");
        float newPrice = scanner.nextFloat();
        scanner.nextLine();

        Developer developer = (Developer) loggedUser;
        developerController.setDeveloper(developer);


        developerController.modifyGame(gameID, newName, newDescription, newGenre, newPrice);
    }

    public static void main(String[] args) {
        new ConsoleApp().start();
    }
}

