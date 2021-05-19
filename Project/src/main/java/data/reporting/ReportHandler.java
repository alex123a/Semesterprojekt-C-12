package data.reporting;

import Interfaces.IReporting;
import data.DatabaseConnection;
import data.Production;
import enumerations.Roles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportHandler implements IReporting {

    private final static ReportHandler RHANDLER = new ReportHandler();

    private final Connection dbConnection = DatabaseConnection.getConnection();

    public static ReportHandler getInstance() {
        return RHANDLER;
    }

    @Override
    public int getTotalCreditCount() {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("SELECT COUNT(id) FROM role");
            ResultSet result = statement.executeQuery();
            // Since it counter number of id it will only have one row, so no need to use while loop
            if (result.next()) {
                return result.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            return -1;
        }
    }

    @Override
    public void generateProductionCreditsCount(Production production, Roles role) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("SELECT COUNT(id) FROM appears_in, role, title" +
                    " WHERE production_id = ? and title.title = ? and role = ?");
        } catch (SQLException e) {

        }
    }

    @Override
    public void generateCreditTypeCount() {

    }

    @Override
    public void generate10MostCredited() {

    }

    @Override
    public void generateCreditsReport() {

    }
}
