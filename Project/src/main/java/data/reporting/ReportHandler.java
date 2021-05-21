package data.reporting;

import Interfaces.IProduction;
import Interfaces.IReporting;
import data.DatabaseConnection;
import data.credits.Production;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class ReportHandler implements IReporting {

    private final static ReportHandler RHANDLER = new ReportHandler();

    private final Connection dbConnection = DatabaseConnection.getConnection();

    private ReportHandler() {
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
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Map<String, Integer> generateProductionCreditsCount(IProduction production) {
        Map<String, Integer> jsonReady = new HashMap<>();
        try {
            //Selects all the titles existing for the production(id)
            PreparedStatement statement = dbConnection.prepareStatement(
                    "SELECT t.title FROM appears_in AS ap, role AS r, title AS t" +
                            " WHERE ap.production_id = ? and r.title_id = t.id and r.appears_in_id = ap.id");
            statement.setInt(1, ((Production) production).getID());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                //Counts how many works in every title for that specific production
                PreparedStatement countStatement = dbConnection.prepareStatement("SELECT COUNT(ap.id) FROM appears_in AS ap, role AS r, title AS t" +
                        " WHERE ap.production_id = ? and t.title = ? and r.title_id = t.id and r.appears_in_id = ap.id");
                countStatement.setInt(1, ((Production) production).getID());
                countStatement.setString(2, result.getString(1));
                ResultSet resultCount = countStatement.executeQuery();
                while (resultCount.next()) {
                    jsonReady.put(result.getString(1), resultCount.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonReady;
    }

    @Override
    public Map<String, Integer> generateCreditTypeCount() {
        Map<String, Integer> jsonReady = new HashMap<>();
        try {
            PreparedStatement titleQuery = dbConnection.prepareStatement("SELECT title.title FROM title");
            ResultSet resultSet = titleQuery.executeQuery();
            while (resultSet.next()) {
                PreparedStatement statement = dbConnection.prepareStatement("SELECT COUNT(r.id) FROM role AS r, title AS t" +
                        " WHERE t.title = ? AND t.id = r.title_id");
                statement.setString(1, resultSet.getString("title"));
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    jsonReady.put(resultSet.getString("title"), result.getInt(1));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return jsonReady;
    }

    @Override
    public Map<String, Integer> generate10MostCredited() {
        try {
            PreparedStatement gets10MostCredited = dbConnection.prepareStatement("SELECT ap.rightsholder_id," +
                    " COUNT(ap.rightsholder_id) AS number_of_times" +
                    " FROM appears_in ap, role r" +
                    " WHERE  r.appears_in_id = ap.id" +
                    " GROUP BY ap.rightsholder_id" +
                    " ORDER BY number_of_times DESC" +
                    " LIMIT 10");
            ResultSet resultOf10Most = gets10MostCredited.executeQuery();
            Map<String, Integer> map = new HashMap<>();
            while (resultOf10Most.next()) {
                PreparedStatement getNameQuery = dbConnection.prepareStatement("SELECT r.first_name, r.last_name " +
                        "FROM rightsholder AS r " +
                        "WHERE r.id = ?");
                getNameQuery.setInt(1, resultOf10Most.getInt(1));
                ResultSet resultSet = getNameQuery.executeQuery();
                resultSet.next();
                map.put(resultSet.getString(1) + " " + resultSet.getString(2), resultOf10Most.getInt(2));
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> generateCreditsReport() {
        List<String> jsonReady = new ArrayList<>();
        try {
            PreparedStatement productionQuery = dbConnection.prepareStatement("SELECT production.id, production.production_name FROM production");
            ResultSet productionSet = productionQuery.executeQuery();
            while (productionSet.next()) {
                jsonReady.add("New Production");
                jsonReady.add(productionSet.getString(1));
                jsonReady.add(productionSet.getString(2));
                PreparedStatement titleQuery = dbConnection.prepareStatement(
                        "SELECT r.id, first_name, last_name, t.title FROM rightsholder r, appears_in ap, role, title t " +
                                "WHERE role.title_id = t.id AND role.appears_in_id = ap.id AND ap.production_id = ? AND ap.rightsholder_id = r.id\n");
                titleQuery.setInt(1, productionSet.getInt(1));
                ResultSet result = titleQuery.executeQuery();
                while (result.next()) {

                    jsonReady.add(result.getString(1));
                    jsonReady.add(result.getString(2) + " " + result.getString(3));
                    jsonReady.add(result.getString(4));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return jsonReady;
    }

    public static ReportHandler getInstance() {
        return RHANDLER;
    }
}
