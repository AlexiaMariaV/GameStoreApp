package ConsoleApp;

import Controller.AccountController;
import Controller.GameController;
import Controller.AdminController;
import Controller.DeveloperController;
import Controller.CustomerController;
import Controller.ShoppingCartController;
import Model.*;
import Repository.InMemoryRepository;
import Service.AccountService;
import Service.GameService;
import Service.AdminService;
import Service.DeveloperService;
import Service.CustomerService;
import Service.ShoppingCartService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ConsoleApp {
    private final AccountController accountController;
    private final GameController gameController;
    private final AdminController adminController;
    private final Scanner scanner;
    private final DeveloperController developerController;
    private final CustomerController customerController;
    private final ShoppingCartController shoppingCartController;


    public ConsoleApp() {
        InMemoryRepository<Game> repository = new InMemoryRepository<>();
        AccountService accountService = new AccountService(new InMemoryRepository<>());
        GameService gameService = new GameService(repository);
        AdminService adminService = new AdminService(repository);
        DeveloperService developerService = new DeveloperService(repository, null);
        CustomerService customerService = new CustomerService(repository, null);
        ShoppingCart placeholderCart = new ShoppingCart(null, new ArrayList<>());
        Customer placeholderCustomer = new Customer(null, "placeholder", "placeholder@example.com", "password", "Customer",0.0f, new ArrayList<>(), new ArrayList<>(), placeholderCart );
        ShoppingCartService shoppingCartService = new ShoppingCartService(placeholderCustomer);
        this.accountController = new AccountController(accountService);
        this.gameController = new GameController(gameService);
        this.adminController = new AdminController(adminService);
        this.developerController = new DeveloperController(developerService);
        this.customerController = new CustomerController(customerService);
        this.shoppingCartController = new ShoppingCartController(shoppingCartService);
        this.scanner = new Scanner(System.in);
        initializeGames();
    }

    private void initializeGames() {
        List<Game> sampleGames = List.of(
                new Game(1, "Cyber Adventure", "Explore a cyberpunk city filled with secrets.", GameGenre.ADVENTURE, 59.99f, new ArrayList<>()),
                new Game(2, "Space Warfare", "A space-themed shooter with intergalactic battles.", GameGenre.SHOOTER, 49.99f, new ArrayList<>()),
                new Game(3, "Mystic Quest", "Solve mysteries in a fantasy world.", GameGenre.RPG, 39.99f, new ArrayList<>()),
                new Game(4, "Farm Builder", "Create and manage your own virtual farm.", GameGenre.RPG, 19.99f, new ArrayList<>()),
                new Game(5, "Puzzle Challenge", "Solve various puzzles to progress through levels.", GameGenre.PUZZLE, 9.99f, new ArrayList<>())
        );

        for (Game game : sampleGames) {
            gameController.addGame(game);
        }
    }

    public void start() {
        while (true) {
            showMainMenu();
        }
    }
    private void showMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Sign Up");
        System.out.println("2. Log In");
        System.out.println("3. Exit");
        System.out.print("Select option: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1 -> handleSignUp();
            case 2 -> handleLogIn();
            case 3 -> {
                System.out.println("Exiting...");
                System.exit(0);
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private void showAdminMenu() {
        System.out.println("\nAdmin Menu:");
        System.out.println("1. View Game");
        System.out.println("2. List All Games");
        System.out.println("3. Delete Game");
        System.out.println("4. Apply Discount to Game");
        System.out.println("5. Delete Account");
        System.out.println("6. Delete Any Account by Email");
        System.out.println("7. Log Out");
        System.out.println("8. Exit");
        System.out.print("Select option: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1 -> handleViewGame();
            case 2 -> handleListAllGames();
            case 3 -> handleDeleteGame();
            case 4 -> handleApplyDiscountToGame();
            case 5 -> handleDeleteAccount();
            case 6 -> handleDeleteAnyAccount();
            case 7 -> {
                accountController.logOut();
                System.out.println("Returning to Main Menu...");
                showMainMenu();
            }
            case 8 -> {
                System.out.println("Exiting...");
                System.exit(0);
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private void showDeveloperMenu() {
        System.out.println("\nDeveloper Menu:");
        System.out.println("1. View Game");
        System.out.println("2. List All Games");
        System.out.println("3. Publish Game");
        System.out.println("4. Modify Game");
        System.out.println("5. Delete Account");
        System.out.println("6. Log Out");
        System.out.println("7. Exit");
        System.out.print("Select option: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1 -> handleViewGame();
            case 2 -> handleListAllGames();
            case 3 -> handlePublishGame();
            case 4 -> handleModifyGame();
            case 5 -> handleDeleteAccount();
            case 6 -> {
                accountController.logOut();
                System.out.println("Returning to Main Menu...");
                showMainMenu();
            }
            case 7 -> {
                System.out.println("Exiting...");
                System.exit(0);
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private void showCustomerMenu() {
        System.out.println("\nCustomer Menu:");
        System.out.println("1. List All Games");
        System.out.println("2. Search Game by Name");
        System.out.println("3. Filter Games by Genre");
        System.out.println("4. Add Funds to your wallet");
        System.out.println("5. View Wallet Balance");
        System.out.println("6. View Games Library");
        System.out.println("7. Make a Purchase");
        System.out.println("8. Delete Account");
        System.out.println("9. Log Out");
        System.out.println("10. Exit");
        System.out.print("Select option: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1 -> handleListAllGames();
            case 2 -> handleSearchGameByName();
            case 3 -> handlefilterByGenre();
            case 4 -> handleAddFundsToWallet();
            case 5 -> handleViewWalletBalance();
            case 6 -> handleViewLibrary();
            case 7 -> showShoppingCartMenu();
            case 8 -> handleDeleteAccount();
            case 9 -> {
                accountController.logOut();
                System.out.println("Returning to Main Menu...");
                showMainMenu();
            }
            case 10 -> {
                System.out.println("Exiting...");
                System.exit(0);
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private void showShoppingCartMenu() {
        while (true) {
            System.out.println("\nShopping Cart Menu:");
            System.out.println("1. List All Games");
            System.out.println("2. View Cart");
            System.out.println("3. Add Game to Cart");
            System.out.println("4. Remove Game from Cart");
            System.out.println("5. View Cart Total Price");
            System.out.println("6. Checkout");
            System.out.println("7. Return to Customer Menu");
            System.out.print("Select option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> handleListAllGames();
                case 2 -> handleViewCart();
                case 3 -> handleAddGameToCart();
                case 4 -> handleRemoveGameFromCart();
                case 5 -> handleViewCartTotalPrice();
                case 6 -> handleCheckout();
                case 7 -> {
                    System.out.println("Returning to Customer Menu...");
                    return; // Exit Shopping Cart Menu
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void handleViewLibrary() {
        List<Game> libraryGames = customerController.getGamesLibrary();
        if (libraryGames.isEmpty()) {
            System.out.println("Your library is empty.");
        } else {
            System.out.println("Games in your library:");
            for (Game game : libraryGames) {
                System.out.println("- " + game.getGameName() + " ($" + game.getDiscountedPrice() + ")");
            }
        }
    }


    private void handleViewWalletBalance() {
        float balance = customerController.getWalletBalance();
        System.out.println("Current funds: " + balance);
    }

    private void handleAddFundsToWallet() {
        System.out.print("Write the amount you want to add to your wallet: ");
        float amount = scanner.nextFloat();
        scanner.nextLine();

        System.out.print("Choose the PaymentMethod (ex: Visa, PayPal, ApplePay): ");
        String paymentType = scanner.nextLine();

        PaymentMethod paymentMethod = new PaymentMethod(1, paymentType);

        if (customerController.addFundsToWallet(amount, paymentMethod)) {
            System.out.println("Funds have been successfully added to your wallet.");
        } else {
            System.out.println("Funds could not be added.");
        }
    }

    private void handleApplyDiscountToGame() {
        System.out.print("Enter the game ID: ");
        int gameId = scanner.nextInt();
        System.out.print("Enter discount percentage: ");
        float discountPercentage = scanner.nextFloat();
        scanner.nextLine();

        adminController.applyDiscountToGame(gameId, discountPercentage);
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
            String role = accountController.getLoggedInUser().getRole();
            if (role.equals("Admin")) {
                while (accountController.getLoggedInUser() != null && role.equals("Admin")) {
                    showAdminMenu();
                }
            } else if (role.equals("Developer")) {
                while (accountController.getLoggedInUser() != null && role.equals("Developer")) {
                    showDeveloperMenu();
                }

            } else if (role.equals("Customer")) {
                Customer loggedInCustomer = (Customer) accountController.getLoggedInUser();
                customerController.setCustomer(loggedInCustomer);
                while(accountController.getLoggedInUser() != null && role.equals("Customer"))
                    showCustomerMenu();
            }
            else {
                System.out.println("Unknown role.");
            }
        } else {
            System.out.println("Invalid email or password.");
        }
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

    private void handleDeleteAnyAccount() {

        System.out.print("Please enter the email of the user whose account you would like to delete: ");
        String email = scanner.nextLine();

        if (accountController.deleteAnyAccount(email)) {
            System.out.println("The account has been deleted.");
        } else {
            System.out.println("The account could not be deleted. Please check the email.");
        }

    }

    private void handlePublishGame(){
        User loggedUser = accountController.getLoggedInUser();
        if (loggedUser == null || !loggedUser.getRole().equals("Developer")) {
            System.out.println("You don't have permission to publish games.");
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

    private void handleSearchGameByName() {
        System.out.print("Enter the name of the game: ");
        String gameName = scanner.nextLine();
        Game game = customerController.searchGameByName(gameName);
        if (game != null) {
            System.out.println("Game found: " + game);
        } else {
            System.out.println("Game not found.");
        }
    }

    private void handlefilterByGenre() {
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();

        List<Game> games = customerController.filterGamesByGenre(genre);
        if (games.isEmpty()) {
            System.out.println("No games found for the specified genre.");
        } else {
            System.out.println("Games in genre " + genre + ":");
            for (Game game : games) {
                System.out.println(game);
            }
        }
    }


    private void handleViewCart() {
        List<Game> cartGames = shoppingCartController.viewCartContents();
        if (cartGames.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Games in your cart:");
            for (Game game : cartGames) {
                System.out.println("- " + game.getGameName() + " ($" + game.getPrice() + ")");
            }
        }
    }

    private void handleAddGameToCart() {
        System.out.print("Enter the name of the game to add to the cart: ");
        String gameName = scanner.nextLine();
        Game game = customerController.searchGameByName(gameName);
        if (game != null) {
            shoppingCartController.addGame(game);
            System.out.println("Game added to cart: " + game.getGameName());
        } else {
            System.out.println("Game not found.");
        }
    }

    private void handleRemoveGameFromCart() {
        System.out.print("Enter the name of the game to remove from the cart: ");
        String gameName = scanner.nextLine();
        Game game = customerController.searchGameByName(gameName);
        if (game != null) {
            shoppingCartController.removeGame(game);
            System.out.println("Game removed from cart: " + game.getGameName());
        } else {
            System.out.println("Game not found in cart.");
        }
    }

    private void handleViewCartTotalPrice() {
        float totalPrice = shoppingCartController.checkout();
        System.out.println("Total price of games in cart: $" + totalPrice);
    }

    private void handleCheckout() {
        float totalPrice = shoppingCartController.checkout();
        float walletBalance = customerController.getWalletBalance();

        System.out.println("Wallet Balance: " + walletBalance);
        System.out.println("Total Price of Cart: " + totalPrice);

        if (walletBalance >= totalPrice) {
            customerController.addFundsToWallet(-totalPrice, new PaymentMethod(1, "Wallet"));
            List<Game> purchasedGames = shoppingCartController.viewCartContents();

            for (Game game : purchasedGames) {
                customerController.addGameToLibrary(game);
            }

            shoppingCartController.clearCart();
            System.out.println("Checkout successful! Thank you for your purchase.");
        } else {
            System.out.println("Insufficient funds. Please add funds to your wallet.");
        }

    }

    public static void main(String[] args) {
        new ConsoleApp().start();
    }
}