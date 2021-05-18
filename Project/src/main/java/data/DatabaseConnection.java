package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;

    /**
     * Establish a connection to the Database
     * @return Connection
     */
    private static Connection createConnection() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            return DriverManager.getConnection(
                    "jdbc:postgresql://ec2-34-250-16-127.eu-west-1.compute.amazonaws.com:5432/d82q285u8akq13",
                    "pjmqbdledqjucs",
                    "030c8df346d06432b9a5a4ed0bf42e56dc34761fda8d3cc04af8085dfb4f7c2b");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConnection(){
        if (connection == null) {
            connection = createConnection();
        }
        return connection;
    }
}
