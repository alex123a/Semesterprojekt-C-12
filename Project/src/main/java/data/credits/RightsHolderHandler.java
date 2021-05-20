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
        throw new UnsupportedOperationException("not implemented yet");
    }

    // Insert rightsholder or edit rightsholder
    void saveRightsholder(IRightsholder rightsholder) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    static RightsHolderHandler getInstance() {
        return rhHandler;
    }
}
