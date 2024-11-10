package Model;

import java.util.List;

public class Customer extends User {

    private float fundWallet;
    private List<Game> gamesLibrary;
    private List<Review> reviews;
    private ShoppingCart shoppingCart;


    public Customer( Integer userId, String username, String email, String password, String role, float fundWallet, List<Game> gamesLibrary, List<Review> reviews, ShoppingCart shoppingCart) {
        super(userId, username, email, password, role);
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
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
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
