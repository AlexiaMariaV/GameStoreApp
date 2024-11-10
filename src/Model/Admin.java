package Model;

public class Admin extends User {

    public Admin(Integer userId, String username, String email, String password, String role) {
        super(userId, username, email, password, role);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "userId='" + userId + '\'' +
                ", username=" +  username + '\''+
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public Integer getId() {
        return userId;
    }
}
