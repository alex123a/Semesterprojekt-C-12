package domain.authentication;

import java.sql.*;

public class Test {
    private static Connection connection = null;

    public static void main(String[] args) {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://ec2-34-250-16-127.eu-west-1.compute.amazonaws.com:5432/d82q285u8akq13",
                    "pjmqbdledqjucs",
                    "030c8df346d06432b9a5a4ed0bf42e56dc34761fda8d3cc04af8085dfb4f7c2b");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        PreparedStatement insertStatement = null;
        try {
            insertStatement = connection.prepareStatement("Select * from users");
            ResultSet resultSet = insertStatement.executeQuery();
            resultSet.next();
            System.out.println(resultSet.getString("user_password"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
