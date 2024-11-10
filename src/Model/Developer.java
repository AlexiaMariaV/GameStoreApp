package Model;

import java.util.List;

public class Developer extends User{
    private List<Game> publishedGames;

    public Developer(Integer userId, String username, String email, String password, String role, List<Game> publishedGames) {
        super(userId, username, email, password, role);
        this.publishedGames = publishedGames;
    }

    public void addPublishedGame(Game game) {
        this.publishedGames.add(game);
    }

    public List<Game> getPublishedGames() {
        return publishedGames;
    }

    public void setPublishedGames(List<Game> publishedGames) {
        this.publishedGames = publishedGames;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", publishedGames=" + publishedGames +
                '}';
    }

    @Override
    public Integer getId() {
        return userId;
    }
}
