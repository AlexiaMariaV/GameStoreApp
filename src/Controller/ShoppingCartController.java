package Controller;

import Model.Game;
import Service.ShoppingCartService;
import java.util.List;

public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    public void addGame(Game game) {
        shoppingCartService.addGameToCart(game);
    }

    public void removeGame(Game game) {
        shoppingCartService.removeGameFromCart(game);
    }

    public List<Game> viewCartContents() {
        return shoppingCartService.getGamesInCart();
    }

    public float checkout() {
        float total = shoppingCartService.calculateTotalPrice();
        System.out.println("Total price: $" + total);
        return total;
    }

    public void clearCart() {
        shoppingCartService.clearCart();
    }
}
