package data.credits;

import Interfaces.IProduction;
import Interfaces.IRightsholder;
import data.DatabaseConnection;
import data.userHandling.Producer;
import enumerations.ProductionGenre;
import enumerations.ProductionType;
import jdk.jshell.spi.ExecutionControl;

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

    // Reads the whole productionData file and returns all productions in an arraylist;
    List<IProduction> getProductions() {
        List<IProduction> productionList = new ArrayList<>();
        try {
            //Statement to get all productions and their attributes
            PreparedStatement productionsStatement = connection.prepareStatement("" +
                    "SELECT id, own_production_id, production_name, description, year, genre_id, category_id " +
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
    IProduction getProduction(int id) {
        try {
            //Statement to get all productions and their attributes
            PreparedStatement productionsStatement = connection.prepareStatement("" +
                    "SELECT id, own_production_id, production_name, description, year, genre_id, category_id " +
                    "FROM production " +
                    "WHERE id = ?;");
            productionsStatement.setInt(1, id);
            ResultSet productionsResult = productionsStatement.executeQuery();

            productionsResult.next();
            //For each production get the rightsholders and their roles
            Production p = getProductionFromResultset(productionsResult);

            return p;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    IProduction saveProduction(IProduction production){

        //TODO insert ALL appears in and role and so on instead of just the onse that have been changed
        
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
                    //TODO hasn't been tested
                    //Run this if the production is already in the approval table
                    insertStatement = connection.prepareStatement("" +
                            "UPDATE production_approval " +
                            "SET own_production_id=?, production_name=?, year=?, genre_id=?, category_id=?, producer_id=?" +
                            "WHERE id = ?" +
                            "RETURNING id, own_production_id, production_name, description, year, genre_id, category_id, producer_id;");
                    insertStatement.setString(1, p.getProductionID());
                    insertStatement.setString(2, p.getName());
                    insertStatement.setInt(3, p.getYear());
                    insertStatement.setInt(4, p.getGenre().getId());
                    insertStatement.setInt(5, p.getType().getId());
                    insertStatement.setInt(6, p.getID());
                    insertStatement.setInt(7, p.getProducer().getId());
                } else {

                    //TODO hasn't been tested
                    //Run this if there's no changes for this production in the approval table
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
                        "RETURNING id, own_production_id, production_name, description, year, genre_id, category_id;");
                insertStatement.setString(1, production.getProductionID());
                insertStatement.setString(2, production.getName());
                insertStatement.setInt(3, production.getYear());
                insertStatement.setInt(4,production.getGenre().getId());
                insertStatement.setInt(5, production.getType().getId());
                insertStatement.setInt(6, production.getProducer().getId());
                insertStatement.setString(7, production.getDescription());

                //Saves any changes to the rightsholders (First or last name could have been changed
                Map<IRightsholder, List<String>> rightsholders = production.getRightsholders();
                for (IRightsholder rightsholder: rightsholders.keySet()) {
                    RightsHolderHandler.getInstance().saveRightsholder(rightsholder);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }



        Production toReturn = null;

        try {
            ResultSet resultProduction = insertStatement.executeQuery();
            resultProduction.next();
            toReturn = getProductionFromResultset(resultProduction);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        int productionID = toReturn.getID();

        // Following block adds appears_in if they are in the production but not in the DB
        Map<IRightsholder, List<String>> rightsholders = production.getRightsholders();
        for (IRightsholder rightsholder: rightsholders.keySet()) {
            //This line saves the rightsholder and gets the rightsholder with the ID back
            Rightsholder r = (Rightsholder) RightsHolderHandler.getInstance().saveRightsholder(rightsholder);

            try {
                int rightsholderID = r.getId();
                //Checks if the appears_in is already in the table
                PreparedStatement checkExistsStatement = connection.prepareStatement("" +
                        "SELECT * FROM appears_in " +
                        "WHERE rightsholder_id=?" +
                        "AND production_id=?");
                checkExistsStatement.setInt(1, rightsholderID);
                checkExistsStatement.setInt(2, productionID);
                ResultSet existing = checkExistsStatement.executeQuery();
                if (!existing.next()) {// the appears_in is not in the table and thus should be inserted
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

                        PreparedStatement getTitleIdStatement = connection.prepareStatement("" +
                                "SELECT id " +
                                "FROM title " +
                                "WHERE title.title = ?");
                        getTitleIdStatement.setString(1, title);
                        ResultSet titleIdResultSet = getTitleIdStatement.executeQuery();
                        titleIdResultSet.next();

                        PreparedStatement insertRoleStatement = connection.prepareStatement("" +
                                "INSERT INTO role_approval (appears_in_id, title_id) " +
                                "VALUES (?, ?)" +
                                "RETURNING id");

                        insertRoleStatement.setInt(1, appearsinID);
                        insertRoleStatement.setInt(2, titleIdResultSet.getInt(1));
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
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        // the following block removes the appears_in if they are in the DB but not in the production
        // meaning it creates a row in appears_in_approval with the id from appears_in row an null values
        try {
            PreparedStatement getAllAppearsinStatement = connection.prepareStatement("" +
                    "SELECT rightsholder_id, id " +
                    "FROM appears_in " +
                    "WHERE production_id=?");
            getAllAppearsinStatement.setInt(1, productionID);
            ResultSet allAppearsinResult = getAllAppearsinStatement.executeQuery();

            //gets all the ids of the rightsholders in the production
            //so they can be compared to the ids in the database
            List<Integer> rightsholderIDs = new ArrayList<>();
            for (IRightsholder r: rightsholders.keySet()) {
                if (r instanceof Rightsholder) {
                    rightsholderIDs.add(((Rightsholder)r).getId());
                }
            }
            while (allAppearsinResult.next()){
                if (!rightsholderIDs.contains(allAppearsinResult.getInt(1))){
                    PreparedStatement insertToBeDeleted = connection.prepareStatement("" +
                            "INSERT INTO appears_in_approval (id)" +
                            "VALUES (?)");
                    insertToBeDeleted.setInt(1, allAppearsinResult.getInt(2));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    //Inserts a row with id and a lot of null-values, which then waits for approval
    boolean deleteProduction(IProduction production) {
        if (production instanceof Production) {
            Production p = (Production) production;
            int id = p.getID();
            try {
                PreparedStatement statement = connection.prepareStatement("" +
                        "INSERT INTO production_approval (id) VALUES (?)");
                statement.setInt(1, id);
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }
            return true;
        } else {
            throw new IllegalArgumentException("Can't delete an IProduction, that is not a Production");
        }
    }

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
                }else if (title.equalsIgnoreCase("medvirkende")){
                    rolesList.add(title+": "+rolesResult.getString(2));
                }else {
                    rolesList.add(title);
                }
            }
            roleMap.put(id, rolesList);
        }
        //Gets the genre and type enum based on the genre and type
        ProductionGenre genre = ProductionGenre.getFromID(productionsResult.getInt(6));
        ProductionType type = ProductionType.getFromID(productionsResult.getInt(7));

        Production p = new Production(productionsResult.getInt(1), productionsResult.getString(2), productionsResult.getString(3), productionsResult.getString(4), productionsResult.getInt(5), genre, type, roleMap);
        return p;
    }

    public void approveChangesToProduction(IProduction production) {
        //todo finish implementation
        //production is always persistense production. Therefore instant typecast.
        //is used for id. Pull information out of approved table, put it into table, then delete
        Production prod = (Production) production;

        try{
            //if id exists in table, overwrite
            //if not, create new
            //if full null, delete
            PreparedStatement prodStatement = connection.prepareStatement("SELECT 1 FROM production WHERE id = ?");
            prodStatement.setInt(1, prod.getID());
            ResultSet res = prodStatement.executeQuery();

            if(res.next()){

            }

        } catch (SQLException e){
          e.printStackTrace();
        }
    }



    static ProductionHandler getInstance() {
        return prHandler;
    }
}
