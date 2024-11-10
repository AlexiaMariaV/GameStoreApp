package Controller;

import Model.Game;
import Service.GameService;

import java.util.List;

public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    public void addGame(Game game) {
        gameService.addGame(game);
    }

    public Game getGameById(Integer gameId) {
        return gameService.getGameById(gameId);
    }

    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }
}

