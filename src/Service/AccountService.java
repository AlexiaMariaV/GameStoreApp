package Service;

import Model.*;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountService {
    private final IRepository<User> userRepository;
    private User loggedInUser;

    public AccountService(IRepository<User> userRepository) {
        this.userRepository = userRepository;
    }

    public boolean signUp(String username, String email, String password) {
        if (isEmailUsed(email)) {
            System.out.println("Email is already used!");
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
        System.out.println("User created successfully!");
        return true;
    }

    public boolean logIn(String email, String password) {
        Optional<User> user = userRepository.getAll().stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst();

        if (user.isPresent()) {
            loggedInUser = user.get();
            System.out.println("Successful authentication for user: " + loggedInUser.getUsername());
            return true;
        } else {
            System.out.println("Wrong email or password!");
            return false;
        }
    }

    public void logOut() {
        if (loggedInUser != null) {
            System.out.println("Successful log out for user: " + loggedInUser.getUsername());
            loggedInUser = null;
        } else {
            System.out.println("No logged in user found!");
        }
    }

    private boolean isEmailUsed(String email) {
        for (User user : userRepository.getAll()) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    private String determineRoleByEmail(String email) {
        if (email.endsWith("@adm.com")) {
            return "Admin";
        } else if (email.endsWith("@dev.com")) {
            return "Developer";
        } else {
            return "Customer";
        }
    }

    public boolean deleteAccount() {
        if (loggedInUser != null) {
            userRepository.delete(loggedInUser.getId());
            System.out.println("Account deleted successfully for user: " + loggedInUser.getUsername());
            loggedInUser = null;
            return true;
        } else {
            System.out.println("No user is logged in to delete.");
            return false;
        }
    }

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
            System.out.println("A user with given email does not exist.");
            return false;
        }

        userRepository.delete(userToDelete.getId());
        System.out.println("User " + userToDelete.getUsername() + " was successfully deleted.");
        return true;
    }


    public User getLoggedInUser() {
        return loggedInUser;
    }
}