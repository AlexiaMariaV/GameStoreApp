package Model;


import java.util.List;

public class Game implements HasId {

    private Integer gameId;
    private String gameName;
    private String gameDescription;
    private String gameGenre;
    //genre to be added
    private float price;
    List<Review> reviews;


    public Game(Integer gameId, String gameName, String gameDescription, String gameGenre, float price, List<Review> reviews) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.gameDescription = gameDescription;
        this.gameGenre = gameGenre;
        this.price = price;
        this.reviews = reviews;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public String getGameGenre() {
        return gameGenre;
    }

    public void setGameGenre(String gameGenre) {
        this.gameGenre = gameGenre;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", gameName='" + gameName + '\'' +
                ", gameDescription='" + gameDescription + '\'' +
                ", gameGenre='" + gameGenre + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public Integer getId() {
        return gameId;
    }
}
