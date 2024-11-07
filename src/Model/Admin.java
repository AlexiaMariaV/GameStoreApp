package Model;

public class Admin extends User {

    public Admin(String username, Integer userId, String password) {
        super(username, userId, password);
    }

    @Override
    public Integer getId() {
        return userId;
    }
}
