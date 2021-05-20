package data.reporting;

import Interfaces.IProduction;
import Interfaces.IReporting;
import data.DatabaseConnection;
import data.credits.Production;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


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
    public Map<String, Integer> generateProductionCreditsCount(IProduction production) {
        Set<String> titleName = new TreeSet<>();
        try {
            PreparedStatement statement = dbConnection.prepareStatement(
                    "SELECT t.title FROM appears_in AS ap, role AS r, title AS t" +
                            " WHERE ap.production_id = ? and r.title_id = t.id and r.appears_in_id = ap.id");
            statement.setInt(1, ((Production) production).getID());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                titleName.add(result.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        Map<String, Integer> jsonReady = new HashMap<>();
        for (String tit : titleName) {
            try {
                PreparedStatement statement = dbConnection.prepareStatement("SELECT COUNT(ap.id) FROM appears_in AS ap, role AS r, title AS t" +
                        " WHERE ap.production_id = ? and t.title = ? and r.title_id = t.id and r.appears_in_id = ap.id");
                statement.setInt(1, ((Production) production).getID());
                statement.setString(2, tit);
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    jsonReady.put(tit, result.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return jsonReady;
    }

    @Override
    public Map<String, Integer> generateCreditTypeCount() {
        List<String> titles = new ArrayList<>();
        try {
            PreparedStatement titleQuery = dbConnection.prepareStatement("SELECT title.title FROM title");
            ResultSet resultSet = titleQuery.executeQuery();
            while (resultSet.next()) {
                titles.add(resultSet.getString("title"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Map<String, Integer> jsonReady = new HashMap<>();
        for (String s : titles) {
            try {
                PreparedStatement statement = dbConnection.prepareStatement("SELECT COUNT(r.id) FROM role AS r, title AS t" +
                        " WHERE t.title = ? AND t.id = r.title_id");
                statement.setString(1, s);
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    jsonReady.put(s, result.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return jsonReady;
            }
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
    public JSONObject generateCreditsReport() {
        JSONObject jsonObject = new JSONObject();
        try {
            PreparedStatement productionQuery = dbConnection.prepareStatement("SELECT production.id, production.production_name FROM production");
            ResultSet productionSet = productionQuery.executeQuery();
            while (productionSet.next()) {
                jsonObject.put("ProgramId",productionSet.getString(1));
                jsonObject.put("ProgramName",productionSet.getString(2));
                PreparedStatement titleQuery = dbConnection.prepareStatement(
                        "SELECT DISTINCT t.title FROM appears_in AS ap, role AS r, title AS t" +
                                " WHERE ap.production_id = ? AND r.title_id = t.id AND r.appears_in_id = ap.id");
                titleQuery.setInt(1, productionSet.getInt(1));
                ResultSet result = titleQuery.executeQuery();
                while (result.next()) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("TitleName",result.getString("title"));
                    JSONArray jsonArray = new JSONArray();
                    PreparedStatement personQuery = dbConnection.prepareStatement("SELECT r.id, first_name, last_name FROM rightsholder r, appears_in ap, role, title t " +
                            "WHERE t.title = ? AND role.title_id = t.id AND role.appears_in_id = ap.id AND ap.production_id = ? AND ap.rightsholder_id = r.id");
                    personQuery.setString(1, result.getString("title"));
                    personQuery.setInt(2, productionSet.getInt(1));
                    ResultSet persons = personQuery.executeQuery();
                    while (persons.next()) {
                        jsonArray.add(persons.getString(1));
                        jsonArray.add(persons.getString(2) + " " + persons.getString(3));
                    }
                    jsonObject1.put("participants",jsonArray);
                    jsonObject.put("Titles",jsonObject1);
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return jsonObject;
    }
}
