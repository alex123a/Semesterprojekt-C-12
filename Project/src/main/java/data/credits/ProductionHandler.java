package data.credits;

import Interfaces.*;
import data.DatabaseConnection;
import data.PersistenceFacade;
import data.userHandling.Producer;
import data.userHandling.UserFacade;
import data.userHandling.UserManager;
import enumerations.ProductionGenre;
import enumerations.ProductionType;
import presentation.userManage.Systemadministrator;
import presentation.userManage.User;

import java.awt.color.ICC_ProfileRGB;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.*;

class ProductionHandler {
    private Connection connection = null;

    // Singleton
    private final static ProductionHandler prHandler = new ProductionHandler();

    private ProductionHandler() {
        this.connection = DatabaseConnection.getConnection();
    }

    // Gets all he productions from the normal tables (not the approval tables) in the database
    // This returns the list of productions as a consumer should see them
    List<IProduction> getProductions() {
        List<IProduction> productionList = new ArrayList<>();
        try {
            //Statement to get all productions and their attributes
            PreparedStatement productionsStatement = connection.prepareStatement("" +
                    "SELECT id, own_production_id, production_name, description, year, genre_id, category_id, producer_id " +
                    "FROM production");
            ResultSet productionsResult = productionsStatement.executeQuery();

            //For each production get the rightsholders and their roles
            while (productionsResult.next()) {
                //Statement to get all rightsholders on each production
                Production p = getProductionFromResultset(productionsResult);
                productionList.add(p);
            }
            return productionList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Reads specific production with productionID
    // If the production waits for approval, the changed version will be returned
    IProduction getProduction(IProduction production) {
        try {
            IProduction returnProduction = null;
            //Statement to get all productions and their attributes
            PreparedStatement productionsStatement = connection.prepareStatement("" +
                    "SELECT id, own_production_id, production_name, description, year, genre_id, category_id, producer_id" +
                    " FROM production_approval" +
                    " WHERE id = ?");
            productionsStatement.setInt(1, ((Production) production).getID());
            ResultSet result = productionsStatement.executeQuery();
            if (result.next()) {
                returnProduction = getProductionFromResultsetApproval(result);
            } else {
                PreparedStatement productionsStatement2 = connection.prepareStatement("" +
                        "SELECT id, own_production_id, production_name, description, year, genre_id, category_id, producer_id" +
                        " FROM production" +
                        " WHERE id = ?");
                productionsStatement2.setInt(1, ((Production) production).getID());
                ResultSet result2 = productionsStatement2.executeQuery();
                if (result2.next()) {
                    returnProduction = getProductionFromResultset(result2);
                }
            }
            return returnProduction;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Saves the production to the approval tables so it waits for approval
    IProduction saveProduction(IProduction production){
        
        PreparedStatement insertStatement = null;

        if (production instanceof Production) {
            Production p = (Production) production;

            //Run this if the production is already in the database
            try {
                //Check whether the production is already in production_approval
                PreparedStatement getProductionStatement = connection.prepareStatement("SELECT * FROM production_approval WHERE id = ?");
                getProductionStatement.setInt(1, p.getID());
                ResultSet result = getProductionStatement.executeQuery();

                if (result.next()) {
                    //Run this if the production is already in the approval table
                    insertStatement = connection.prepareStatement("" +
                            "UPDATE production_approval " +
                            "SET own_production_id=?, production_name=?, year=?, genre_id=?, category_id=?, producer_id=?, description=?" +
                            "WHERE id = ?" +
                            "RETURNING id, own_production_id, production_name, description, year, genre_id, category_id, producer_id;");
                    insertStatement.setString(1, p.getProductionID());
                    insertStatement.setString(2, p.getName());
                    insertStatement.setInt(3, p.getYear());
                    insertStatement.setInt(4, p.getGenre().getId());
                    insertStatement.setInt(5, p.getType().getId());
                    insertStatement.setInt(6, p.getProducer().getId());
                    insertStatement.setString(7, p.getDescription());
                    insertStatement.setInt(8, p.getID());

                    //Delete from "shadow" tables
                    //The data will be inserted again further down
                    PreparedStatement removeFromAppearsInApprovalStatement = connection.prepareStatement("" +
                            "DELETE FROM appears_in_approval WHERE production_id=? RETURNING id");
                    removeFromAppearsInApprovalStatement.setInt(1, p.getID());
                    ResultSet appearsInApprovalIDs = removeFromAppearsInApprovalStatement.executeQuery();
                    while (appearsInApprovalIDs.next()){
                        int appearsInApprovalID = appearsInApprovalIDs.getInt(0);
                        PreparedStatement removeFromRoleApprovalStatement = connection.prepareStatement("" +
                                "DELETE FROM role_approval WHERE appears_in_id=? RETURNING id");
                        removeFromRoleApprovalStatement.setInt(1, appearsInApprovalID);
                        ResultSet roleApprovalIDs = removeFromRoleApprovalStatement.executeQuery();
                        while (roleApprovalIDs.next()){
                            int roleApprovalID = roleApprovalIDs.getInt(1);
                            PreparedStatement removeFromRoleNameApprovalStatement = connection.prepareStatement("" +
                                    "DELETE FROM rolename_approval WHERE role_id=?");
                            removeFromRoleNameApprovalStatement.setInt(1, roleApprovalID);
                            removeFromRoleNameApprovalStatement.execute();
                        }
                    }

                } else {

                    insertStatement = connection.prepareStatement("" +
                            "INSERT INTO production_approval (id, own_production_id, production_name, year, genre_id, category_id, description, producer_id) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                            "RETURNING id, own_production_id, production_name, description, year, genre_id, category_id, producer_id;");
                    insertStatement.setInt(1, p.getID());
                    insertStatement.setString(2, p.getProductionID());
                    insertStatement.setString(3, p.getName());
                    insertStatement.setInt(4, p.getYear());
                    insertStatement.setInt(5, p.getGenre().getId());
                    insertStatement.setInt(6, p.getType().getId());
                    insertStatement.setString(7, p.getDescription());
                    insertStatement.setInt(8, p.getProducer().getId());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {

            //Run this if the production is a new production
            try {

                insertStatement = connection.prepareStatement("" +
                        "INSERT INTO production_approval (own_production_id, production_name, year, genre_id, category_id, producer_id, description) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                        "RETURNING id, own_production_id, production_name, description, year, genre_id, category_id, producer_id;");
                insertStatement.setString(1, production.getProductionID());
                insertStatement.setString(2, production.getName());
                insertStatement.setInt(3, production.getYear());
                insertStatement.setInt(4, production.getGenre().getId());
                insertStatement.setInt(5, production.getType().getId());
                insertStatement.setInt(6, production.getProducer().getId());
                insertStatement.setString(7, production.getDescription());


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        Production toReturn = null;

        try {
            ResultSet resultProduction = insertStatement.executeQuery();
            resultProduction.next();
            toReturn = getProductionFromResultsetApproval(resultProduction);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        int productionID = toReturn.getID();

        // Following block adds ALL appears_in to appears_in_approval
        Map<IRightsholder, List<String>> rightsholders = production.getRightsholders();
        for (IRightsholder rightsholder : rightsholders.keySet()) {
            //This line saves the rightsholder and gets the rightsholder with the ID back
            Rightsholder r = (Rightsholder) RightsHolderHandler.getInstance().saveRightsholder(rightsholder);

            try {
                int rightsholderID = r.getId();

                PreparedStatement insertAppears_inStatement = connection.prepareStatement("" +
                        "INSERT INTO appears_in_approval (production_id, rightsholder_id) " +
                        "VALUES (?, ?)" +
                        "RETURNING id");
                insertAppears_inStatement.setInt(1, productionID);
                insertAppears_inStatement.setInt(2, rightsholderID);
                ResultSet appears_in_id_result = insertAppears_inStatement.executeQuery();
                appears_in_id_result.next();
                int appearsinID = appears_in_id_result.getInt(1);

                //Inserts into roleapproval

                for (String role: rightsholders.get(rightsholder)){

                    //The role string might consist of e.g. "medvirkende: darth vader" therefore has to be split
                    String title;
                    if (role.contains(": ")) {
                        title = role.split(": ")[0];
                    }else {
                        title = role;
                    }

                    PreparedStatement insertRoleStatement = connection.prepareStatement("" +
                            "INSERT INTO role_approval (appears_in_id, title_id) " +
                            "VALUES (?, (SELECT id FROM title WHERE title.title = ?))" +
                            "RETURNING id");

                    insertRoleStatement.setInt(1, appearsinID);
                    insertRoleStatement.setString(2, title);
                    ResultSet roleIdResult = insertRoleStatement.executeQuery();
                    roleIdResult.next();
                    if (role.contains(": ")) {
                        PreparedStatement insertRoleNameStatement = connection.prepareStatement("" +
                                "INSERT INTO rolename_approval (role_id, rolename)" +
                                "VALUES (?, ?)");
                        insertRoleNameStatement.setInt(1, roleIdResult.getInt(1));
                        insertRoleNameStatement.setString(2, role.split(": ")[1]);
                        insertRoleNameStatement.execute();
                    }
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return toReturn;
    }

    //Delete the production (Doesn't wait for approval)
    boolean deleteProduction(IProduction production) {
        if (production instanceof Production) {
            Production p = (Production) production;
            int id = p.getID();
            try {
                PreparedStatement statement = connection.prepareStatement("" +
                        "DELETE FROM production " +
                        "WHERE id = ?");
                statement.setInt(1, id);
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }
            return true;
        } else {
            throw new IllegalArgumentException("Can't delete an IProduction that is not a Production");
        }
    }

    //A helper method that returns a production from the normal tables (not approval tables) given a resultset from an SQL query
    private Production getProductionFromResultset(ResultSet productionsResult) throws SQLException {
        PreparedStatement RightsholdersStatement = connection.prepareStatement("" +
                "SELECT DISTINCT rightsholder_id " +
                "FROM appears_in " +
                "WHERE production_id = ?");
        RightsholdersStatement.setInt(1, productionsResult.getInt(1));
        ResultSet rightsholderIDs = RightsholdersStatement.executeQuery();
        Map<Integer, List<String>> roleMap = new HashMap<>();
        while (rightsholderIDs.next()) {
            //for each rightsholder, get all the roles that rightsholder has in the production
            int id = rightsholderIDs.getInt(1);
            PreparedStatement rolesStatement = connection.prepareStatement("" +
                    "SELECT title, rolename.rolename FROM " +
                    "appears_in LEFT JOIN role ON appears_in.id = role.appears_in_id " +
                    "LEFT JOIN title ON role.title_id = title.id " +
                    "LEFT JOIN rolename ON role.id = rolename.role_id " +
                    "WHERE appears_in.production_id = ?" +
                    "AND appears_in.rightsholder_id = ?");
            rolesStatement.setInt(1, productionsResult.getInt(1));
            rolesStatement.setInt(2, id);
            ResultSet rolesResult = rolesStatement.executeQuery();
            List<String> rolesList = new ArrayList<>();
            while (rolesResult.next()) {
                String title = rolesResult.getString(1);
                if (title == null) {
                    rolesList.add("MISSING TITLE");
                } else if (title.equalsIgnoreCase("medvirkende")) {
                    rolesList.add(title + ": " + rolesResult.getString(2));
                } else {
                    rolesList.add(title);
                }
            }
            roleMap.put(id, rolesList);
        }
        //Gets the genre and type enum based on the genre and type
        ProductionGenre genre = ProductionGenre.getFromID(productionsResult.getInt(6));
        ProductionType type = ProductionType.getFromID(productionsResult.getInt(7));

        //Get the productions producer
        int producerID = productionsResult.getInt(8);
        Producer producer = getProducerFromID(producerID);

        Production p = new Production(
                productionsResult.getInt(1),
                productionsResult.getString(2),
                productionsResult.getString(3),
                productionsResult.getString(4),
                productionsResult.getInt(5),
                genre,
                type,
                producer,
                roleMap);
        return p;
    }

    //A helper method to get a producer from the producerID
    //It's probably not really supposed to be in this class...
    private Producer getProducerFromID(int producerID) throws SQLException {
        PreparedStatement getProducer = connection.prepareStatement("" +
                "SELECT producer.id, users.username, users.user_password " +
                "FROM producer, users " +
                "WHERE users.id = producer.id " +
                "AND producer.id = ?");
        getProducer.setInt(1, producerID);
        ResultSet producerResult = getProducer.executeQuery();
        producerResult.next();
        Producer producer = new Producer(
                producerResult.getInt(1),
                producerResult.getString(2),
                producerResult.getString(3)
        );
        return producer;
    }

    //This Approves changes to a production. This means that it moves the productions
    //information from the approval tables to the normal tables and deletes the data
    //from the approval tables
    public void approveChangesToProduction(IProduction production) {

        //production is always persistence production. Therefore instant typecast.
        //is used for id. Pull information out of approved table, put it into table, then delete
        Production prod = (Production) production;
        int prodID = prod.getID();
        try{
            //if id exists in table, overwrite
            //if not, create new

            PreparedStatement prodStatement = connection.prepareStatement("SELECT * FROM production WHERE id = ?");
            prodStatement.setInt(1, prodID);
            ResultSet res = prodStatement.executeQuery();

            PreparedStatement approvalDeleteStatement = null;
            //if its id exists in production table
            if(res.next()){
                //overwrite
                //delete
                PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM production WHERE id = ?");
                deleteStatement.setInt(1, prodID);
                deleteStatement.execute();

            }

            //insert new
            PreparedStatement newStatement = connection.prepareStatement("INSERT INTO production " +
                    "(own_production_id, production_name , year , genre_id , category_id , producer_id , description, id) " +
                    "VALUES (?, ?, ? , ? , ? , ? , ? , ?)");
            newStatement.setString(1,prod.getProductionID());
            newStatement.setString(2,prod.getName());
            newStatement.setInt(3,prod.getYear());
            newStatement.setInt(4,prod.getGenre().getId());
            newStatement.setInt(5,prod.getType().getId());
            newStatement.setInt(6,((Producer)prod.getProducer()).getId());
            newStatement.setString(7,prod.getDescription());
            newStatement.setInt(8,prodID);
            newStatement.execute();

            for (IRightsholder rightsholder: prod.getRightsholders().keySet()){
                RightsHolderHandler.getInstance().approveChangesToRightsholder(rightsholder);
            }

            //Insert into Appears_in
            PreparedStatement moveAppearsInStatement = connection.prepareStatement("" +
                    "INSERT INTO appears_in (id, production_id, rightsholder_id) " +
                    "SELECT id, production_id, rightsholder_id " +
                    "FROM appears_in_approval " +
                    "WHERE production_id=?");
            moveAppearsInStatement.setInt(1, prodID);
            moveAppearsInStatement.execute();

            //Insert into role
            PreparedStatement moveRoleStatement = connection.prepareStatement("" +
                    "INSERT INTO role (id, appears_in_id, title_id)" +
                    "SELECT role_approval.id, role_approval.appears_in_id, role_approval.title_id " +
                    "FROM role_approval " +
                    "INNER JOIN appears_in_approval " +
                    "ON role_approval.appears_in_id = appears_in_approval.id " +
                    "WHERE appears_in_approval.production_id=?");
            moveRoleStatement.setInt(1, prodID);
            moveRoleStatement.execute();

            //Insert into rolename

            PreparedStatement moveRolenameStatement = connection.prepareStatement("" +
                    "INSERT INTO rolename (role_id, rolename)" +
                    "SELECT rolename_approval.role_id, rolename_approval.rolename " +
                    "FROM rolename_approval " +
                    "INNER JOIN role " +
                    "ON rolename_approval.role_id = role.id " +
                    "INNER JOIN appears_in_approval " +
                    "ON role.appears_in_id = appears_in_approval.id " +
                    "WHERE appears_in_approval.production_id = ?");
            moveRolenameStatement.setInt(1, prodID);
            moveRolenameStatement.execute();

            //Delete from approval tables
            PreparedStatement deleteFromAppearsInApprovalStatement = connection.prepareStatement("" +
                    "DELETE FROM appears_in_approval " +
                    "WHERE EXISTS(" +
                    "SELECT * FROM appears_in " +
                    "WHERE appears_in.id=appears_in_approval.id)");
            deleteFromAppearsInApprovalStatement.execute();

            PreparedStatement deleteFromRoleApprovalStatement = connection.prepareStatement("" +
                    "DELETE FROM role_approval " +
                    "WHERE EXISTS(" +
                    "SELECT * FROM role " +
                    "WHERE role.id=role_approval.id)");
            deleteFromRoleApprovalStatement.execute();

            PreparedStatement deleteFromRolenameApprovalStatement = connection.prepareStatement("" +
                    "DELETE FROM rolename_approval " +
                    "WHERE EXISTS(" +
                    "SELECT * FROM rolename " +
                    "WHERE rolename.role_id=rolename_approval.role_id)");
            deleteFromRolenameApprovalStatement.execute();

            //delete the production from approval table
            approvalDeleteStatement = connection.prepareStatement("DELETE FROM production_approval WHERE id = ?");
            approvalDeleteStatement.setInt(1, prodID);
            approvalDeleteStatement.execute();


        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //This return all productions WITH CHANGES WAITING FOR APPROVAL
    //If the user argument is a producer, it will only return the
    //productions owned by that producer
    public List<IProduction> getProductionChanged(IUser user) {
        //If the user is a Systemadministrator, call getProductionsChanged for all producers
        //If the user is a producer, call getProductionsChanged for that producer
        if (user instanceof IAdministrator) {
            List<IProduction> productions = new ArrayList<>();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT producer.id, users.username, users.user_password FROM producer, users WHERE users.id = producer.id");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    productions.addAll(getProductionsChanged(new User(resultSet.getInt("id"),resultSet.getString("username"),resultSet.getString("user_password"))));
                }
                return productions;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            return new ArrayList<>(getProductionsChanged(user));
        }
        return null;
    }

    //A helper method to the above method
    private List<Production> getProductionsChanged(IUser user) {
        //get productions for a producer
        List<Production> productions = new ArrayList<>();
        try {
            PreparedStatement productionsStatement = connection.prepareStatement("" +
                    "SELECT production_approval.id, own_production_id, production_name, description, year, genre_id, category_id, producer_id" +
                    " FROM production_approval, producer, users WHERE users.username = ? AND producer.id = production_approval.producer_id AND users.id = producer.id");
            productionsStatement.setString(1, user.getUsername());
            ResultSet proResult = productionsStatement.executeQuery();
            while (proResult.next()) {
                if (!(proResult.getString(3) == null)) {
                    Production p = getProductionFromResultsetApproval(proResult);
                    productions.add(p);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            PreparedStatement productionsStatement = connection.prepareStatement("" +
                    "SELECT production.id, own_production_id, production_name, description, year, genre_id, category_id, producer_id" +
                    " FROM production, producer, users WHERE users.username = ? AND producer.id = production.producer_id AND users.id = producer.id");
            productionsStatement.setString(1, user.getUsername());
            ResultSet proResult = productionsStatement.executeQuery();
            while (proResult.next()) {
                if (proResult.getString(3) != null) {
                    boolean contains = false;
                    Production p = getProductionFromResultset(proResult);
                    for (Production production : productions) {
                        if (production.getID() == p.getID()) {
                            contains = true;
                        }
                    }
                    if (!contains) {
                        productions.add(p);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productions;
    }

    //A helper method to get a production from a resultset IF THE PRODUCTION
    //IS IN THE APPROVAL TABLES
    private Production getProductionFromResultsetApproval(ResultSet productionsResult) throws SQLException {
        PreparedStatement RightsholdersStatement = connection.prepareStatement("" +
                "SELECT DISTINCT rightsholder_id " +
                "FROM appears_in_approval " +
                "WHERE production_id = ?");
        RightsholdersStatement.setInt(1, productionsResult.getInt(1));
        ResultSet rightsholderIDs = RightsholdersStatement.executeQuery();
        Map<Integer, List<String>> roleMap = new HashMap<>();
        while (rightsholderIDs.next()) {
            //for each rightsholder, get all the roles that rightsholder has in the production
            int id = rightsholderIDs.getInt(1);
            PreparedStatement rolesStatement = connection.prepareStatement("" +
                    "SELECT title, rolename_approval.rolename FROM " +
                    "appears_in_approval LEFT JOIN role_approval ON appears_in_approval.id = role_approval.appears_in_id " +
                    "LEFT JOIN title ON role_approval.title_id = title.id " +
                    "LEFT JOIN rolename_approval ON role_approval.id = rolename_approval.role_id " +
                    "WHERE appears_in_approval.production_id = ? " +
                    "AND appears_in_approval.rightsholder_id = ?");
            rolesStatement.setInt(1, productionsResult.getInt(1));
            rolesStatement.setInt(2, id);
            ResultSet rolesResult = rolesStatement.executeQuery();
            List<String> rolesList = new ArrayList<>();
            while (rolesResult.next()) {
                String title = rolesResult.getString(1);
                if (title == null) {
                    rolesList.add("MISSING TITLE");
                } else if (title.equalsIgnoreCase("medvirkende")) {
                    rolesList.add(title + ": " + rolesResult.getString(2));
                } else {
                    rolesList.add(title);
                }
            }
            roleMap.put(id, rolesList);
        }
        //Gets the genre and type enum based on the genre and type
        ProductionGenre genre = ProductionGenre.getFromID(productionsResult.getInt(6));
        ProductionType type = ProductionType.getFromID(productionsResult.getInt(7));

        int producerID = productionsResult.getInt(8);
        Producer producer = getProducerFromID(producerID);

        Production p = new Production(
                productionsResult.getInt(1),
                productionsResult.getString(2),
                productionsResult.getString(3),
                productionsResult.getString(4),
                productionsResult.getInt(5),
                genre,
                type,
                producer,
                roleMap);
        return p;
    }

    static ProductionHandler getInstance() {
        return prHandler;
    }
}
