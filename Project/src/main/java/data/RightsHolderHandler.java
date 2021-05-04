package data;

import Interfaces.IRightsholder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class RightsHolderHandler {
    private static Connection connection = null;
    // Singleton
    private static final RightsHolderHandler rhHandler = new RightsHolderHandler();

    private RightsHolderHandler() {
        try {
            connection = getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Establish a connection to the Database
     * @return Connection
     */
    private Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
        return DriverManager.getConnection(
                "ec2-34-250-16-127.eu-west-1.compute.amazonaws.com:5432/d82q285u8akq13",
                "pjmqbdledqjucs",
                "030c8df346d06432b9a5a4ed0bf42e56dc34761fda8d3cc04af8085dfb4f7c2b");
    }

    static RightsHolderHandler getInstance() {
        return rhHandler;
    }
}
