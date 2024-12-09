package Service;

import Model.Admin;
import Model.Discount;
import Repository.IRepository;
import Model.Game;

/**
 * Service class for admin-specific functions, such as managing games and applying discounts.
 */

public class AdminService {
    private final IRepository<Game> gameRepository;
    private final IRepository<Admin> adminRepository;
    private final IRepository<Discount> discountRepository;

    /**
     * Constructs the AdminService with a game repository.
     * @param gameRepository The repository for managing games.
     */

    public AdminService(IRepository<Game> gameRepository, IRepository<Admin> adminRepository, IRepository<Discount> discountRepository) {
        this.gameRepository = gameRepository;
        this.adminRepository = adminRepository;
        this.discountRepository = discountRepository;
    }

    /**
     * Deletes a game from the repository by its ID.
     * @param gameId The ID of the game to delete.
     *               If the game does not exist, a message is displayed.
     */

    public void deleteGame(int gameId) {
        Game game = gameRepository.get(gameId);
        if (game == null) {
            System.out.println("Game with ID " + gameId + " not found.");
            return;
        }
        gameRepository.delete(gameId);
    }

    /**
     * Applies a discount to a specific game.
     * @param gameId The ID of the game to apply the discount to.
     * @param discountPercentage The discount percentage to apply.
     *                           Updates the game repository with the discounted price.
     *                           If the game does not exist, a message is displayed.
     */

    public void applyDiscountToGame(int gameId, float discountPercentage) {
        Game game = gameRepository.get(gameId);
        if (game == null) {
            System.out.println("Game with ID " + gameId + " not found.");
            return;
        }

        Discount discount = new Discount(gameId, discountPercentage);
        discountRepository.create(discount);
        game.setDiscount(discount);
        gameRepository.update(game);

        float discountedPrice = game.getPrice() * (1 - discountPercentage / 100);
        System.out.println("Discount of " + discountPercentage + "% applied to game: " + game.getGameName());
        System.out.println("New discounted price: " + discountedPrice);
    }


}
