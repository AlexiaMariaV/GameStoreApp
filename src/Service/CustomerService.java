package Service;

import Model.*;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Service class for managing customer-specific functions, such as browsing games, managing wallet funds, and leaving reviews.
 */

public class CustomerService {
    private final IRepository<Game> gameRepository;
    private final IRepository<User> userRepository;
    private Customer loggedInCustomer;

    /**
     * Constructs the CustomerService with a game repository and logged-in customer.
     * @param gameRepository The repository for managing games.
     * @param loggedInCustomer The currently logged-in customer.
     */

    public CustomerService(IRepository<Game> gameRepository, IRepository<User> userRepository, Customer loggedInCustomer) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
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





    public List<Game> sortGamesByNameAscending() {
        List<Game> games = new ArrayList<>(gameRepository.getAll());

        for (int i = 0; i < games.size() - 1; i++) {
            for (int j = 0; j < games.size() - i - 1; j++) {
                if (games.get(j).getGameName().compareToIgnoreCase(games.get(j + 1).getGameName()) > 0) {
                    Collections.swap(games, j, j + 1);
                }
            }
        }
        return games;
    }

    public List<Game> sortGamesByPriceDescending() {
        List<Game> allGames = new ArrayList<>(gameRepository.getAll());

        for (int i = 0; i < allGames.size() - 1; i++) {
            for (int j = 0; j < allGames.size() - i - 1; j++) {
                if (allGames.get(j).getPrice() < allGames.get(j + 1).getPrice()) {
                    Game temp = allGames.get(j);
                    allGames.set(j, allGames.get(j + 1));
                    allGames.set(j + 1, temp);
                }
            }
        }
        return allGames;
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

    public List<Game> filterGamesByPriceRange(float minPrice, float maxPrice) {
        List<Game> gamesByPriceRange = new ArrayList<>();

        for (Game game : gameRepository.getAll()) {
            if (game.getPrice() >= minPrice && game.getPrice() <= maxPrice) {
                gamesByPriceRange.add(game);
            }
        }

        return gamesByPriceRange;
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
            float currentFunds = loggedInCustomer.getFundWallet();
            loggedInCustomer.setFundWallet(currentFunds + amount);
            userRepository.update(loggedInCustomer);
            System.out.println("The amount has been successfully added through: " + paymentMethod.getPaymentType());
            return true;
        }

        if (amount < 0) {
            float currentFunds = loggedInCustomer.getFundWallet();
            loggedInCustomer.setFundWallet(currentFunds + amount);
            userRepository.update(loggedInCustomer);
            return true;
        }
        return false;
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
            boolean alreadyInLibrary = false;

            for (Game g : loggedInCustomer.getGamesLibrary()) {
                if (g.getId().equals(game.getId())) {
                    alreadyInLibrary = true;
                    break;
                }
            }

            if (!alreadyInLibrary) {
                loggedInCustomer.getGamesLibrary().add(game);
                userRepository.update(loggedInCustomer);
            }
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
//        if (loggedInCustomer == null) {
//            System.out.println("No user logged in.");
//            return false;
//        }
//
//        if (loggedInCustomer.getGamesLibrary().contains(game)) {
//            Review review = new Review(game.getReviews().size() + 1, reviewText, loggedInCustomer, game);
//            game.getReviews().add(review);
//            loggedInCustomer.getReviews().add(review);
//
//            return true;
//        } else {
//            return false;
//        }
        if (loggedInCustomer == null) {
            System.out.println("No user logged in.");
            return false;
        }

        boolean ownsGame = false;

        for (Game g : loggedInCustomer.getGamesLibrary()) {
            if (g.getId().equals(game.getId())) {
                ownsGame = true;
                break;
            }
        }

        if (ownsGame) {
            Review review = new Review(game.getReviews().size() + 1, reviewText, loggedInCustomer, game);
            game.getReviews().add(review);
            loggedInCustomer.getReviews().add(review);

            gameRepository.update(game);
            userRepository.update(loggedInCustomer);

            return true;
        } else {
            System.out.println("You can only review games that you own.");
            return false;
        }
    }

}
