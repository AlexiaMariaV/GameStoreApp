package Service;

import Model.*;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing user accounts, including authentication, role-based signup, and account deletion.
 */

public class AccountService {
    private final IRepository<User> userRepository;
    private User loggedInUser;

    /**
     * Constructs the AccountService with a user repository.
     * @param userRepository The repository for storing and retrieving users.
     */

    public AccountService(IRepository<User> userRepository) {
        this.userRepository = userRepository;
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
                break;
            case "Developer":
                newUser = new Developer(userId, username, email, password, role, new ArrayList<Game>());
                break;
            default:
                newUser = new Customer(userId, username, email, password, role, 0.0f, List.of(), List.of(), new ShoppingCart(null, List.of()));
                break;
        }

        userRepository.create(newUser);
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
        System.out.println("Wrong email or password!");
        return false;
    }

    /**
     * Logs out the currently logged-in user.
     */

    public boolean logOut() {
        if (loggedInUser != null) {
            loggedInUser = null;
            return true;
        }
        return false;
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
            loggedInUser = null;
            return true;
        } else {
            System.out.println("No user is logged in to delete.");
            return false;
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
            return false;
        }

        userRepository.delete(userToDelete.getId());
        return true;
    }

    /**
     * Retrieves the currently logged-in user.
     * @return The logged-in user, or null if no user is logged in.
     */

    public User getLoggedInUser() {
        return loggedInUser;
    }
}