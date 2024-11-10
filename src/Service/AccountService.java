package Service;

import Model.*;
import Repository.IRepository;

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
            System.out.println("Email-ul este deja folosit!");
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
                newUser = new Developer(userId, username, email, password, role, List.of());
                break;
            default:
                newUser = new Customer(userId, username, email, password, role, 0.0f, List.of(), List.of(), new ShoppingCart(null, List.of()));
                break;
        }

        userRepository.create(newUser);
        System.out.println("Utilizator creat cu succes!");
        return true;
    }

    public boolean logIn(String email, String password) {
        Optional<User> user = userRepository.getAll().stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst();

        if (user.isPresent()) {
            loggedInUser = user.get();
            System.out.println("Autentificare reușită pentru utilizatorul: " + loggedInUser.getUsername());
            return true;
        } else {
            System.out.println("Email sau parolă incorecte!");
            return false;
        }
    }

    public void logOut() {
        if (loggedInUser != null) {
            System.out.println("Deconectare reușită pentru utilizatorul: " + loggedInUser.getUsername());
            loggedInUser = null;
        } else {
            System.out.println("Niciun utilizator conectat!");
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
        if (email.endsWith("adm@gmail.com")) {
            return "Admin";
        } else if (email.endsWith("dev@gmail.com")) {
            return "Developer";
        } else {
            return "Customer";
        }
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}