package ConsoleApp;

import Controller.*;
import Model.*;
import Repository.IRepository;
import Repository.InMemoryRepository;
import Repository.FileRepository;
import Service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * ConsoleApp class provides the main interface for interacting with the gaming platform
 * through the console. It manages various user roles (Admin, Developer, Customer)
 * and their associated actions, including purchasing, managing games, and account actions.
 */


public class ConsoleApp {
    private final AccountController accountController;
    private final GameController gameController;
    private final AdminController adminController;
    private final Scanner scanner;
    private final DeveloperController developerController;
    private final CustomerController customerController;
    private final ShoppingCartController shoppingCartController;

    /**
     * Constructs a ConsoleApp instance and initializes repositories, services, and controllers.
     */


    public ConsoleApp() {
//        InMemoryRepository<Game> repository = new InMemoryRepository<>();
//        AccountService accountService = new AccountService(new InMemoryRepository<>());
        // FileRepository (ensure the file paths are correctly set)
//        FileRepository<Game> repository = new FileRepository<>("games.dat");
//        AccountService accountService = new AccountService(new FileRepository<>("users.dat"));
        IRepository<Game> gameRepository = FileRepository.getInstance(Game.class, "games.dat");
        IRepository<User> userRepository = FileRepository.getInstance(User.class, "user.dat");
        AccountService accountService = new AccountService(userRepository);
        GameService gameService = new GameService(gameRepository);
        AdminService adminService = new AdminService(gameRepository);
        DeveloperService developerService = new DeveloperService(gameRepository, userRepository,null);
        CustomerService customerService = new CustomerService(gameRepository, userRepository,null);
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

    /**
     * Initializes sample games to the repository for demonstration purposes.
     */

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

    /**
     * Starts the application, showing the main menu continuously.
     */

    public void start() {
        while (true) {
            showMainMenu();
        }
    }

    /**
     * Displays the main menu for all users and handles sign-up, login, and exit.
     */

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
            case 3 -> exitApp();
            default -> System.out.println("Invalid option.");
        }
    }

    /**
     * Displays the Admin menu, providing options to manage games, discounts, and accounts.
     */

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
            case 7 -> handleLogOut();
            case 8 -> exitApp();
            default -> System.out.println("Invalid option.");
        }
    }

    /**
     * Displays the Developer menu, allowing the developer to publish and modify games.
     */

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
            case 6 -> handleLogOut();
            case 7 -> exitApp();
            default -> System.out.println("Invalid option.");
        }
    }

    /**
     * Displays the Customer menu, where the customer can view games, manage wallet,
     * add reviews, and make purchases.
     */

    private void showCustomerMenu() {
        System.out.println("\nCustomer Menu:");
        System.out.println("1. List All Games");
        System.out.println("2. Search Game by Name");
        System.out.println("3. Sort/Filter Games by");
        //System.out.println("3. Filter Games by Genre");
        System.out.println("4. Add Funds to your wallet");
        System.out.println("5. View Wallet Balance");
        System.out.println("6. View Games Library");
        System.out.println("7. Make a Purchase");
        System.out.println("8. Add Review to a game");
        System.out.println("9. Delete Account");
        System.out.println("10. Log Out");
        System.out.println("11. Exit");
        System.out.print("Select option: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1 -> handleListAllGames();
            case 2 -> handleSearchGameByName();
            case 3 -> showSortFilterMenu();
            case 4 -> handleAddFundsToWallet();
            case 5 -> handleViewWalletBalance();
            case 6 -> handleViewLibrary();
            case 7 -> showShoppingCartMenu();
            case 8 -> handleAddReviewToGame();
            case 9 -> handleDeleteAccount();
            case 10 -> handleLogOut();
            case 11 -> exitApp();
            default -> System.out.println("Invalid option.");
        }
    }

    /**
     * Displays the Shopping Cart menu, allowing customers to add, remove games,
     * and complete the checkout process.
     */

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
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Displays the menu for sorting and filtering games.
     */

    private void showSortFilterMenu() {
        while (true) {
            System.out.println("\nSort/Filter Games Menu:");
            System.out.println("1. Sort Games by Name (Ascending)");
            System.out.println("2. Sort Games by Price (Descending)");
            System.out.println("3. Filter Games by Genre");
            System.out.println("4. Filter Games by Price Range");
            System.out.println("5. Return to Customer Menu");
            System.out.print("Select option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> handleSortGamesByNameAscending();
                case 2 -> handleSortGamesByPriceDescending();
                case 3 -> handleFilterByGenre();
                case 4 -> handleFilterGamesByPriceRange();
                case 5 -> {
                    System.out.println("Returning to Customer Menu...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }



    //METHODS FOR ALL USERS

    /**
     * Handles user sign-up, allowing new users to create an account.
     */

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

    /**
     * Handles user login, verifying email and password for authentication.
     */

    private void handleLogIn() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (accountController.logIn(email, password)) {
            System.out.println("Login successful!");
            String role = accountController.getLoggedInUser().getRole();
            if (role.equals("Admin")) {
                while (accountController.getLoggedInUser() != null) {
                    showAdminMenu();
                }
            } else if (role.equals("Developer")) {
                while (accountController.getLoggedInUser() != null) {
                    showDeveloperMenu();
                }

            } else if (role.equals("Customer")) {
                Customer loggedInCustomer = (Customer) accountController.getLoggedInUser();
                customerController.setCustomer(loggedInCustomer);
                while(accountController.getLoggedInUser() != null)
                    showCustomerMenu();
            }
            else {
                System.out.println("Unknown role.");
            }
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    /**
     * Allows the user to view details of a specific game by its ID.
     */

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

    /**
     * Displays a list of all available games.
     */

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

    /**
     * Handles account deletion for the currently logged-in user.
     */

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

    /**
     * Logs out the currently logged-in user.
     */

    private void handleLogOut() {
        boolean success = accountController.logOut();

        if (success) {
            System.out.println("Logged out successfully. Returning to Main Menu.");
        } else {
            System.out.println("No user is logged in to log out.");
        }
    }

    /**
     * Exits the application gracefully by printing a message and stopping the execution.
     */

    private void exitApp() {
        System.out.println("Exiting the application...");
        System.exit(0);
    }



    //ADMIN ONLY METHODS

    /**
     * Allows Admin to delete a specific game by its ID.
     */

    private void handleDeleteGame() {
        if (accountController.getLoggedInUser() == null || !accountController.getLoggedInUser().getRole().equals("Admin")) {
            System.out.println("You don't have permission to delete games.");
            return;
        }

        System.out.print("Enter the game ID you want to delete: ");
        int gameId = scanner.nextInt();
        scanner.nextLine();

        adminController.deleteGame(gameId);
        System.out.println("Game with ID " + gameId + " has been successfully deleted.");
    }

    /**
     * Allows Admin to delete any account by specifying the email.
     */

    private void handleDeleteAnyAccount() {

        System.out.print("Please enter the email of the user whose account you would like to delete: ");
        String email = scanner.nextLine();

        if (accountController.deleteAnyAccount(email)) {
            System.out.println("The account has been deleted.");
        } else {
            System.out.println("The account could not be deleted. Please check the email.");
        }

    }

    /**
     * Allows Admin to apply a discount to a specified game.
     */

    private void handleApplyDiscountToGame() {
        System.out.print("Enter the game ID: ");
        int gameId = scanner.nextInt();
        System.out.print("Enter discount percentage: ");
        float discountPercentage = scanner.nextFloat();
        scanner.nextLine();

        adminController.applyDiscountToGame(gameId, discountPercentage);
    }





    //DEVELOPER ONLY METHODS

    /**
     * Allows Developer to publish a new game to the platform.
     */

    private void handlePublishGame(){
        User loggedUser = accountController.getLoggedInUser();
        if (loggedUser == null || !loggedUser.getRole().equals("Developer")) {
            System.out.println("You don't have permission to publish games.");
            return;
        }

        System.out.print("Game name: ");
        String gameName = scanner.nextLine();
        System.out.print("Game description: ");
        String gameDescription = scanner.nextLine();
        System.out.print("Game genre (ex: ACTION, ADVENTURE): ");
        String genreInput = scanner.nextLine();
        GameGenre gameGenre = GameGenre.valueOf(genreInput.toUpperCase());
        System.out.print("Price: ");
        float price = scanner.nextFloat();
        scanner.nextLine();

        Game game = new Game(null, gameName, gameDescription, gameGenre, price, List.of());

        Developer developer = (Developer) loggedUser;
        developerController.setDeveloper(developer);
        developerController.publishGame(game);
        System.out.println("Game published successfully: " + game.getGameName());
    }

    /**
     * Allows Developer to modify details of an existing game.
     */

    private void handleModifyGame() {
        User loggedUser = accountController.getLoggedInUser();
        if (loggedUser == null || !loggedUser.getRole().equals("Developer")) {
            System.out.println("You don't have permission to modify games.");
            return;
        }
        System.out.print("Game ID: ");
        int gameID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("New Name: ");
        String newName = scanner.nextLine();
        System.out.print("New Game description: ");
        String newDescription = scanner.nextLine();
        System.out.print("New Game genre: ");
        String newGenre = scanner.nextLine();
        System.out.print("New Price: ");
        float newPrice = scanner.nextFloat();
        scanner.nextLine();

        Developer developer = (Developer) loggedUser;
        developerController.setDeveloper(developer);


        developerController.modifyGame(gameID, newName, newDescription, newGenre, newPrice);
    }


    //CUSTOMER ONLY METHODS

    /**
     * Allows customers to search for a game by its name.
     */

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

    /**
     * Sorts and displays games by name in ascending order.
     */

    private void handleSortGamesByNameAscending() {
        List<Game> sortedGames = customerController.sortGamesByNameAscending();

        if (sortedGames.isEmpty()) {
            System.out.println("No games available.");
        } else {
            System.out.println("Games sorted by name (ascending):");
            for (Game game : sortedGames) {
                System.out.println("- " + game.getGameName() + " ($" + game.getPrice() + ")");
            }
        }
    }

    /**
     * Sorts and displays games by price in descending order.
     */

    private void handleSortGamesByPriceDescending() {
        List<Game> sortedGames = customerController.sortGamesByPriceDescending();

        System.out.println("Games sorted by price (descending):");
        for (Game game : sortedGames) {
            System.out.println("- " + game.getGameName() + " ($" + game.getPrice() + ")");
        }
    }

    /**
     * Filters and displays games by the specified genre.
     */

    private void handleFilterByGenre() {
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

    /**
     * Filters and displays games within the specified price range.
     */

    private void handleFilterGamesByPriceRange() {
        System.out.print("Enter minimum price: ");
        float minPrice = scanner.nextFloat();
        System.out.print("Enter maximum price: ");
        float maxPrice = scanner.nextFloat();
        scanner.nextLine();

        List<Game> filteredGames = customerController.filterGamesByPriceRange(minPrice, maxPrice);

        if (filteredGames.isEmpty()) {
            System.out.println("No games found in the specified price range.");
        } else {
            System.out.println("Games in the price range $" + minPrice + " - $" + maxPrice + ":");
            for (Game game : filteredGames) {
                System.out.println("- " + game.getGameName() + " ($" + game.getPrice() + ")");
            }
        }
    }


    /**
     * Displays the contents of the shopping cart.
     */

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

    /**
     * Adds a game to the customer's shopping cart.
     */

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

    /**
     * Removes a specified game from the customer's shopping cart.
     */

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

    /**
     * Calculates and displays the total price of games in the shopping cart.
     */

    private void handleViewCartTotalPrice() {
        float totalPrice = shoppingCartController.checkout();
        System.out.println("Total price of games in cart: $" + totalPrice);
    }

    /**
     * Finalizes the purchase process, deducting funds and transferring games to the customer library.
     */

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
            System.out.println("Cart has been cleared.");
            System.out.println("Checkout successful! Thank you for your purchase.");
        } else {
            System.out.println("Insufficient funds. Please add funds to your wallet.");
        }

    }

    /**
     * Allows customers to view games they own in their library.
     */

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

    /**
     * Displays the current wallet balance for the logged-in customer.
     */

    private void handleViewWalletBalance() {
        float balance = customerController.getWalletBalance();
        System.out.println("Current funds: " + balance);
    }

    /**
     * Allows customers to add funds to their wallet using a specified payment method.
     */

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

    /**
     * Allows customers to add a review to a game they own.
     */

    private void handleAddReviewToGame() {
        System.out.print("Enter the name of the game to review: ");
        String gameName = scanner.nextLine();
        Game game = customerController.searchGameByName(gameName);

        if (game == null) {
            System.out.println("Game not found.");
            return;
        }

        System.out.print("Enter your review: ");
        String reviewText = scanner.nextLine();

        boolean success = customerController.addReviewToGame(game, reviewText);
        if (success) {
            System.out.println("Review added successfully!");
        } else {
            System.out.println("Could not add review. Make sure you own the game.");
        }
    }

    public static void main(String[] args) {
        new ConsoleApp().start();
    }
}