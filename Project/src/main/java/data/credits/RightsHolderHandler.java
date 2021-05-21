package data.credits;

import Interfaces.IRightsholder;
import data.DatabaseConnection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class RightsHolderHandler {
    private Connection connection = null;

    /**
     * Establish a connection to the Database
     *
     * @return Connection
     */

    // Singleton
    private static final RightsHolderHandler rhHandler = new RightsHolderHandler();

    private RightsHolderHandler() {
        this.connection = DatabaseConnection.getConnection();
    }

    // Return all rightholders
    List<IRightsholder> getRightsholders() {
        List<IRightsholder> rightsholderList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM rightsholder");
            ResultSet rightsholderResult = statement.executeQuery();

            while (rightsholderResult.next()) {

                rightsholderList.add(new Rightsholder(rightsholderResult.getInt(1), rightsholderResult.getString(2), rightsholderResult.getString(3), rightsholderResult.getString(4), getRightsholdersProductions(rightsholderResult)));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rightsholderList;
    }

    private List<Integer> getRightsholdersProductions(ResultSet rightsholderSet) throws SQLException {

        List<Integer> idList = new ArrayList<>();
        PreparedStatement productionStatement = connection.prepareStatement("" +
                "SELECT DISTINCT production_id " +
                "FROM appears_in " +
                "WHERE rightsholder_id = ?");
        productionStatement.setInt(1, rightsholderSet.getInt(1));
        ResultSet productionIDs = productionStatement.executeQuery();

        while (productionIDs.next()) {
            idList.add(productionIDs.getInt(1));
        }

        return idList;
    }

    // Return one rightholder with the use of id
    IRightsholder getRightsholder(int id) {
        Rightsholder r = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM rightsholder WHERE id = ?");
            statement.setInt(1, id);
            ResultSet rightsholderResult = statement.executeQuery();

            while (rightsholderResult.next()) {
                r = new Rightsholder(rightsholderResult.getInt(1), rightsholderResult.getString(2), rightsholderResult.getString(3), rightsholderResult.getString(4), getRightsholdersProductions(rightsholderResult));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }

    // Insert rightsholder or edit rightsholder
    IRightsholder saveRightsholder(IRightsholder rightsholder) {
        PreparedStatement statement;
        try {
            if (rightsholder instanceof Rightsholder) {
                //if Irightsholder is from persistence
                Rightsholder r = (Rightsholder) rightsholder;
                //try to get one rightsholder from shadow table, where id is equals rightsholder
                //meant to check if rightsholder is already waiting for change approvals
                PreparedStatement getRightsholderStatement = connection.prepareStatement("SELECT 1 FROM rightsholder_approval WHERE id = ?");
                getRightsholderStatement.setInt(1, r.getId());
                ResultSet result = getRightsholderStatement.executeQuery();

                //if result has next (if there is anything in it) true is returned
                if (result.next()) {
                    //statement for updating already existing tuple in approve table
                    statement = connection.prepareStatement(
                            "UPDATE rightsholder_approval SET first_name = ?, last_name = ?, rightsholder_description = ? " +
                                    "WHERE id=? " +
                                    "RETURNING id, first_name, last_name, rightsholder_description");

                } else {
                    //statement for inserting new tuple in R approval table
                    statement = connection.prepareStatement(
                            "INSERT INTO rightsholder_approval(first_name, last_name, rightsholder_description, id) " +
                                    "VALUES (?,?,?,?) " +
                                    "RETURNING id, first_name, last_name, rightsholder_description");
                }
                statement.setInt(4, r.getId());
            } else {
                //if IRightsholder is from presentation

                statement = connection.prepareStatement(
                        "INSERT INTO " +
                                "rightsholder_approval(first_name, last_name, rightsholder_description) " +
                                "VALUES (?,?,?)" +
                                "RETURNING id, first_name, last_name, rightsholder_description");

            }
            if (statement != null) {
                statement.setString(1, rightsholder.getFirstName());
                statement.setString(2, rightsholder.getLastName());
                statement.setString(3, rightsholder.getDescription());
                ResultSet rResults = statement.executeQuery();
                rResults.next();
                return new Rightsholder(rResults.getInt(1), rResults.getString(2), rResults.getString(3), rResults.getString(4), getRightsholdersProductions(rResults));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    void approveChangesToRightsholder(IRightsholder rightsholder) {

        if (rightsholder instanceof Rightsholder) {
            Rightsholder r = (Rightsholder) rightsholder;

            try {
                //try to get one rightsholder from db, where id is equals rightsholder
                //meant to check if rightsholder is created or being created for change approvals
                PreparedStatement getRightsholderStatement = connection.prepareStatement("SELECT 1 FROM rightsholder WHERE id = ?");
                getRightsholderStatement.setInt(1, r.getId());
                ResultSet result = getRightsholderStatement.executeQuery();

                PreparedStatement statement;
                //if rightsholder id is present in rightsholder table
                if (result.next()) {
                    System.out.println(1);
                    statement = connection.prepareStatement("UPDATE rightsholder SET first_name = ?, last_name = ?, rightsholder_description = ? WHERE id=?");

                }
                //if rightsholder is new, insert statement
                else {
                    statement = connection.prepareStatement("INSERT INTO rightsholder (first_name, last_name, rightsholder_description, id) VALUES (?,?,?,?)");
                }
                //System.out.println("Id" + r.get)
                statement.setString(1, r.getFirstName());
                statement.setString(2, r.getLastName());
                statement.setString(3, r.getDescription());
                statement.setInt(4, r.getId());
                System.out.println(r.getFirstName() + " " + r.getLastName() + " " + r.getDescription() + " " + r.getId());

                statement.execute();

                    System.out.println(3);
                    PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM rightsholder_approval WHERE id = ?");
                    deleteStatement.setInt(1, r.getId());
                    deleteStatement.execute();



            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalArgumentException("Presentation rightsholder used");

        }


        //check if rightsholder is new, or already in table. update if not new.
        //delete when moved
    }

    static RightsHolderHandler getInstance() {
        return rhHandler;
    }
}
