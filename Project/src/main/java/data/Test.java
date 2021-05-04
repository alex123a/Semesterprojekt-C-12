package data;

import java.sql.*;


public class Test {
    private static Connection connection = null;


    public static void main(String[] args) {
        try {
            connection = getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM title");
            ResultSet queryResultSet = queryStatement.executeQuery();

            //Write to console
            System.out.println("The following titles matched the query:");
            while (queryResultSet.next()) {
                System.out.println("" + queryResultSet.getString("title")); //getString parameter can be column name or index
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
        return DriverManager.getConnection(
                "jdbc:postgresql://ec2-34-250-16-127.eu-west-1.compute.amazonaws.com:5432/d82q285u8akq13",
                "pjmqbdledqjucs",
                "030c8df346d06432b9a5a4ed0bf42e56dc34761fda8d3cc04af8085dfb4f7c2b");
    }
}
