package Service;

import Repository.IRepository;
import Model.Game;

public class AdminService {
    private final IRepository<Game> gameRepository;
    private final AccountService accountService;

    public AdminService(IRepository<Game> gameRepository, AccountService accountService) {
        this.gameRepository = gameRepository;
        this.accountService = accountService;
    }

    // Modificăm această metodă pentru a accepta gameId ca parametru
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
