package Service;

import Model.*;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import Exception.BusinessLogicException;

/**
 * Service class for managing user accounts, including authentication, role-based signup, and account deletion.
 */

public class AccountService {
    private final IRepository<User> userRepository; //ambele
    private final IRepository<Admin> adminRepository;
    private final  IRepository<Developer> developerRepository;
    private final IRepository<Customer> customerRepository;
    private User loggedInUser;

    /**
     * Constructs the AccountService with a user repository.
     * @param userRepository The repository for storing and retrieving users.
     */

    public AccountService(IRepository<User> userRepository, IRepository<Admin> adminRepository, IRepository<Developer> developerRepository, IRepository<Customer> customerRepository) {

        this.userRepository = userRepository; //ambele
        this.adminRepository = adminRepository;
        this.developerRepository = developerRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Registers a new user with the given details and assigns a role based on the email domain.
     * @param username The username for the new account.
     * @param email The email for the new account.
     * @param password The password for the new account.
     * @return true if registration is successful, false otherwise.
     */

    public boolean signUp(String username, String email, String password) {
        if (isEmailUsed(email)) {
            return false;
        }

        String role = determineRoleByEmail(email);

        int userId = userRepository.getAll().size() + 1;

        User newUser;
        switch (role) {
            case "Admin":
                newUser = new Admin(userId, username, email, password, role);
                adminRepository.create((Admin) newUser);
                break;
            case "Developer":
                newUser = new Developer(userId, username, email, password, role, new ArrayList<Game>());
                developerRepository.create((Developer) newUser);
                break;
            default:
                newUser = new Customer(userId, username, email, password, role, 0.0f, List.of(), List.of(), new ShoppingCart(null, List.of()));
                customerRepository.create((Customer) newUser);
                break;
        }
        userRepository.create(newUser);
//        executeAction(newUser, "create");
        return true;
    }

    /**
     * Authenticates a user with their email and password.
     * @param email The email of the user.
     * @param password The password of the user.
     * @return true if login is successful, false otherwise.
     */

    public boolean logIn(String email, String password) {
        for (User u : userRepository.getAll()) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                loggedInUser = u;
                System.out.println("Successful authentication for user: " + loggedInUser.getUsername());
                return true;
            }
        }
        throw new BusinessLogicException("Wrong email or password.");
    }

    /**
     * Logs out the currently logged-in user.
     * @return true if the user was logged out successfully, false if no user was logged in.
     */

    public boolean logOut() {
        if (loggedInUser != null) {
            loggedInUser = null;
            return true;
        }
        throw new BusinessLogicException("No user is logged in to log out.");
    }


    /**
     * Checks if a given email is already in use by an existing user.
     * @param email The email to check.
     * @return true if the email is in use, false otherwise.
     */

    private boolean isEmailUsed(String email) {
        for (User user : userRepository.getAll()) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines the role of a user based on their email domain.
     * @param email The email of the user.
     * @return A string representing the role, either "Admin", "Developer", or "Customer".
     */

    private String determineRoleByEmail(String email) {
        if (email.endsWith("@adm.com")) {
            return "Admin";
        } else if (email.endsWith("@dev.com")) {
            return "Developer";
        } else {
            return "Customer";
        }
    }

    /**
     * Deletes the currently logged-in user's account.
     * @return true if the account was deleted, false otherwise.
     */

    public boolean deleteAccount() {
        if (loggedInUser != null) {
            userRepository.delete(loggedInUser.getId());
            executeAction(loggedInUser, "delete");
            loggedInUser = null;
            return true;
        } else {
            throw new BusinessLogicException("No user is logged in to delete.");
        }
    }

    /**
     * Deletes any user account by email (admin-only function).
     * @param email The email of the account to delete.
     * @return true if the account was deleted, false otherwise.
     */

    public boolean deleteAnyAccount(String email) {

        List<User> users = userRepository.getAll();
        User userToDelete = null;

        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                userToDelete = user;
                break;
            }
        }
        if (userToDelete == null) {
            throw new BusinessLogicException("User with the given email does not exist.");
        }

        userRepository.delete(userToDelete.getId());
        executeAction(userToDelete, "delete");
        return true;
    }

    private void executeAction(User user, String action) {
        switch (user.getRole()) {
            case "Admin" -> executeForAdmin((Admin) user, action);
            case "Developer" -> executeForDeveloper((Developer) user, action);
            case "Customer" -> executeForCustomer((Customer) user, action);
            default -> throw new IllegalArgumentException("Invalid user role");
        }
    }

    private void executeForAdmin(Admin admin, String action) {
        switch (action) {
            case "create" -> adminRepository.create(admin);
            case "update" -> adminRepository.update(admin);
            case "delete" -> adminRepository.delete(admin.getId());
            default -> throw new IllegalArgumentException("Invalid action");
        }
    }

    private void executeForDeveloper(Developer developer, String action) {
        switch (action) {
            case "create" -> developerRepository.create(developer);
            case "update" -> developerRepository.update(developer);
            case "delete" -> developerRepository.delete(developer.getId());
            default -> throw new IllegalArgumentException("Invalid action");
        }
    }

    private void executeForCustomer(Customer customer, String action) {
        switch (action) {
            case "create" -> customerRepository.create(customer);
            case "update" -> customerRepository.update(customer);
            case "delete" -> customerRepository.delete(customer.getId());
            default -> throw new IllegalArgumentException("Invalid action");
        }
    }


    /**
     * Retrieves the currently logged-in user.
     * @return The logged-in user, or null if no user is logged in.
     */

    public User getLoggedInUser() {
        return loggedInUser;
    }
}