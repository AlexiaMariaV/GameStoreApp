package Service;

import Model.Game;
import Repository.IRepository;
import java.util.List;

public class GameService {
    private final IRepository<Game> gameRepository;

    public GameService(IRepository<Game> gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void addGame(Game game) {

        if (game.getId() == null) {
            Integer nextId = generateNextId();
            game.setGameId(nextId);
        }

        try{
            gameRepository.create(game);
            System.out.println("Game added: " + game.getGameName());
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public Game getGameById(Integer gameId) {
        Game game = gameRepository.get(gameId);
        return game != null ? game : null;
    }


    public List<Game> getAllGames() {
        return gameRepository.getAll();
    }

    private Integer generateNextId() {
        List<Game> allGames = gameRepository.getAll();

        if (allGames.isEmpty()) {
            return 1;
        }

        Integer maxId = 0;
        for (Game game : allGames) {
            if (game.getId() > maxId) {
                maxId = game.getId();
            }
        }
        return maxId + 1;
    }

}
