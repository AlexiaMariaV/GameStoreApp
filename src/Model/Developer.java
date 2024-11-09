package Model;

import java.util.List;

public class Developer extends User{
    private List<Game> publishedGames;

    public Developer(String username, Integer userId, String password, List<Game> publishedGames) {
        super(username, userId, password);
        this.publishedGames = publishedGames;
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
                ", username='" + username + '\'' +
                ", userId=" + userId +
                ", password='" + password + '\'' +
                "publishedGames=" + publishedGames +
                '}';
    }

    @Override
    public Integer getId() {
        return userId;
    }
}
