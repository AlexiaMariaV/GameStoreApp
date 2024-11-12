package Controller;

import Model.Customer;
import Model.Game;
import Model.PaymentMethod;
import Service.CustomerService;

import java.util.List;

public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void setCustomer(Customer customer) {
        customerService.setLoggedInCustomer(customer);
    }

    public List<Game> listAllGames() {
        return customerService.getAllGames();
    }

    public Game searchGameByName(String gameName) {
        return customerService.searchGameByName(gameName);
    }

    public List<Game> filterGamesByGenre(String genre) {
        return customerService.filterByGenre(genre);
    }

    public boolean addFundsToWallet(float amount, PaymentMethod paymentMethod) {
        return customerService.addFundsToWallet(amount, paymentMethod);
    }
    public float getWalletBalance() {
        return customerService.getWalletBalance();
    }

    public void addGameToLibrary(Game game) {
        customerService.addGameToLibrary(game);
    }

    public List<Game> getGamesLibrary() {
        return customerService.getGamesLibrary();
    }
}

