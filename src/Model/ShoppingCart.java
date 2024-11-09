package Model;

import java.util.List;

public class ShoppingCart {
    private List<Game> listOfGames;
    private Customer customer;

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
