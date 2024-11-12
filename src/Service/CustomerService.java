package Service;

import Model.Customer;
import Model.Game;
import Model.PaymentMethod;
import Model.Review;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private final IRepository<Game> gameRepository;
    private Customer loggedInCustomer;

    public CustomerService(IRepository<Game> gameRepository, Customer loggedInCustomer) {
        this.gameRepository = gameRepository;
        this.loggedInCustomer = loggedInCustomer;
    }

    public void setLoggedInCustomer(Customer customer) {
        this.loggedInCustomer = customer;
    }

    public List<Game> getAllGames() {
        return gameRepository.getAll();
    }

    public Game searchGameByName(String gameName) {
        List<Game> games = gameRepository.getAll();
        for (Game game : games) {
            if (game.getGameName().equalsIgnoreCase(gameName)) {
                return game;
            }
        }
        return null;
    }

    public List<Game> filterByGenre(String genre) {
        List<Game> gamesByGenre = new ArrayList<>();
        for (Game game : gameRepository.getAll()) {
            if (game.getGameGenre().name().equalsIgnoreCase(genre)) {
                gamesByGenre.add(game);
            }
        }
        return gamesByGenre;
    }

    public Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }

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

    public float getWalletBalance() {
        if (loggedInCustomer == null) {
            System.out.println("No user logged in.");
            return 0;
        }
        return loggedInCustomer.getFundWallet();
    }
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

    public List<Game> getGamesLibrary() {
        if (loggedInCustomer != null) {
            return loggedInCustomer.getGamesLibrary();
        }
        return new ArrayList<>();
    }

    public boolean addReviewToGame(Game game, String reviewText) {
        if (loggedInCustomer == null) {
            System.out.println("No user is logged in.");
            return false;
        }

        if (loggedInCustomer.getGamesLibrary().contains(game)) {
            Review review = new Review(game.getReviews().size() + 1, reviewText, loggedInCustomer, game);
            game.getReviews().add(review);
            loggedInCustomer.getReviews().add(review);

            System.out.println("Review added successfully.");
            return true;
        } else {
            System.out.println("You can only review games that you own.");
            return false;
        }
    }

}
