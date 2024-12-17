package Tests;
import Model.*;
import Service.CustomerService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Repository.FileRepository;
import java.util.ArrayList;
import java.util.List;

public class Tests {

    @Test
    public void TestCRUDCustomer() {
        FileRepository<Customer> customerRepository = new FileRepository<>("customersTest.dat");
        Customer customer = new Customer(1, "TestUser", "test@test.com", "password123", "Customer", 100.0f, new ArrayList<>(), new ArrayList<>(), null);

        customerRepository.create(customer);
        assertEquals("TestUser", customerRepository.get(1).getUsername());

        Customer fetchedCustomer = customerRepository.get(1);
        assertNotNull(fetchedCustomer);
        assertEquals("test@test.com", fetchedCustomer.getEmail());

        Customer updatedCustomer = new Customer(1, "UpdatedUser", "updated@test.com", "newpass", "Customer", 200.0f, new ArrayList<>(), new ArrayList<>(), null);
        customerRepository.update(updatedCustomer);
        assertEquals("UpdatedUser", customerRepository.get(1).getUsername());

        customerRepository.delete(1);
        assertNull(customerRepository.get(1));
    }

    @Test
    public void TestCRUDAdmin() {
        FileRepository<Admin> adminRepository = new FileRepository<>("adminsTest.dat");
        Admin admin = new Admin(1, "AdminUser", "admin@test.com", "adminpass", "Admin");

        adminRepository.create(admin);
        assertEquals("AdminUser", adminRepository.get(1).getUsername());

        Admin fetchedAdmin = adminRepository.get(1);
        assertNotNull(fetchedAdmin);
        assertEquals("admin@test.com", fetchedAdmin.getEmail());

        Admin updatedAdmin = new Admin(1, "UpdatedAdmin", "updated@test.com", "newadminpass", "Admin");
        adminRepository.update(updatedAdmin);
        assertEquals("UpdatedAdmin", adminRepository.get(1).getUsername());

        adminRepository.delete(1);
        assertNull(adminRepository.get(1));
    }

    @Test
    public void TestCRUDDeveloper() {
        FileRepository<Developer> developerRepository = new FileRepository<>("developersTest.dat");
        Developer developer = new Developer(1, "DevUser", "dev@test.com", "devpass", "Developer", new ArrayList<>());

        developerRepository.create(developer);
        assertEquals("DevUser", developerRepository.get(1).getUsername());

        Developer fetchedDeveloper = developerRepository.get(1);
        assertNotNull(fetchedDeveloper);
        assertEquals("dev@test.com", fetchedDeveloper.getEmail());

        Developer updatedDeveloper = new Developer(1, "UpdatedDev", "updated@test.com", "newdevpass", "Developer", new ArrayList<>());
        developerRepository.update(updatedDeveloper);
        assertEquals("UpdatedDev", developerRepository.get(1).getUsername());

        developerRepository.delete(1);
        assertNull(developerRepository.get(1));
    }

    @Test
    public void TestCRUDGame() {
        FileRepository<Game> gameRepository = new FileRepository<>("gamesTest.dat");
        Game game = new Game(1, "TestGame", "A test game description", GameGenre.ADVENTURE, 29.99f, new ArrayList<>());

        gameRepository.create(game);
        assertEquals("TestGame", gameRepository.get(1).getGameName());

        Game fetchedGame = gameRepository.get(1);
        assertNotNull(fetchedGame);
        assertEquals("A test game description", fetchedGame.getGameDescription());

        Game updatedGame = new Game(1, "UpdatedGame", "An updated game description", GameGenre.SHOOTER, 49.99f, new ArrayList<>());
        gameRepository.update(updatedGame);
        assertEquals("UpdatedGame", gameRepository.get(1).getGameName());
        assertEquals(49.99f, gameRepository.get(1).getPrice(), 0.01);

        gameRepository.delete(1);
        assertNull(gameRepository.get(1));
    }

    @Test
    public void TestCRUDDiscount() {
        FileRepository<Discount> discountRepository = new FileRepository<>("discountsTest.dat");
        Discount discount = new Discount(1, 20.0f);

        discountRepository.create(discount);
        assertEquals(20.0f, discountRepository.get(1).getDiscountPercentage());

        Discount updatedDiscount = new Discount(1, 30.0f);
        discountRepository.update(updatedDiscount);
        assertEquals(30.0f, discountRepository.get(1).getDiscountPercentage());

        discountRepository.delete(1);
        assertNull(discountRepository.get(1));
    }

    @Test
    public void TestCRUDReview() {
        FileRepository<Review> reviewRepository = new FileRepository<>("reviewsTest.dat");
        Customer customer = new Customer(1, "Reviewer", "reviewer@test.com", "pass", "Customer", 100.0f, new ArrayList<>(), new ArrayList<>(), null);
        Game game = new Game(1, "Reviewed Game", "Great game", GameGenre.RPG, 49.99f, new ArrayList<>());
        Review review = new Review(1, "Awesome Game!", customer, game);

        reviewRepository.create(review);
        assertEquals("Awesome Game!", reviewRepository.get(1).getText());

        Review updatedReview = new Review(1, "Even better!", customer, game);
        reviewRepository.update(updatedReview);
        assertEquals("Even better!", reviewRepository.get(1).getText());

        reviewRepository.delete(1);
        assertNull(reviewRepository.get(1));
    }

