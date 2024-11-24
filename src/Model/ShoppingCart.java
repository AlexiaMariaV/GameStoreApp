package Model;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a shopping cart for a customer, containing a list of games they wish to purchase.
 */

public class ShoppingCart implements Serializable {
    private List<Game> listOfGames;
    private Customer customer;

    /**
     * Constructs a shopping cart for the specified customer and game list.
     *
     * @param customer    The customer who owns the cart.
     * @param listOfGames The list of games in the cart.
     */

    public ShoppingCart(Customer customer, List<Game> listOfGames) {
        this.customer = customer;
        this.listOfGames = listOfGames;
    }

    public List<Game> getListOfGames() {
        return listOfGames;
    }

    public void setListOfGames(List<Game> listOfGames) {
        this.listOfGames = listOfGames;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "listOfGames=" + listOfGames +
                ", customer=" + customer +
                '}';
    }
}
