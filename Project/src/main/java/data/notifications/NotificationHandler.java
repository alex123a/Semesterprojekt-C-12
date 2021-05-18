package data.notifications;

import Interfaces.INotification;
import Interfaces.INotificationHandler;
import Interfaces.INotificationProvider;
import Interfaces.IUser;
import data.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationHandler implements INotificationHandler, INotificationProvider {

    private static final NotificationHandler handler = new NotificationHandler();
    private Connection dbConnection = DatabaseConnection.getConnection();

    private NotificationHandler() {

    }

    public static NotificationHandler getInstance() {
        return handler;
    }

    @Override
    public boolean createProducerNotification(IUser user, INotification notification) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO producer_notification(producer_id, text, viewed, production_id) VALUES (?, ?, ?, ?)");
            statement.setInt(1, user.getId());
            statement.setString(2, notification.getText());
            statement.setBoolean(3, notification.getViewed());
            statement.setInt(4, notification.getProductionId());
            statement.execute();
            return true;
        } catch(SQLException e) {
            return false;
        }
    }

    @Override
    public boolean createAdminNotification(INotification notification) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO administrator_notification(text, production_id, approval_status) VALUES (?, ?, ?)");
            statement.setString(1, notification.getText());
            statement.setInt(2, notification.getProductionId());
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
            statement.setInt(1, notification.getID());
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
            statement.setInt(1, notification.getID());
            statement.execute();
            return true;
        } catch(SQLException e) {
            return false;
        }
    }

    @Override
    public boolean editAdminNotification(INotification newNotification) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("UPDATE administrator_notification WHERE id = ? SET text = ?, production_id = ?, approval_status = ?");
            statement.setInt(1, newNotification.getID());
            statement.setString(2, newNotification.getText());
            statement.setInt(3, newNotification.getProductionId());
            statement.setInt(4, newNotification.getApproval());
            statement.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean editProducerNotification(INotification newNotification) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("UPDATE administrator_notification WHERE id = ? SET producer_id = ?, text = ?, viewed = ?, production_id = ?");
            statement.setInt(1, newNotification.getID());
            statement.setInt(2, newNotification.getProducerID());
            statement.setString(3, newNotification.getText());
            statement.setBoolean(4, newNotification.getViewed());
            statement.setInt(5, newNotification.getProductionId());
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
            PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM administrator_notification");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                list.add(new AdminNotifcation(result.getInt(1), result.getString(2), result.getInt(3), result.getInt(4)));
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
            PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM producer_notification");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                list.add(new ProducerNotification(result.getInt(1), result.getInt(2), result.getString(3), result.getBoolean(4), result.getInt(5)));
            }
            return list;
        } catch (SQLException e) {
            return null;
        }
    }
}