    @Test
    public void TestBuyingGame() {
        FileRepository<Customer> customerRepository = new FileRepository<>("customersTest2.dat");
        FileRepository<Game> gameRepository = new FileRepository<>("games.dat");
        ShoppingCart shoppingCart = new ShoppingCart(null, new ArrayList<>());

        Customer customer = new Customer(1, "TestUser", "test@test.com", "password123", "Customer", 100.0f, new ArrayList<>(), new ArrayList<>(), shoppingCart);
        Game game = new Game(1, "TestGame", "A test game description", GameGenre.ADVENTURE, 29.99f, new ArrayList<>());

        customerRepository.create(customer);
        gameRepository.create(game);

        shoppingCart.getListOfGames().add(game);
        assertTrue(shoppingCart.getListOfGames().contains(game));
        assertEquals(1, shoppingCart.getListOfGames().size());

        float walletBefore = customer.getFundWallet();
        float totalPrice = game.getPrice();

        if (walletBefore >= totalPrice) {
            customer.setFundWallet(walletBefore - totalPrice);
            customer.getGamesLibrary().add(game);
            shoppingCart.getListOfGames().clear();
        }

        assertEquals(walletBefore - totalPrice, customer.getFundWallet(), 0.01);

        assertTrue(customer.getGamesLibrary().contains(game));
        assertEquals(1, customer.getGamesLibrary().size());
        assertTrue(shoppingCart.getListOfGames().isEmpty());
    }

    @Test
    public void TestAddReview() {
        FileRepository<Game> gameRepository = new FileRepository<>("gamesTest2.dat");
        FileRepository<Review> reviewRepository = new FileRepository<>("reviewsTest2.dat");
        FileRepository<Customer> customerRepository = new FileRepository<>("customersTest3.dat");

        Customer customer = new Customer(1, "john_doe", "john@example.com", "password", "Customer", 100f, new ArrayList<>(), new ArrayList<>(), new ShoppingCart(null, new ArrayList<>()));
        Game game = new Game(1, "Test Game", "Description", GameGenre.ACTION, 19.99f, new ArrayList<>());

        customerRepository.create(customer);
        gameRepository.create(game);

        Review review = new Review(1, "Great game!", customer, game);
        reviewRepository.create(review);

        Review savedReview = reviewRepository.get(1);
        assertNotNull(savedReview);
        assertEquals("Great game!", savedReview.getText());
        assertEquals("Test Game", savedReview.getGame().getGameName());

        reviewRepository.delete(1);
        customerRepository.delete(1);
        gameRepository.delete(1);
    }

    @Test
    public void TestAddGameAsDeveloper() {
        FileRepository<Developer> developerRepository = new FileRepository<>("developersTest2.dat");
        FileRepository<Game> gameRepository = new FileRepository<>("gamesTest3.dat");

        Developer developer = new Developer(1, "DevUser", "dev@test.com", "devpass123", "Developer", new ArrayList<>());
        developerRepository.create(developer);

        Game newGame = new Game(1, "New Game", "A new game description", GameGenre.ACTION, 49.99f, new ArrayList<>());

        developer.getPublishedGames().add(newGame);
        gameRepository.create(newGame);

        Game fetchedGame = gameRepository.get(1);
        assertNotNull(fetchedGame);
        assertEquals("New Game", fetchedGame.getGameName());

        assertTrue(developer.getPublishedGames().contains(newGame));
        assertEquals(1, developer.getPublishedGames().size());
    }

    @Test
    public void TestSortAndFilterGames() {
        FileRepository<Game> gameRepository = new FileRepository<>("gamesTest4.dat");
        gameRepository.create(new Game(1, "Cyber Adventure", "Explore a cyber city", GameGenre.ADVENTURE, 59.99f, new ArrayList<>()));
        gameRepository.create(new Game(2, "Space Warfare", "Epic space battles", GameGenre.SHOOTER, 49.99f, new ArrayList<>()));
        gameRepository.create(new Game(3, "Mystic Quest", "Solve magical mysteries", GameGenre.RPG, 39.99f, new ArrayList<>()));
        gameRepository.create(new Game(4, "Farm Builder", "Manage your farm", GameGenre.RPG, 19.99f, new ArrayList<>()));
        gameRepository.create(new Game(5, "Puzzle Challenge", "Solve mind-bending puzzles", GameGenre.PUZZLE, 9.99f, new ArrayList<>()));
        CustomerService customerService = new CustomerService(gameRepository, null, null, null, null, null);

        List<Game> sortedByName = customerService.sortGamesByNameAscending();
        assertEquals("Cyber Adventure", sortedByName.get(0).getGameName());
        assertEquals("Farm Builder", sortedByName.get(1).getGameName());
        assertEquals("Mystic Quest", sortedByName.get(2).getGameName());

        List<Game> sortedByPrice = customerService.sortGamesByPriceDescending();
        assertEquals("Cyber Adventure", sortedByPrice.get(0).getGameName());
        assertEquals("Space Warfare", sortedByPrice.get(1).getGameName());
        assertEquals("Mystic Quest", sortedByPrice.get(2).getGameName());

        List<Game> filteredByGenre = customerService.filterByGenre("RPG");
        assertEquals(2, filteredByGenre.size());
        assertTrue(filteredByGenre.stream().allMatch(game -> game.getGameGenre() == GameGenre.RPG));

        List<Game> filteredByPriceRange = customerService.filterGamesByPriceRange(10.0f, 40.0f);
        assertEquals(2, filteredByPriceRange.size());
        assertTrue(filteredByPriceRange.stream().allMatch(game -> game.getPrice() >= 10.0f && game.getPrice() <= 40.0f));
    }


}
