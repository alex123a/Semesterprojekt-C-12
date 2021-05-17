package Interfaces;

public interface INotificationHandler {
    boolean createProducerNotification(IUser user, INotification notification);

    boolean createAdminNotification(INotification notification);

    boolean deleteAdminNotification(INotification notification);

    boolean deleteProducerNotification(INotification notification);

    boolean editNotification(INotification newNotification);

}
