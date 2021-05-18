package Interfaces;

public interface INotificationHandler {
    boolean createProducerNotification(int userId, INotification notification);

    boolean createAdminNotification(INotification notification);

    boolean deleteNotification(int notificationId);

    boolean editNotification(INotification newNotification);

}
