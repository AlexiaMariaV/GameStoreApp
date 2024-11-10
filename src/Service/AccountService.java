package Service;
import Model.Admin;
import Model.Customer;
import Model.User;
import Repository.IRepository;

import java.util.List;

public class AccountService {
    private final IRepository<User> userIRepository;
    private User currentUser;

    public AccountService(IRepository<User> userIRepository) {
        this.userIRepository = userIRepository;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<User> getAllUsers() {
        return userIRepository.getAll();
    }

    public boolean takenUsername(String username) {
        for (User user : userIRepository.getAll()) {
            if (user.getUsername().equals(username)) {
                return true; // Username exists
            }
        }
        return false; // Username is unique
    }

    public boolean adminEmail(String email) {
        return email.endsWith("@adm.com");
    }
    public boolean developerEmail(String email) {
        return email.endsWith("@dev.com");
    }

    public boolean createAccount(String role, String username, String email, String password) {
        if (takenUsername(username)) {
            return false;
        }

        int newID = userIRepository.getAll().size() + 1;

        User newUser;
        if ("Customer".equalsIgnoreCase(role)) newUser = new Customer(newID, username, email, password, "Customer",0, 0, 0, 0);
        else if ("Admin".equalsIgnoreCase(role) && adminEmail(email))
            newUser = new Admin(newID, username, email, password);
        else if ("Developer".equalsIgnoreCase(role) && developerEmail(email))
            newUser = new Admin(newID, username, email, password);

        else return false;


        userIRepository.create(newUser);
        return true;
    }
    public boolean login(String username, String password) {
        for (User user : userIRepository.getAll()) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    currentUser = user;
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean logout() {
        if (currentUser != null) {
            currentUser = null;
            return true;
        }
        return false;
    }


    public boolean deleteAccount(int id) {
        if (currentUser == null || !(currentUser instanceof Admin)) {
            return false;
        }


        boolean found = false;
        for (User user : userIRepository.getAll()) {
            if (user.getUserId() == id) {
                found = true;
                break;
            }
        }


        if (found) {
            userIRepository.delete(id);
            return true;
        }
        return false;
    }
}
