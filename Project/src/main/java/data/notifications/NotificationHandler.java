package data.notifications;

import Interfaces.INotification;
import Interfaces.INotificationHandler;
import Interfaces.INotificationProvider;
import Interfaces.IUser;
import data.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    public boolean editNotification(INotification newNotification) {
        return false;
    }

    @Override
    public List<INotification> getNotifications(IUser user) {
        return null;
    }
}
