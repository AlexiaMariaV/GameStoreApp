package Service;

import Model.Game;
import Repository.IRepository;
import java.util.List;

/**
 * Service class for managing games, including adding games to the repository.
 */

public class GameService {
    private final IRepository<Game> gameRepository;

    /**
     * Constructs the GameService with a game repository.
     * @param gameRepository The repository for managing games.
     */

    public GameService(IRepository<Game> gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Adds a new game to the repository, assigning it an ID if not present.
     * @param game The game to add.
     */

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

    /**
     * Retrieves a game by its ID.
     * @param gameId The ID of the game to retrieve.
     * @return The game if found, or null otherwise.
     */

    public Game getGameById(Integer gameId) {
        Game game = gameRepository.get(gameId);
        return game != null ? game : null;
    }

    /**
     * Retrieves all games from the repository.
     * @return A list of all games in the repository.
     */

    public List<Game> getAllGames() {
        return gameRepository.getAll();
    }

    /**
     * Generates the next unique ID for a new game.
     * @return The next available ID.
     */

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
