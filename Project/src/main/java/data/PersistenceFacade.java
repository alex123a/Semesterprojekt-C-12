package data;

import Interfaces.*;
import data.notifications.NotificationHandler;
import data.userHandling.UserFacade;

import java.util.List;

public class PersistenceFacade implements IPersistenceFacade {
    private static final PersistenceFacade PERSISTENCE_FACADE = new PersistenceFacade();

    @Override
    public List<IUser> getUsers() {
        return UserFacade.getInstance().getUsers();
    }

    @Override
    public boolean makeUserProducer(IUser user) {
        return UserFacade.getInstance().makeUserProducer(user);
    }

    @Override
    public boolean makeUserAdmin(IUser user) {
        return UserFacade.getInstance().makeUserAdmin(user);
    }

    @Override
    public boolean deleteUser(IUser user) {
        return UserFacade.getInstance().deleteUser(user);
    }

    @Override
    public boolean editUser(IUser user) {
        return UserFacade.getInstance().editUser(user);
    }

    @Override
    public boolean addUser(IUser user) {
        return UserFacade.getInstance().addUser(user);
    }

    @Override
    public IUser getUser(IUser user) {
        return UserFacade.getInstance().getUser(user);
    }

    @Override
    public String getDatabasePassword(IUser user) {
        return UserFacade.getInstance().getDatabasePassword(user);
    }

    public static PersistenceFacade getInstance() {
        return PERSISTENCE_FACADE;
    }

    @Override
    public IRightsholder getRightsholder(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRightsholder> getRightsholders() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveRightsholder(IRightsholder rightsholder) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IProduction getProduction(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IProduction> getProductions() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveProduction(IProduction production) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteProduction(IProduction production) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createProducerNotification(IUser user, INotification notification) {
       return NotificationHandler.getInstance().createProducerNotification(user, notification);
    }

    @Override
    public boolean createAdminNotification(INotification notification) {
        return NotificationHandler.getInstance().createAdminNotification(notification);
    }

    @Override
    public boolean deleteAdminNotification(INotification notification) {
        return NotificationHandler.getInstance().deleteAdminNotification(notification);
    }

    @Override
    public boolean deleteProducerNotification(INotification notification) {
        return NotificationHandler.getInstance().deleteProducerNotification(notification);
    }

    @Override
    public boolean editAdminNotification(INotification newNotification) {
        return NotificationHandler.getInstance().editAdminNotification(newNotification);
    }

    @Override
    public boolean editProducerNotification(INotification newNotification) {
        return NotificationHandler.getInstance().editProducerNotification(newNotification);
    }

    @Override
    public List<INotification> getAdminNotifications() {
        return NotificationHandler.getInstance().getAdminNotifications();
    }

    @Override
    public List<INotification> getProducerNotifications(IUser user) {
        return NotificationHandler.getInstance().getProducerNotifications(user);
    }

    @Override
    public int getTotalCreditCount() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void generateProducerCreditsCount() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void generateCreditTypeCount() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void generate10MostCredited() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void generateCreditsReport() {
        throw new UnsupportedOperationException();
    }
}
