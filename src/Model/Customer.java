package Model;

import java.util.List;

public class Customer extends User {

    private float fundWallet;
    private List<Game> gamesLibrary;
    private List<Review> reviews;
    private ShoppingCart shoppingCart;


    public Customer(String username, Integer userId, String password, float fundWallet, List<Game> gamesLibrary, List<Review> reviews, ShoppingCart shoppingCart) {
        super(username, userId, password);
        this.fundWallet = fundWallet;
        this.gamesLibrary = gamesLibrary;
        this.reviews = reviews;
        this.shoppingCart = shoppingCart;
    }

    public float getFundWallet() {
        return fundWallet;
    }

    public void setFundWallet(float fundWallet) {
        this.fundWallet = fundWallet;
    }

    public List<Game> getGamesLibrary() {
        return gamesLibrary;
    }

    public void setGamesLibrary(List<Game> gamesLibrary) {
        this.gamesLibrary = gamesLibrary;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "username='" + username + '\'' +
                ", userId=" + userId +
                ", password='" + password + '\'' +
                ", fundWallet=" + fundWallet +
                ", gamesLibrary=" + gamesLibrary +
                ", reviews=" + reviews +
                ", shoppingCart=" + shoppingCart +
                '}';
    }

    @Override
    public Integer getId() {
        return 0;
    }
}
