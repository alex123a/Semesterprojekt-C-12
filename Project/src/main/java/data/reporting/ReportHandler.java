package data.reporting;

import Interfaces.IProduction;
import Interfaces.IReporting;
import data.DatabaseConnection;
import data.credits.Production;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    public int generateProductionCreditsCount(IProduction production, String title) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("SELECT COUNT(ap.id) FROM appears_in ap, role r, title t" +
                    " WHERE ap.production_id = ? and t.title = ? and r.title_id = t.id and r.appears_in_id = ap.id");
            statement.setInt(1, ((Production) production).getID());
            statement.setString(2, title);
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
    public int generateCreditTypeCount(String type) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("SELECT COUNT(r.id) FROM role r, title t" +
                    " WHERE t.title = ? and t.id = r.title_id");
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
    public Map<Integer, Integer> generate10MostCredited() {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("SELECT ap.rightsholder_id," +
                    " COUNT(ap.rightsholder_id) AS number_of_times" +
                    " FROM appears_in ap, role r" +
                    " WHERE  r.appears_in_id = ap.id" +
                    " GROUP BY ap.rightsholder_id" +
                    " ORDER BY number_of_times DESC" +
                    " LIMIT 10");
            ResultSet result = statement.executeQuery();
            Map<Integer, Integer> map = new HashMap<>();
            while (result.next()) {
                map.put(result.getInt(1), result.getInt(2));
            }
            return map;
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return null;
    }

    @Override
    public void generateCreditsReport() {

    }
}
