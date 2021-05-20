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
    private File file = null;

    /**
     * Establish a connection to the Database
     * @return Connection
     */

    // Singleton
    private static final RightsHolderHandler rhHandler = new RightsHolderHandler();

    private RightsHolderHandler() {
        this.connection = DatabaseConnection.getConnection();
    }

    private List<Integer> getRightsholdersProductions(ResultSet rightsholderSet) throws SQLException {

        List<Integer> idList = new ArrayList<>();
        PreparedStatement productionStatement = connection.prepareStatement("" +
                "SELECT DISTINCT production_id " +
                "FROM appears_in " +
                "WHERE rightsholder_id = ?");
        productionStatement.setInt(1, rightsholderSet.getInt(1));
        ResultSet productionIDs = productionStatement.executeQuery();

        while(productionIDs.next()){
            idList.add( productionIDs.getInt(1) );
        }

        return idList;
    }

    // Return all rightholders
    List<IRightsholder> getRightsholders() {
        List<IRightsholder> rightsholderList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM rightsholder");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                //TODO for each row in the resultset, make a rightsholder
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rightsholderList;
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


        }catch(SQLException e){
            e.printStackTrace();
        }
        return r;
    }

    // Insert rightsholder or edit rightsholder
    void saveRightsholder(IRightsholder rightsholder) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    static RightsHolderHandler getInstance() {
        return rhHandler;
    }
}
