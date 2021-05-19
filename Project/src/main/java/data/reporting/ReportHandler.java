package data.reporting;

import Interfaces.IReporting;
import data.DatabaseConnection;
import data.Production;

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
    public int generateProductionCreditsCount(Production production, String title) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("SELECT COUNT(ap.id) FROM appears_in ap, role r, title t" +
                    " WHERE ap.production_id = ? and t.title = ? and r.title_id = t.id and r.appears_in_id = ap.id");
            statement.setInt(1, production.getProductionID());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
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
