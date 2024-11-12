package Controller;

import Model.Customer;
import Model.Game;
import Model.PaymentMethod;
import Service.CustomerService;

import java.util.List;

/**
 * Controller for customer actions, including viewing games, managing funds, and adding reviews.
 */

public class CustomerController {
    private final CustomerService customerService;

    /**
     * Constructs the CustomerController with a CustomerService instance.
     * @param customerService The CustomerService used for customer operations.
     */

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Sets the currently logged-in customer.
     * @param customer The customer to set as logged in.
     */

    public void setCustomer(Customer customer) {
        customerService.setLoggedInCustomer(customer);
    }

    /**
     * Lists all games available in the system.
     * @return A list of all games.
     */

    public List<Game> listAllGames() {
        return customerService.getAllGames();
    }

    /**
     * Searches for a game by its name.
     * @param gameName The name of the game to search for.
     * @return The Game if found, or null if not.
     */

    public Game searchGameByName(String gameName) {
        return customerService.searchGameByName(gameName);
    }

    /**
     * Filters games by a specified genre.
     * @param genre The genre to filter games by.
     * @return A list of games in the specified genre.
     */

    public List<Game> filterGamesByGenre(String genre) {
        return customerService.filterByGenre(genre);
    }

    /**
     * Adds funds to the customer's wallet using a specified payment method.
     * @param amount The amount to add.
     * @param paymentMethod The payment method to use.
     * @return true if funds are added successfully, false otherwise.
     */

    public boolean addFundsToWallet(float amount, PaymentMethod paymentMethod) {
        return customerService.addFundsToWallet(amount, paymentMethod);
    }

    /**
     * Retrieves the current balance of the customer's wallet.
     * @return The wallet balance.
     */

    public float getWalletBalance() {
        return customerService.getWalletBalance();
    }

    /**
     * Adds a game to the customer's library.
     * @param game The game to add to the library.
     */

    public void addGameToLibrary(Game game) {
        customerService.addGameToLibrary(game);
    }

    /**
     * Retrieves the games in the customer's library.
     * @return A list of games in the customer's library.
     */

    public List<Game> getGamesLibrary() {
        return customerService.getGamesLibrary();
    }

    /**
     * Adds a review to a specified game.
     * @param game The game to review.
     * @param reviewText The review text to add.
     * @return true if the review is added successfully, false otherwise.
     */

    public boolean addReviewToGame(Game game, String reviewText) {
        return customerService.addReviewToGame(game, reviewText);
    }
}

