package Interfaces;

import java.util.List;

public interface INotificationProvider {
    List<INotification> getAdminNotifications();

    List<INotification> getProducerNotifications(IUser user);
}
