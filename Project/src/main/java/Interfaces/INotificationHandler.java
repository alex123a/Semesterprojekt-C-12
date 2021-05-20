package Interfaces;

import java.util.List;

public interface INotificationHandler {
    boolean createProducerNotification(IUser user, INotification notification);

    boolean createAdminNotification(INotification notification, IProduction production);

    boolean deleteAdminNotification(INotification notification);

    boolean deleteProducerNotification(INotification notification);

    boolean editAdminNotification(INotification newNotification);

    boolean editProducerNotification(INotification newNotification);

    List<INotification> getAdminNotifications();

    List<INotification> getProducerNotifications(IUser user);

}
