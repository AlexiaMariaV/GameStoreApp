package Service;

import Model.Customer;
import Model.Game;
import Model.PaymentMethod;
import Model.Review;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing customer-specific functions, such as browsing games, managing wallet funds, and leaving reviews.
 */

public class CustomerService {
    private final IRepository<Game> gameRepository;
    private Customer loggedInCustomer;

    /**
     * Constructs the CustomerService with a game repository and logged-in customer.
     * @param gameRepository The repository for managing games.
     * @param loggedInCustomer The currently logged-in customer.
     */

    public CustomerService(IRepository<Game> gameRepository, Customer loggedInCustomer) {
        this.gameRepository = gameRepository;
        this.loggedInCustomer = loggedInCustomer;
    }

    /**
     * Sets the currently logged-in customer.
     * @param customer The customer to set as logged in.
     */

    public void setLoggedInCustomer(Customer customer) {
        this.loggedInCustomer = customer;
    }

    /**
     * Retrieves all games available in the repository.
     * @return A list of all available games.
     */

    public List<Game> getAllGames() {
        return gameRepository.getAll();
    }

    /**
     * Searches for a game by its name.
     * @param gameName The name of the game to search for.
     * @return The game if found, or null otherwise.
     */

    public Game searchGameByName(String gameName) {
        List<Game> games = gameRepository.getAll();
        for (Game game : games) {
            if (game.getGameName().equalsIgnoreCase(gameName)) {
                return game;
            }
        }
        return null;
    }

    /**
     * Filters games by a specified genre.
     * @param genre The genre to filter games by.
     * @return A list of games that match the specified genre.
     */

    public List<Game> filterByGenre(String genre) {
        List<Game> gamesByGenre = new ArrayList<>();
        for (Game game : gameRepository.getAll()) {
            if (game.getGameGenre().name().equalsIgnoreCase(genre)) {
                gamesByGenre.add(game);
            }
        }
        return gamesByGenre;
    }

    /**
     * Retrieves the currently logged-in customer.
     * @return The logged-in customer.
     */

    public Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }

    /**
     * Adds funds to the customer's wallet.
     * @param amount The amount to add to the wallet.
     * @param paymentMethod The payment method used for adding funds.
     * @return true if funds are added successfully, false otherwise.
     */

    public boolean addFundsToWallet(float amount, PaymentMethod paymentMethod) {
        if (loggedInCustomer == null) {
            System.out.println("No user logged in.");
            return false;
        }

        if (amount > 0) {
            System.out.println("The amount has been successfully added through: " + paymentMethod.getPaymentType());
        }

        float currentFunds = loggedInCustomer.getFundWallet();
        loggedInCustomer.setFundWallet(currentFunds + amount);
        System.out.println("The amount has been successfully added through: " + paymentMethod.getPaymentType());
        System.out.println("Current amount: " + loggedInCustomer.getFundWallet());
        return true;
    }

    /**
     * Retrieves the balance of the customer's wallet.
     * @return The current balance in the wallet.
     */

    public float getWalletBalance() {
        if (loggedInCustomer == null) {
            System.out.println("No user logged in.");
            return 0;
        }
        return loggedInCustomer.getFundWallet();
    }

    /**
     * Adds a game to the customer's library.
     * @param game The game to add to the library.
     */

    public void addGameToLibrary(Game game) {
        if (loggedInCustomer != null) {
            List<Game> gamesLibrary = loggedInCustomer.getGamesLibrary();
            if (gamesLibrary == null) {
                gamesLibrary = new ArrayList<>();
                loggedInCustomer.setGamesLibrary(gamesLibrary);
            }
            gamesLibrary.add(game);
        }
    }

    /**
     * Retrieves the customer's game library.
     * @return A list of games in the customer's library.
     */

    public List<Game> getGamesLibrary() {
        if (loggedInCustomer != null) {
            return loggedInCustomer.getGamesLibrary();
        }
        return new ArrayList<>();
    }

    /**
     * Adds a review to a game if the customer owns it.
     * @param game The game to review.
     * @param reviewText The review content.
     * @return true if the review is added successfully, false otherwise.
     */

    public boolean addReviewToGame(Game game, String reviewText) {
        if (loggedInCustomer == null) {
            System.out.println("No user is logged in.");
            return false;
        }

        if (loggedInCustomer.getGamesLibrary().contains(game)) {
            Review review = new Review(game.getReviews().size() + 1, reviewText, loggedInCustomer, game);

            List<Review> gameReviews = game.getReviews();
            if (gameReviews instanceof ArrayList) {
                gameReviews.add(review);
            } else {
                gameReviews = new ArrayList<>(gameReviews);
                gameReviews.add(review);
                game.setReviews(gameReviews);
            }

            List<Review> customerReviews = loggedInCustomer.getReviews();
            if (customerReviews instanceof ArrayList) {
                customerReviews.add(review);
            } else {
                customerReviews = new ArrayList<>(customerReviews);
                customerReviews.add(review);
                loggedInCustomer.setReviews(customerReviews);
            }
            return true;
        } else {
            return false;
        }
    }

}
