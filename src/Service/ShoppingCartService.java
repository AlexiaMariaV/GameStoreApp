package Service;

import Model.Customer;
import Model.Game;
import Model.ShoppingCart;
import java.util.List;

/**
 * Service class for managing the customer's shopping cart, including adding/removing games and calculating total price.
 */

public class ShoppingCartService {
    private final ShoppingCart shoppingCart;

    /**
     * Constructs the ShoppingCartService with the customer's shopping cart.
     * @param customer The customer associated with the shopping cart.
     */

    public ShoppingCartService(Customer customer) {
        this.shoppingCart = new ShoppingCart(customer, customer.getShoppingCart().getListOfGames());
    }

    /**
     * Adds a game to the shopping cart if it is not already present.
     * @param game The game to add to the cart.
     */

    public void addGameToCart(Game game) {
        if (!shoppingCart.getListOfGames().contains(game)) {
            shoppingCart.getListOfGames().add(game);
        } else {
            System.out.println("Game is already in the cart.");
        }
    }

    /**
     * Removes a game from the shopping cart if it is present.
     * @param game The game to remove from the cart.
     */

    public void removeGameFromCart(Game game) {
        if (shoppingCart.getListOfGames().remove(game)) {
            System.out.println("Game removed from cart: " + game.getGameName());
        } else {
            System.out.println("Game not found in the cart.");
        }
    }

    /**
     * Calculates the total price of all games in the cart, taking discounts into account.
     * @return The total price of the cart.
     */

    public float calculateTotalPrice() {
        float total = 0;
        for (Game game : shoppingCart.getListOfGames()) {
            total += game.getDiscountedPrice();
        }
        return total;
    }

    /**
     * Clears all games from the shopping cart.
     */

    public void clearCart() {
        shoppingCart.getListOfGames().clear();
        System.out.println("Cart has been cleared.");
    }

    /**
     * Retrieves all games currently in the shopping cart.
     * @return A list of games in the cart.
     */

    public List<Game> getGamesInCart() {
        return shoppingCart.getListOfGames();
    }

}
