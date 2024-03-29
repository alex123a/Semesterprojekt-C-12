package data.userHandling;

import Interfaces.*;
import data.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager implements IUserFacade {
    private static Connection connection = null;
    private static final UserManager USERMANAGER = new UserManager();

    private UserManager() {
            connection = DatabaseConnection.getConnection();
    }

    //Selects the users password from the database from their username
    @Override
    public String getDatabasePassword(IUser user) {
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT user_password FROM users WHERE username = ?");
            queryStatement.setString(1, user.getUsername());
            ResultSet resultSet = queryStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("user_password");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
//  Retrieves users whose username is like the username from the tempUser object.
    public List<IUser> getUsersBySearch(IUser tempUser) {
        List<IUser> matches = new ArrayList<>();
        IUser user = null;
        try {
//          Search patteren for the SQL statement in the like clause.
            String searchPatteren = "%" + tempUser.getUsername() + "%";
            PreparedStatement selectStatement = connection.prepareStatement("SELECT id, username FROM users WHERE username LIKE ?");
            selectStatement.setString(1, searchPatteren);
            ResultSet selectResult = selectStatement.executeQuery();

//          Makes the correct user object depending on the usertype, by checking if the userid is in producer table or not.
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
//  Retrieves an user from the database by the username. This is for when the program only knows a users username and needs the other information.
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
//  Gets all users from the database.
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
                while (queryResultSet.next()) {
                    list.add(new Producer(queryResultSet.getInt("id"), queryResultSet.getString("username"), queryResultSet.getString("user_password")));
                }
            }
            //Administrator
            PreparedStatement adminStatement = connection.prepareStatement("SELECT * FROM administrator");
            ResultSet adminResult = adminStatement.executeQuery();
            while (adminResult.next()) {
                PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
                queryStatement.setInt(1,adminResult.getInt(1));
                ResultSet queryResultSet = queryStatement.executeQuery();
                while (queryResultSet.next()) {
                    list.add(new SystemAdministrator(queryResultSet.getInt("id"), queryResultSet.getString("username"), queryResultSet.getString("user_password")));
                }
            }
        } catch (SQLException exception) {
            System.out.println("Error getting users");
            System.out.println(exception.getMessage());
        }
        return list;
    }

    @Override
//  Retrieves all producers from the database
    public List<IUser> getAllProducers() {
        List<IUser> list = new ArrayList<>();
        try {
            //Producer
            PreparedStatement producerStatement = connection.prepareStatement("SELECT * FROM producer");
            ResultSet producerResult = producerStatement.executeQuery();
            while (producerResult.next()) {
                PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
                queryStatement.setInt(1,producerResult.getInt("id"));
                ResultSet queryResultSet = queryStatement.executeQuery();
                while (queryResultSet.next()) {
                    list.add(new Producer(queryResultSet.getInt("id"), queryResultSet.getString("username"), queryResultSet.getString("user_password")));
                }
            }
        } catch (SQLException exception) {
            System.out.println("Error getting users");
            System.out.println(exception.getMessage());
        }
        return list;
    }

    @Override
//  Removes an user from the database by the users id.
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
//  Updates an users information in the database.
    public boolean editUser(IUser user) {
        try {
            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM users");
            ResultSet selectResult = selectStatement.executeQuery();
            while(selectResult.next()) {
//              Checks if the user has got an new username that already exists to another user by comparing the usernames and user ids
                if (selectResult.getInt("id") != user.getId() && selectResult.getString("username").equals(user.getUsername())) {
                    return false;
                }
            }
//          If the username does not exists or if it is the current users username the information will be updated.
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
//  Adds a new user to the database.
    public boolean addUser(IUser user) {
        try {
            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM users");
            ResultSet selectResult = selectStatement.executeQuery();
            while(selectResult.next()) {
//              Checks if the user has got an new username that already exists to another user by comparing the usernames and user ids
                if (selectResult.getInt("id") != user.getId() && selectResult.getString("username").equals(user.getUsername())) {
                    return false;
                }
            }
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO users(username, user_password) VALUES (?, ?)");
            insertStatement.setString(1, user.getUsername());
            insertStatement.setString(2, user.getPassword());
            insertStatement.execute();
//          Gets the userid from the new user to be able to insert the user in the correct producer or systemadmin table.
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
}
