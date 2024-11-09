package Model;

public class Admin extends User {

    public Admin(String username, Integer userId, String password) {
        super(username, userId, password);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", userId=" + userId +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public Integer getId() {
        return userId;
    }
}
