package Service;

import Model.Developer;
import Model.Game;
import Repository.IRepository;

import java.util.List;

public class DeveloperService {
    private final IRepository<Game> gameRepository;
    private Developer loggedInDeveloper;

    public DeveloperService(IRepository<Game> gameRepository, Developer loggedInDeveloper) {
        this.gameRepository = gameRepository;
        this.loggedInDeveloper = loggedInDeveloper;
    }

    public void setDeveloper(Developer developer){
        this.loggedInDeveloper = developer;
    }

    public boolean publishGame(Game game) {
        if (loggedInDeveloper == null) {
            System.out.println("You are not logged in as a developer.");
            return false;
        }

        List<Game> allGames = gameRepository.getAll();
        for (Game existingGame : allGames) {
            if (existingGame.getGameName().equalsIgnoreCase(game.getGameName())) {
                System.out.println("A game with this name already exists.");
                return false;
            }
        }

        game.setGameId(allGames.size() + 1);
        gameRepository.create(game);
        loggedInDeveloper.addPublishedGame(game);
        System.out.println("Game published successfully: " + game.getGameName());
        return true;
    }

    public boolean modifyGame(int gameId, String newName, String newDescription, String newGenre, float newPrice) {

        Game game = gameRepository.get(gameId);
        if (game != null && loggedInDeveloper.getPublishedGames().contains(game)) {
            game.setGameName(newName);
            game.setGameDescription(newDescription);
            game.setPrice(newPrice);
            System.out.println("Game has been actualised: " + game);
            return true;
        }
        System.out.println("You don't have permission to modify this game or game not found.");
        return false;
    }

    public List<Game> getPublishedGames() {
        return loggedInDeveloper.getPublishedGames();
    }
}
