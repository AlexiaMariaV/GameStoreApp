package Service;

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


}
