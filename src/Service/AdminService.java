package Service;

import Model.Discount;
import Repository.IRepository;
import Model.Game;

public class AdminService {
    private final IRepository<Game> gameRepository;

    public AdminService(IRepository<Game> gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void deleteGame(int gameId) {
        Game game = gameRepository.get(gameId);
        if (game == null) {
            System.out.println("Game with ID " + gameId + " not found.");
            return;
        }
        gameRepository.delete(gameId);
        System.out.println("Game with ID " + gameId + " has been successfully deleted.");
    }

    public void applyDiscountToGame(int gameId, float discountPercentage) {
        Game game = gameRepository.get(gameId);
        if (game == null) {
            System.out.println("Game with ID " + gameId + " not found.");
            return;
        }

        Discount discount = new Discount(gameId, discountPercentage);
        game.setDiscount(discount);

        float discountedPrice = game.getPrice() * (1 - discountPercentage / 100);
        System.out.println("Discount of " + discountPercentage + "% applied to game: " + game.getGameName());
        System.out.println("New discounted price: " + discountedPrice);
    }


}
