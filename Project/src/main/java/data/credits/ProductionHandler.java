package data.credits;

import Interfaces.IProduction;
import Interfaces.IRightsholder;
import data.DatabaseConnection;
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
        //TODO insert rightsholders, appears_in and so on
        //TODO insert Producer id
        //TODO All the statement.set() calls could be refactored

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
                            "SET own_production_id=?, production_name=?, year=?, genre_id=?, category_id=?" +
                            "WHERE id = ?" +
                            "RETURNING id, own_production_id, production_name, description, year, genre_id, category_id;");
                    insertStatement.setString(1, p.getProductionID());
                    insertStatement.setString(2, p.getName());
                    insertStatement.setInt(3, p.getYear());
                    insertStatement.setInt(4, p.getGenre().getId());
                    insertStatement.setInt(5, p.getType().getId());
                    insertStatement.setInt(6, p.getID());
                } else {

                    //Run this if there's no changes for this production in the approval table
                    insertStatement = connection.prepareStatement("" +
                            "INSERT INTO production_approval (id, own_production_id, production_name, year, genre_id, category_id, description) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                            "RETURNING id, own_production_id, production_name, description, year, genre_id, category_id;");
                    insertStatement.setInt(1, p.getID());
                    insertStatement.setString(2, p.getProductionID());
                    insertStatement.setString(3, p.getName());
                    insertStatement.setInt(4, p.getYear());
                    insertStatement.setInt(5, p.getGenre().getId());
                    insertStatement.setInt(6, p.getType().getId());
                    insertStatement.setString(7, p.getDescription());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {

            //Run this if the production is a new production
            try {

                //Run this
                insertStatement = connection.prepareStatement("" +
                        "INSERT INTO production_approval (own_production_id, production_name, year, genre_id, category_id, description) " +
                        "VALUES (?, ?, ?, ?, ?, ?) " +
                        "RETURNING id, own_production_id, production_name, description, year, genre_id, category_id;");
                insertStatement.setString(1, production.getProductionID());
                insertStatement.setString(2, production.getName());
                insertStatement.setInt(3, production.getYear());
                insertStatement.setInt(4,production.getGenre().getId());
                insertStatement.setInt(5, production.getType().getId());
                insertStatement.setString(6, production.getDescription());

                Map<IRightsholder, List<String>> rightsholders = production.getRightsholders();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }



        IProduction toReturn = null;

        try {
            ResultSet resultProduction = insertStatement.executeQuery();
            resultProduction.next();
            toReturn = getProductionFromResultset(resultProduction);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Map<IRightsholder, List<String>> rightsholders = production.getRightsholders();
        for (IRightsholder rightsholder: rightsholders.keySet()) {
            //This line saves the rightsholder and gets the rightsholder with the ID back
            rightsholder = RightsHolderHandler.getInstance().saveRightsholder(rightsholder);
            //Insert a new line in appears_in_approval
            try {
                PreparedStatement appears_in_statement = connection.prepareStatement("" +
                        "INSERT INTO appears_in_approval (production_id, rightsholder_id) " +
                        "VALUES (?, ?);");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

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



    static ProductionHandler getInstance() {
        return prHandler;
    }
}
