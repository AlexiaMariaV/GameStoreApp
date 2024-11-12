package Service;

import Model.Customer;
import Model.Game;
import Model.ShoppingCart;
import java.util.List;

public class ShoppingCartService {
    private final ShoppingCart shoppingCart;

    public ShoppingCartService(Customer customer) {
        this.shoppingCart = new ShoppingCart(customer, customer.getShoppingCart().getListOfGames());
    }

    public void addGameToCart(Game game) {
        if (!shoppingCart.getListOfGames().contains(game)) {
            shoppingCart.getListOfGames().add(game);
            System.out.println("Game added to cart: " + game.getGameName());
        } else {
            System.out.println("Game is already in the cart.");
        }
    }

    public void removeGameFromCart(Game game) {
        if (shoppingCart.getListOfGames().remove(game)) {
            System.out.println("Game removed from cart: " + game.getGameName());
        } else {
            System.out.println("Game not found in the cart.");
        }
    }

    public float calculateTotalPrice() {
        float total = 0;
        for (Game game : shoppingCart.getListOfGames()) {
            total += game.getDiscountedPrice();
        }
        return total;
    }

    public void clearCart() {
        shoppingCart.getListOfGames().clear();
        System.out.println("Cart has been cleared.");
    }

    public List<Game> getGamesInCart() {
        return shoppingCart.getListOfGames();
    }

}
