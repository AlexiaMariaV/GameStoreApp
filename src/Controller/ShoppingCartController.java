package Controller;

import Model.Game;
import Service.ShoppingCartService;
import java.util.List;

/**
 * Controller for shopping cart actions, including adding, removing, and checking out games.
 */

public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    /**
     * Constructs the ShoppingCartController with a ShoppingCartService instance.
     * @param shoppingCartService The ShoppingCartService used for cart operations.
     */

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    /**
     * Adds a game to the shopping cart.
     * @param game The game to add.
     */

    public void addGame(Game game) {
        shoppingCartService.addGameToCart(game);
    }

    /**
     * Removes a game from the shopping cart.
     * @param game The game to remove.
     */

    public void removeGame(Game game) {
        shoppingCartService.removeGameFromCart(game);
    }

    /**
     * Retrieves the contents of the shopping cart.
     * @return A list of games in the cart.
     */

    public List<Game> viewCartContents() {
        return shoppingCartService.getGamesInCart();
    }

    /**
     * Calculates and displays the total price of items in the shopping cart.
     * @return The total price of games in the cart.
     */

    public float checkout() {
        float total = shoppingCartService.calculateTotalPrice();
        System.out.println("Total price: $" + total);
        return total;
    }

    /**
     * Clears all items from the shopping cart.
     */

    public void clearCart() {
        shoppingCartService.clearCart();
    }
}
