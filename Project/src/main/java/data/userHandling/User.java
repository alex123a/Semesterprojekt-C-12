package data.userHandling;

import Interfaces.IUser;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class User implements IUser {

    int ID;
    String username;
    String password;

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