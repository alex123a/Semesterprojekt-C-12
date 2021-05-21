package Interfaces;

import java.util.List;

public interface INotificationHandler {
    boolean createProducerNotification(INotification notification);

    boolean createAdminNotification(INotification notification);

    boolean deleteAdminNotification(INotification notification);

    boolean deleteProducerNotification(INotification notification);

    boolean editAdminNotification(INotification newNotification);

    boolean editProducerNotification(INotification newNotification);

    List<INotification> getAdminNotifications();

    List<INotification> getProducerNotifications(IUser user);

    int countUnreadAdminNotifications();

    int countUnreadProducerNotifications(IUser user);

}
