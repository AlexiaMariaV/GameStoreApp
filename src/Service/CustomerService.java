package Service;

import Model.Customer;
import Model.Game;
import Model.PaymentMethod;
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
            System.out.println("Niciun client conectat.");
            return false;
        }

        if (amount <= 0) {
            System.out.println("Amount must be greater than 0.");
            return false;
        }

        float currentFunds = loggedInCustomer.getFundWallet();
        loggedInCustomer.setFundWallet(currentFunds + amount);
        System.out.println("The amount has been successfully added through: " + paymentMethod.getPaymentType());
        System.out.println("Current amount: " + loggedInCustomer.getFundWallet());
        return true;
    }
}
