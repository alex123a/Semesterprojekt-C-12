package data.notifications;

import Interfaces.*;
import data.DatabaseConnection;
import data.PersistenceFacade;
import data.credits.FacadeData;
import data.credits.Production;
import data.userHandling.Producer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationHandler implements INotificationHandler, INotificationProvider {

    private static final NotificationHandler handler = new NotificationHandler();
    private final Connection dbConnection = DatabaseConnection.getConnection();

    private NotificationHandler() {

    }

    public static NotificationHandler getInstance() {
        return handler;
    }

    @Override
    public boolean createProducerNotification(INotification notification) {
        try {
            IProduction production = notification.getProduction();
            PreparedStatement statement = dbConnection.prepareStatement(
                    "INSERT INTO producer_notification(producer_id, notification_text, viewed, production_id) " +
                    "VALUES (?, ?, ?, ?)");
            System.out.println(notification.getProducer().getId());
            System.out.println(notification.getText());
            System.out.println(notification.getViewed());
            System.out.println(((Production) production).getID());
            statement.setInt(1, notification.getProducer().getId());
            statement.setString(2, notification.getText());
            statement.setBoolean(3, notification.getViewed());
            statement.setInt(4, ((Production) production).getID());
            statement.execute();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createAdminNotification(INotification notification) {
        try {
            IProduction production = notification.getProduction();
            PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO administrator_notification(notification_text, production_id, approval_status_id) VALUES (?, ?, ?)");
            statement.setString(1, notification.getText());
            statement.setInt(2, ((Production) production).getID());
            statement.setInt(3, notification.getApproval());
            statement.execute();
            return true;
        } catch(SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteAdminNotification(INotification notification) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("DELETE FROM administrator_notification WHERE id = ?");
            statement.setInt(1, ((Notification) notification).getID());
            statement.execute();
            return true;
        } catch(SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteProducerNotification(INotification notification) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("DELETE FROM producer_notification WHERE id = ?");
            statement.setInt(1, ((Notification) notification).getID());
            statement.execute();
            return true;
        } catch(SQLException e) {
            return false;
        }
    }

    @Override
    public boolean editAdminNotification(INotification newNotification) {
        try {
            IProduction production = newNotification.getProduction();
            PreparedStatement statement = dbConnection.prepareStatement("UPDATE administrator_notification SET notification_text = ?, production_id = ?, approval_status_id = ? WHERE id = ? ");
            statement.setString(1, newNotification.getText());
            statement.setInt(2, ((Production) production).getID());
            statement.setInt(3, newNotification.getApproval());
            statement.setInt(4, ((Notification) newNotification).getID());
            statement.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean editProducerNotification(INotification newNotification) {
        try {
            IProduction production = newNotification.getProduction();
            PreparedStatement statement = dbConnection.prepareStatement("UPDATE producer_notification SET producer_id = ?, notification_text = ?, viewed = ?, production_id = ? WHERE id = ?");
            statement.setInt(1, ((Notification) newNotification).getID());
            statement.setInt(2, newNotification.getProducer().getId());
            statement.setString(3, newNotification.getText());
            statement.setBoolean(4, newNotification.getViewed());
            statement.setInt(5, ((Production) production).getID());
            statement.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }



    @Override
    public List<INotification> getAdminNotifications() {
        try {
            //(int id, String productionID, String name, String description, int year, ProductionGenre genre,
            // ProductionType type, Map<Integer, List<String>> rightsholders)
            List<INotification> list = new ArrayList<>();
            PreparedStatement statement = dbConnection.prepareStatement("SELECT an.id, an.notification_text," +
                    "an.production_id, an.approval_status_id" +
                    " FROM administrator_notification AS an");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                list.add(new AdminNotification(result.getInt(1), result.getString(2),
                        PersistenceFacade.getInstance().getProductionFromID(new Production(result.getInt(3))), result.getInt(4)));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<INotification> getProducerNotifications(IUser user) {
        try {
            List<INotification> list = new ArrayList<>();
            PreparedStatement statement = dbConnection.prepareStatement("SELECT pn.id, pn.producer_id, pn.notification_text," +
                    " pn.viewed, pn.production_id" +
                    " FROM producer_notification pn" +
                    " WHERE pn.producer_id = ?");
            statement.setInt(1, user.getId());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                list.add(new ProducerNotification(result.getInt(1), (IProducer) PersistenceFacade.getInstance().getUser(new Producer(result.getInt(2))),
                        result.getString(3), result.getBoolean(4),
                        FacadeData.getInstance().getProduction(new Production(result.getInt(5)))));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int countUnreadAdminNotifications() {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("SELECT COUNT(id)" +
                    " FROM administrator_notification WHERE approval_status_id = 1");
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int countUnreadProducerNotifications(IUser user) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("SELECT COUNT(id)" +
                    " FROM producer_notification WHERE viewed = 'false' and producer_id = ?");
            statement.setInt(1, user.getId());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
