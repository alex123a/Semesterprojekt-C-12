package data.notifications;

import Interfaces.*;
import data.DatabaseConnection;
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
    public boolean createProducerNotification(IUser user, INotification notification) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement(
                    "INSERT INTO producer_notification(producer_id, notification_text, viewed, production_id) " +
                    "VALUES (?, ?, ?, ?)");
            statement.setInt(1, user.getId());
            statement.setString(2, notification.getText());
            statement.setBoolean(3, notification.getViewed());
            statement.setInt(4, ((ProducerNotification) notification).getDb_id_production());
            statement.execute();
            return true;
        } catch(SQLException e) {
            return false;
        }
    }

    @Override
    public boolean createAdminNotification(INotification notification, IProduction production) {
        try {
            notification.setProducerID(((Production) production).getID());
            PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO administrator_notification(notification_text, production_id, approval_status_id) VALUES (?, ?, ?)");
            statement.setString(1, notification.getText());
            statement.setInt(2, ((AdminNotification) notification).getDb_id_production());
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
            PreparedStatement statement = dbConnection.prepareStatement("UPDATE administrator_notification SET notification_text = ?, production_id = ?, approval_status_id = ? WHERE id = ? ");
            statement.setString(1, newNotification.getText());
            statement.setInt(2, ((AdminNotification) newNotification).getDb_id_production());
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
            PreparedStatement statement = dbConnection.prepareStatement("UPDATE producer_notification SET producer_id = ?, notification_text = ?, viewed = ?, production_id = ? WHERE id = ?");
            statement.setInt(1, ((Notification) newNotification).getID());
            statement.setInt(2, newNotification.getProducerID());
            statement.setString(3, newNotification.getText());
            statement.setBoolean(4, newNotification.getViewed());
            statement.setInt(5, ((ProducerNotification) newNotification).getDb_id_production());
            statement.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<INotification> getAdminNotifications() {
        try {
            List<INotification> list = new ArrayList<>();
            PreparedStatement statement = dbConnection.prepareStatement("SELECT an.id, an.notification_text," +
                    " p.own_production_id, p.production_name, an.approval_status_id, an.production_id" +
                    " FROM administrator_notification AS an" +
                    ", production AS p WHERE an.production_id = p.id");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                list.add(new AdminNotification(result.getInt(1), result.getString(2),
                        result.getString(3), result.getString(4),
                        result.getInt(5), result.getInt(6)));
            }
            return list;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<INotification> getProducerNotifications(IUser user) {
        try {
            List<INotification> list = new ArrayList<>();
            PreparedStatement statement = dbConnection.prepareStatement("SELECT pn.id, p.own_production_id," +
                    " pn.notification_text, pn.viewed, p.production_name, pn.production_id" +
                    " FROM producer_notification pn, production p" +
                    " WHERE pn.producer_id = ?");
            statement.setInt(1, user.getId());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                list.add(new ProducerNotification(result.getInt(1), result.getString(2),
                        result.getString(3), result.getBoolean(4), ((Producer) user).getId(),
                        result.getString(5), result.getInt(6)));
            }
            return list;
        } catch (SQLException e) {
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
