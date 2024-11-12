package Model;


import java.util.ArrayList;
import java.util.List;

public class Game implements HasId {

    private Integer gameId;
    private String gameName;
    private String gameDescription;
    private GameGenre gameGenre;
    private float price;
    private Discount discount;
    //private Developer developer;
    List<Review> reviews;


    public Game(Integer gameId, String gameName, String gameDescription, GameGenre gameGenre, float price, List<Review> reviews) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.gameDescription = gameDescription;
        this.gameGenre = gameGenre;
        this.price = price;
        //this.developer = developer;
        this.reviews = new ArrayList<>(reviews);
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

    public GameGenre getGameGenre() {
        return gameGenre;
    }

    public void setGameGenre(GameGenre gameGenre) {
        this.gameGenre = gameGenre;
    }

    public float getPrice() {
        return price;
    }

    public float getDiscountedPrice() {
        if (discount != null) {
            return price * (1 - discount.getDiscountPercentage() / 100);
        }
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public List<Review> getReviews() {return reviews;}

    public void setReviews(List<Review> reviews) {this.reviews = new ArrayList<>(reviews);}

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", gameName='" + gameName + '\'' +
                ", gameDescription='" + gameDescription + '\'' +
                ", gameGenre='" + gameGenre + '\'' +
                ", price=" + getDiscountedPrice() +
                ", reviews=" + reviews +
                '}';
    }

    @Override
    public Integer getId() {
        return gameId;
    }
}
