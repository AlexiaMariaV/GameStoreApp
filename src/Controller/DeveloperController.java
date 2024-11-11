package Controller;

import Model.Developer;
import Model.Game;
import Model.GameGenre;
import Service.DeveloperService;

import java.util.List;
import java.util.Scanner;

public class DeveloperController {
    private final DeveloperService developerService;
    private final Scanner scanner;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
        this.scanner = new Scanner(System.in);
    }

    public void setDeveloper(Developer developer){
        this.developerService.setDeveloper(developer);
    }

    public void publishGame(Game game) {
        developerService.publishGame(game);
    }

    public void modifyGame(Integer gameId, String newName, String newDescription, String newGenre, Float newPrice) {
        developerService.modifyGame(gameId, newName, newDescription, newGenre, newPrice);
    }
}