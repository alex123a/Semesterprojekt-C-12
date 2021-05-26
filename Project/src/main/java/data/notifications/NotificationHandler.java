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

public class NotificationHandler implements INotificationFacade {

    private static final NotificationHandler handler = new NotificationHandler();
    private final Connection dbConnection = DatabaseConnection.getConnection();

    private NotificationHandler() {

    }

    public static NotificationHandler getInstance() {
        return handler;
    }

    // This method creates a producer notifications
    @Override
    public boolean createProducerNotification(INotification notification) {
        try {
            IProduction production = notification.getProduction();
            PreparedStatement statement = dbConnection.prepareStatement(
                    "INSERT INTO producer_notification(notification_text, viewed, production_id) " +
                    "VALUES (?, ?, ?)");
            statement.setString(1, notification.getText());
            statement.setBoolean(2, notification.getViewed());
            statement.setInt(3, ((Production) production).getID());
            statement.execute();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // This method creates an admin notification
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

    // This method deletes an admin notification
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

    // This method deletes an producer notification
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

    // This method edit an admin notification (Only edits/updates the approval status)
    @Override
    public boolean editAdminNotification(INotification newNotification) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("UPDATE administrator_notification SET approval_status_id = ? WHERE id = ? ");
            statement.setInt(1, newNotification.getApproval());
            statement.setInt(2, ((Notification) newNotification).getID());
            statement.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    // This method edit an producer notification (Only edits/updates the viewed status)
    @Override
    public boolean editProducerNotification(INotification newNotification) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement(
                    "UPDATE producer_notification SET viewed = 'true' WHERE id = ?");
            statement.setInt(1, ((Notification) newNotification).getID());
            statement.execute();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // This methods returns a list with all the admin notifications
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

    // This method returns a list with all the notifications for a specific producer
    @Override
    public List<INotification> getProducerNotifications(IUser user) {
        try {
            List<INotification> list = new ArrayList<>();
            PreparedStatement notificationStatement = dbConnection.prepareStatement("SELECT pn.id, pn.notification_text," +
                    " pn.viewed, pn.production_id" +
                    " FROM producer_notification pn, production p" +
                    " WHERE pn.production_id = p.id and p.producer_id = ?");
            notificationStatement.setInt(1, user.getId());
            ResultSet result = notificationStatement.executeQuery();
            while (result.next()) {
                list.add(new ProducerNotification(result.getInt(1), (IProducer) PersistenceFacade.getInstance().getUser(new Producer(user.getId())),
                        result.getString(2), result.getBoolean(3),
                        FacadeData.getInstance().getProduction(new Production(result.getInt(4)))));
            }

            PreparedStatement notificationStatement_for_approval = dbConnection.prepareStatement("SELECT pn.id, pn.notification_text," +
                    " pn.viewed, pn.production_id" +
                    " FROM producer_notification pn, production_approval pa" +
                    " WHERE pn.production_id = pa.id and pa.producer_id = ?");
            notificationStatement_for_approval.setInt(1, user.getId());
            ResultSet result_for_approval = notificationStatement_for_approval.executeQuery();

            while (result_for_approval.next()) {
                list.add(new ProducerNotification(result_for_approval.getInt(1), (IProducer) PersistenceFacade.getInstance().getUser(new Producer(user.getId())),
                        result_for_approval.getString(2), result_for_approval.getBoolean(3),
                        FacadeData.getInstance().getProduction(new Production(result_for_approval.getInt(4)))));
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    This method counts all admin notifications where approval status is "waiting" and not really if it is read or not.
    Maybe the method name should have been something else
    */
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

    // Counts all the unread notifications for a specific producer. It is unread if the boolean viewed is false.
    @Override
    public int countUnreadProducerNotifications(IUser user) {
        try {
            int unread = 0;
            PreparedStatement statement = dbConnection.prepareStatement("SELECT COUNT(pn.id)" +
                    " FROM producer_notification pn, production p WHERE viewed = 'false' and producer_id = ? and p.id = pn.production_id");
            statement.setInt(1, user.getId());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                unread += result.getInt(1);
            }
            return unread;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
