package data.credits;

import Interfaces.IProduction;
import Interfaces.IRightsholder;
import data.DatabaseConnection;
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
    // TODO: This hasn't been tested. Y'all owe me $100 if this works first try
    List<IProduction> getProductions() {
        List<IProduction> productionList = new ArrayList<>();
        try {
            //Statement to get all productions and their attributes
            PreparedStatement productionsStatement = connection.prepareStatement("" +
                    "SELECT (P.id, P.own_production_id, P.name, P.year, G.name, T.type) " +
                    "FROM production as P, genre as G, type as T " +
                    "WHERE G.id = P.genre_id AND T.id = P.type_id");
            ResultSet productionsResult = productionsStatement.executeQuery();

            //For each production get the rightsholders and their roles
            while (productionsResult.next()) {
                //Statement to get all rightsholders on each production
                PreparedStatement RightsholdersStatement = connection.prepareStatement("" +
                        "SELECT UNIQUE rightsholder_id " +
                        "FROM appears_in " +
                        "WHERE production_id = ?");
                RightsholdersStatement.setInt(1, productionsResult.getInt(1));
                ResultSet rightsholderIDs = RightsholdersStatement.executeQuery();
                Map<Integer, List<String>> roleMap = new HashMap<>();
                while (rightsholderIDs.next()) {
                    //for each rightsholder, get all the roles that rightsholder has in the production
                    int id = rightsholderIDs.getInt(1);
                    PreparedStatement rolesStatement = connection.prepareStatement("" +
                            "SELECT title, rolename.name FROM " +
                            "appears_in LEFT JOIN role ON appears_in.id = role.appears_in_id" +
                            "LEFT JOIN title ON role.title_id = title.id " +
                            "LEFT JOIN rolename ON role.id = rolename.role_id" +
                            "WHERE appears_in.production_id = ?" +
                            "AND appears_in.rightsholder_id = ?");
                    rolesStatement.setInt(1, productionsResult.getInt(1));
                    rolesStatement.setInt(2, id);
                    ResultSet rolesResult = rolesStatement.executeQuery();
                    List<String> rolesList = new ArrayList<>();
                    while (rolesResult.next()) {
                        String title = rolesResult.getString(1);
                        if (title.equalsIgnoreCase("medvirkende")){
                            rolesList.add(title+": "+rolesResult.getString(2));
                        }
                    }
                    roleMap.put(id, rolesList);
                }

                Production p = new Production(productionsResult.getInt(1), productionsResult.getString(2), productionsResult.getString(3), roleMap);
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
        throw new UnsupportedOperationException("not implemented yet");
    }

    void saveProduction(IProduction production) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    boolean deleteProduction(IProduction production) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    static ProductionHandler getInstance() {
        return prHandler;
    }
}
