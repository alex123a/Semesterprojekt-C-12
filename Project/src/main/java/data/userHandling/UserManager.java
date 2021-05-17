package data.userHandling;

import Interfaces.IUser;
import Interfaces.IUserHandling;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager implements IUserHandling {
    private static Connection connection = null;
    private static final UserManager USERMANAGER = new UserManager();

    private UserManager() {
        try {
            connection = getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public String getDatabasePassword(String username) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT password FROM users WHERE username = ?");
            queryStatement.setString(1,username);
            ResultSet resultSet = queryStatement.executeQuery();
            return resultSet.getString("user_password");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<IUser> getUsers() {
        List<IUser> list = new ArrayList<>();
        try {
            //Producer
            PreparedStatement producerStatement = connection.prepareStatement("SELECT * FROM producer");
            ResultSet producerResult = producerStatement.executeQuery();
            while (producerResult.next()) {
                PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
                queryStatement.setInt(1,producerResult.getInt("id"));
                ResultSet queryResultSet = queryStatement.executeQuery();
                list.add(new Producer(queryResultSet.getInt("id"), queryResultSet.getString("username"), queryResultSet.getString("user_password")));
            }
            //Administrator
            PreparedStatement adminStatement = connection.prepareStatement("SELECT * FROM producer");
            ResultSet adminResult = adminStatement.executeQuery();
            while (adminResult.next()) {
                PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
                queryStatement.setInt(1,adminResult.getInt("id"));
                ResultSet queryResultSet = queryStatement.executeQuery();
                list.add(new SystemAdministrator(queryResultSet.getInt("id"), queryResultSet.getString("username"), queryResultSet.getString("user_password")));
            }
        } catch (SQLException exception) {
            System.out.println("Error getting users");
            System.out.println(exception.getMessage());
        }
        return list;
    }

    @Override
    public boolean makeUserProducer(IUser user) {
        try {
            PreparedStatement selectStatement = connection.prepareStatement("SELECT id FROM producer WHERE id = ?");
            selectStatement.setInt(1, user.getId());
            ResultSet result = selectStatement.executeQuery();
            if (result != null) {
                PreparedStatement deleteStatement = connection.prepareStatement("DELETE id FROM administrator WHERE id = ?");
                deleteStatement.setInt(1, user.getId());
                deleteStatement.execute();
            }
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO producer(id) VALUES (?)");
            insertStatement.setInt(1, user.getId());
            insertStatement.execute();
            return true;
        } catch (SQLException exception) {
            System.out.println("Error making user producer");
            System.out.println(exception.getMessage());
        }
        return false;
    }

    @Override
    public boolean makeUserAdmin(IUser user) {
        try {
            PreparedStatement selectStatement = connection.prepareStatement("SELECT id FROM producer WHERE id = ?");
            selectStatement.setInt(1, user.getId());
            ResultSet result = selectStatement.executeQuery();
            if (result != null) {
                PreparedStatement deleteStatement = connection.prepareStatement("DELETE id FROM producer WHERE id = ? ");
                deleteStatement.setInt(1, user.getId());
                deleteStatement.execute();
            }

            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO administrator(id) VALUES (?)");
            insertStatement.setInt(1, user.getId());
            insertStatement.execute();
            return true;


        } catch (SQLException exception) {
            System.out.println("Error making user administrator");
            System.out.println(exception.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteUser(IUser user) {
        try {
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE id FROM users WHERE id = ?");
            deleteStatement.setInt(1, user.getId());
            deleteStatement.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean editUser(IUser user) {
        try {
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE users SET username = ?, password = ? WHERE id = ?");
            updateStatement.setString(1,user.getUsername());
            updateStatement.setString(2,user.getPassword());
            updateStatement.setInt(3,user.getId());
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static UserManager getInstance() {
        return USERMANAGER;
    }

    /**
     * Establish a connection to the Database
     *
     * @return Connection
     */
    public Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
        return DriverManager.getConnection(
                "jdbc:postgresql://ec2-34-250-16-127.eu-west-1.compute.amazonaws.com:5432/d82q285u8akq13",
                "pjmqbdledqjucs",
                "030c8df346d06432b9a5a4ed0bf42e56dc34761fda8d3cc04af8085dfb4f7c2b");
    }
}
