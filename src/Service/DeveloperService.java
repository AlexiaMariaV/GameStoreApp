package Service;

import Model.Developer;
import Model.Game;
import Model.User;
import Repository.IRepository;
import Exception.BusinessLogicException;

import java.util.List;

/**
 * Service class for developer-specific functions, such as publishing and modifying games.
 */

public class DeveloperService {
    private final IRepository<Game> gameRepository;
    private final IRepository<User> userRepository;
    private final IRepository<Developer> developerRepository;
    private Developer loggedInDeveloper;

    /**
     * Constructs the DeveloperService with a game repository and logged-in developer.
     * @param gameRepository The repository for managing games.
     * @param userRepository The repository for managing users.
     * @param loggedInDeveloper The currently logged-in developer.
     */

    public DeveloperService(IRepository<Game> gameRepository, IRepository<User> userRepository, IRepository<Developer> developerRepository, Developer loggedInDeveloper) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.developerRepository = developerRepository;
        this.loggedInDeveloper = loggedInDeveloper;
    }

    /**
     * Sets the developer as the logged-in user.
     * @param developer The developer to set as logged in.
     */

    public void setDeveloper(Developer developer){
        this.loggedInDeveloper = developer;
    }

    /**
     * Publishes a new game by the developer.
     * @param game The game to publish.
     * @return true if the game is published successfully, false otherwise.
     */

    public boolean publishGame(Game game) {
        if (loggedInDeveloper == null) {
            throw new BusinessLogicException("You are not logged in as a developer.");
        }

        List<Game> allGames = gameRepository.getAll();
        for (Game existingGame : allGames) {
            if (existingGame.getGameName().equalsIgnoreCase(game.getGameName())) {
                throw new BusinessLogicException("A game with this name already exists.");
            }
        }

        game.setGameId(allGames.size() + 1);
        gameRepository.create(game);
//        loggedInDeveloper.addPublishedGame(game);
        loggedInDeveloper.getPublishedGames().add(game);
        userRepository.update(loggedInDeveloper);
        developerRepository.update(loggedInDeveloper);
        return true;
    }

    /**
     * Modifies an existing game if the developer owns it.
     * @param gameId The ID of the game to modify.
     * @param newName The new name for the game.
     * @param newDescription The new description for the game.
     * @param newGenre The new genre for the game.
     * @param newPrice The new price for the game.
     * @return true if the game is modified successfully, false otherwise.
     */

    public boolean modifyGame(int gameId, String newName, String newDescription, String newGenre, float newPrice) {

        Game game = gameRepository.get(gameId);

        boolean ownsGame = false;

        if (game != null) {
            for (Game g : loggedInDeveloper.getPublishedGames()) {
                if (g.getId().equals(gameId)) {
                    ownsGame = true;
                    break;
                }
            }
        }

        if (ownsGame) {
            game.setGameName(newName);
            game.setGameDescription(newDescription);
            game.setPrice(newPrice);

            gameRepository.update(game);

            System.out.println("Game has been updated: " + game);
            return true;
        }

        throw new BusinessLogicException("You don't have permission to modify this game.");
    }

    /**
     * Retrieves the list of games published by the developer.
     * @return A list of published games.
     */

    public List<Game> getPublishedGames() {
        return loggedInDeveloper.getPublishedGames();
    }
}
