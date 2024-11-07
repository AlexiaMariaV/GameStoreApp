package Model;

public abstract class User implements HasId {
    protected String username;
    protected Integer userId;
    protected String password;

    public User(String username, Integer userId, String password) {
        this.username = username;
        this.userId = userId;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

