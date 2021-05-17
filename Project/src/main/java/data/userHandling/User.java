package data.userHandling;

import Interfaces.IUser;

import java.sql.Connection;
import java.sql.DriverManager;
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


}
