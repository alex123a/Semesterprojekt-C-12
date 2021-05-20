package presentation.userManage;

import Interfaces.IUser;

public class User implements IUser {
    int ID;
    String username;
    String password;

    public User(int ID, String username, String password) {
        this(username, password);
        this.ID = ID;
    }
    public User(String username, String password) {
        this(username);
        this.password = password;
    }
    public User(String username) {
        this.username = username;
    }
    @Override
    public int getId() {
        return ID;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
