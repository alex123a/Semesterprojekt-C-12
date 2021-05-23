package data.userHandling;

import Interfaces.IAdministrator;
import Interfaces.IProducer;
import Interfaces.IUser;
import Interfaces.IUserHandling;

import javax.xml.transform.Result;
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
    public String getDatabasePassword(IUser user) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT user_password FROM users WHERE username = ?");
            queryStatement.setString(1, user.getUsername());
            ResultSet resultSet = queryStatement.executeQuery();
            resultSet.next();
            return resultSet.getString("user_password");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<IUser> getUsersBySearch(IUser tempUser) {
        List<IUser> matches = new ArrayList<>();
        IUser user = null;
        try {
            String searchPatteren = "%" + tempUser.getUsername() + "%";
            PreparedStatement selectStatement = connection.prepareStatement("SELECT id, username FROM users WHERE username LIKE ?");
            selectStatement.setString(1, searchPatteren);
            ResultSet selectResult = selectStatement.executeQuery();
            while(selectResult.next()) {
                PreparedStatement selectProducerId = connection.prepareStatement("SELECT id FROM producer WHERE id = ?");
                selectProducerId.setInt(1, selectResult.getInt("id"));
                ResultSet producerIdResult = selectProducerId.executeQuery();
                if (producerIdResult.next()) {
                    if(producerIdResult.getInt("id") == selectResult.getInt("id")) {
                        user = new Producer(selectResult.getString("username"));
                    }
                } else {
                    user = new SystemAdministrator(selectResult.getString("username"));
                }
                matches.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matches;
    }

    @Override
    public IUser getUser(IUser user) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            statement.setString(1, user.getUsername());
            ResultSet result = statement.executeQuery();
            int id = 0;
            String username = "";
            String password = "";
            while (result.next()) {
                id = result.getInt(1);
                username = result.getString(2);
                password = result.getString(3);
            }

            List<Integer> adminList = new ArrayList<>();
            List<Integer> producerList = new ArrayList<>();

            // Here we select all IDs from administrator and producer
            PreparedStatement admins = connection.prepareStatement("SELECT id FROM administrator");
            ResultSet resultAdmin = admins.executeQuery();
            while (resultAdmin.next()) {
                adminList.add(resultAdmin.getInt(1));
            }
            PreparedStatement producers = connection.prepareStatement("SELECT id FROM producer");
            ResultSet resultProducer = producers.executeQuery();
            while (resultProducer.next()) {
                producerList.add(resultProducer.getInt(1));
            }
            // Finding out which list the user is and returns the user with the correct authentication/user type
            for (Integer theId: adminList) {
                if (theId == id) {
                    IAdministrator administrator = new SystemAdministrator(theId, username, password);
                    return administrator;
                }
            }

            for (Integer theId: producerList) {
                if (theId == id) {
                    IProducer producer = new Producer(theId, username, password);
                    return producer;
                }
            }

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
                queryStatement.setInt(1,producerResult.getInt(1));
                ResultSet queryResultSet = queryStatement.executeQuery();
                queryResultSet.next();
                list.add(new Producer(queryResultSet.getInt(1), queryResultSet.getString(2), queryResultSet.getString(3)));
            }
            //Administrator
            PreparedStatement adminStatement = connection.prepareStatement("SELECT * FROM administrator");
            ResultSet adminResult = adminStatement.executeQuery();
            while (adminResult.next()) {
                PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
                queryStatement.setInt(1,adminResult.getInt(1));
                ResultSet queryResultSet = queryStatement.executeQuery();
                queryResultSet.next();
                list.add(new SystemAdministrator(queryResultSet.getInt(1), queryResultSet.getString(2), queryResultSet.getString(3)));
            }
        } catch (SQLException exception) {
            System.out.println("Error getting users");
            System.out.println(exception.getMessage());
        }
        return list;
    }

    @Override
    public boolean deleteUser(IUser user) {
        try {
            PreparedStatement deleteStatement = connection.prepareStatement(" DELETE FROM users WHERE id = ? ");
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
            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM users");
            ResultSet selectResult = selectStatement.executeQuery();
            while(selectResult.next()) {
                if (selectResult.getInt("id") != user.getId() && selectResult.getString("username").equals(user.getUsername())) {
                    return false;
                }
            }
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE users SET username = ?, user_password = ? WHERE id = ?");
            updateStatement.setString(1,user.getUsername());
            updateStatement.setString(2,user.getPassword());
            updateStatement.setInt(3,user.getId());
            updateStatement.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addUser(IUser user) {
        try {
            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM users");
            ResultSet selectResult = selectStatement.executeQuery();
            while(selectResult.next()) {
                if (selectResult.getInt("id") != user.getId() && selectResult.getString("username").equals(user.getUsername())) {
                    return false;
                }
            }
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO users(username, user_password) VALUES (?, ?)");
            insertStatement.setString(1, user.getUsername());
            insertStatement.setString(2, user.getPassword());
            insertStatement.execute();

            PreparedStatement selectUserID = connection.prepareStatement("SELECT id FROM users WHERE username = ?");
            selectUserID.setString(1, user.getUsername());
            ResultSet selectIDResult = selectUserID.executeQuery();
            selectIDResult.next();
            int userID = selectIDResult.getInt("id");

            if(user instanceof IProducer) {
                insertStatement = connection.prepareStatement("INSERT INTO producer(id) VALUES (?) ");
            } else {
                insertStatement = connection.prepareStatement("INSERT INTO administrator(id) VALUES (?) ");
            }
            insertStatement.setInt(1, userID);
            insertStatement.execute();

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
