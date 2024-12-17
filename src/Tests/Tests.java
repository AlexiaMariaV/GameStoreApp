package Tests;
import Controller.DeveloperController;
import Model.*;
import Service.DeveloperService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Repository.FileRepository;

import java.util.ArrayList;
import java.util.List;

public class Tests {

    @Test
    public void TestCRUDCustomer() {
        FileRepository<Customer> customerRepository = new FileRepository<>("customers.dat");
        Customer customer = new Customer(1, "TestUser", "test@test.com", "password123", "Customer", 100.0f, new ArrayList<>(), new ArrayList<>(), null);

        // Create
        customerRepository.create(customer);
        assertEquals("TestUser", customerRepository.get(1).getUsername());

        // Read
        Customer fetchedCustomer = customerRepository.get(1);
        assertNotNull(fetchedCustomer);
        assertEquals("test@test.com", fetchedCustomer.getEmail());

        // Update
        Customer updatedCustomer = new Customer(1, "UpdatedUser", "updated@test.com", "newpass", "Customer", 200.0f, new ArrayList<>(), new ArrayList<>(), null);
        customerRepository.update(updatedCustomer);
        assertEquals("UpdatedUser", customerRepository.get(1).getUsername());

        // Delete
        customerRepository.delete(1);
        assertNull(customerRepository.get(1));
    }

    @Test
    public void TestCRUDAdmin() {
        FileRepository<Admin> adminRepository = new FileRepository<>("admins.dat");
        Admin admin = new Admin(1, "AdminUser", "admin@test.com", "adminpass", "Admin");

        // Create
        adminRepository.create(admin);
        assertEquals("AdminUser", adminRepository.get(1).getUsername());

        // Read
        Admin fetchedAdmin = adminRepository.get(1);
        assertNotNull(fetchedAdmin);
        assertEquals("admin@test.com", fetchedAdmin.getEmail());

        // Update
        Admin updatedAdmin = new Admin(1, "UpdatedAdmin", "updated@test.com", "newadminpass", "Admin");
        adminRepository.update(updatedAdmin);
        assertEquals("UpdatedAdmin", adminRepository.get(1).getUsername());

        // Delete
        adminRepository.delete(1);
        assertNull(adminRepository.get(1));
    }

    @Test
    public void TestCRUDDeveloper() {}

    @Test
    public void TestCRUDGame() {}

    @Test
    public void TestCRUDDiscount() {
        FileRepository<Discount> discountRepository = new FileRepository<>("discounts.dat");
        Discount discount = new Discount(1, 20.0f);

        // Create
        discountRepository.create(discount);
        assertEquals(20.0f, discountRepository.get(1).getDiscountPercentage());

        // Update
        Discount updatedDiscount = new Discount(1, 30.0f);
        discountRepository.update(updatedDiscount);
        assertEquals(30.0f, discountRepository.get(1).getDiscountPercentage());

        // Delete
        discountRepository.delete(1);
        assertNull(discountRepository.get(1));
    }

    @Test
    public void TestCRUDReview() {
        FileRepository<Review> reviewRepository = new FileRepository<>("reviews.dat");
        Customer customer = new Customer(1, "Reviewer", "reviewer@test.com", "pass", "Customer", 100.0f, new ArrayList<>(), new ArrayList<>(), null);
        Game game = new Game(1, "Reviewed Game", "Great game", GameGenre.RPG, 49.99f, new ArrayList<>());
        Review review = new Review(1, "Awesome Game!", customer, game);

        // Create
        reviewRepository.create(review);
        assertEquals("Awesome Game!", reviewRepository.get(1).getText());

        // Update
        Review updatedReview = new Review(1, "Even better!", customer, game);
        reviewRepository.update(updatedReview);
        assertEquals("Even better!", reviewRepository.get(1).getText());

        // Delete
        reviewRepository.delete(1);
        assertNull(reviewRepository.get(1));
    }

    @Test
    public void TestBuyingGame() {}

    @Test
    public void TestAddReview() {
        FileRepository<Game> gameRepository = new FileRepository<>("games.dat");
        FileRepository<Review> reviewRepository = new FileRepository<>("reviews.dat");
        FileRepository<Customer> customerRepository = new FileRepository<>("customers.dat");

        // Creare obiecte necesare
        Customer customer = new Customer(1, "john_doe", "john@example.com", "password", "Customer", 100f, new ArrayList<>(), new ArrayList<>(), new ShoppingCart(null, new ArrayList<>()));
        Game game = new Game(1, "Test Game", "Description", GameGenre.ACTION, 19.99f, new ArrayList<>());

        customerRepository.create(customer);
        gameRepository.create(game);

        // Adaugarea unui Review
        Review review = new Review(1, "Great game!", customer, game);
        reviewRepository.create(review);

        // Verificare
        Review savedReview = reviewRepository.get(1);
        assertNotNull(savedReview);
        assertEquals("Great game!", savedReview.getText());
        assertEquals("Test Game", savedReview.getGame().getGameName());

        // Cleanup
        reviewRepository.delete(1);
        customerRepository.delete(1);
        gameRepository.delete(1);
    }

    @Test
    public void TestAddGameAsDeveloper() {}

    @Test
    public void TestSortAndFilterGames() {}


}
