package data;

import Interfaces.INotification;
import Interfaces.IProduction;
import Interfaces.IRightsholder;
import Interfaces.IUser;
import data.notifications.AdminNotifcation;
import data.notifications.NotificationHandler;
import data.notifications.ProducerNotification;
import domain.CreditsManagement.CreditsSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        NotificationHandler d = NotificationHandler.getInstance();

        INotification adminNoti = new AdminNotifcation("Sej notification", 1, 1);
        INotification prodNoti = new ProducerNotification(1, "Sej notfification 2", false, 1);
        //IUser user = new Producer()

        d.createProducerNotification(, prodNoti);
        d.createAdminNotification();
    }
}
