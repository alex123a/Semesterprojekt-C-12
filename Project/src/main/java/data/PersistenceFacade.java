package data;

import Interfaces.*;
import data.credits.FacadeData;
import data.notifications.AdminNotification;
import data.notifications.NotificationHandler;
import data.reporting.ReportHandler;
import data.userHandling.UserFacade;

import java.util.List;
import java.util.Map;

public class PersistenceFacade implements IPersistenceFacade {
    private static final PersistenceFacade PERSISTENCE_FACADE = new PersistenceFacade();

    public static PersistenceFacade getInstance() {
        return PERSISTENCE_FACADE;
    }

    @Override
    public List<IUser> getUsers() {
        return UserFacade.getInstance().getUsers();
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

    @Override
    public List<IRightsholder> getRightsholders() {
        return FacadeData.getInstance().getRightsholders();

    }

    @Override
    public void saveRightsholder(IRightsholder rightsholder) {
        FacadeData.getInstance().saveRightsholder(rightsholder);
    }

    @Override
    public void approveChangesToRightsholder(IRightsholder rightsholder) {

    }

    @Override
    public List<IProduction> getProductions() {
        return FacadeData.getInstance().getProductions();
    }

    @Override
    public IProduction saveProduction(IProduction production) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteProduction(IProduction production) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void approveChangesToProduction(IProduction production) {

    }

    @Override
    public boolean createProducerNotification(INotification notification) {
       return NotificationHandler.getInstance().createProducerNotification(notification);
    }

    @Override
    public boolean createAdminNotification(INotification notification, IProduction production) {
        return NotificationHandler.getInstance().createAdminNotification(notification, production);
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
    public int countUnreadAdminNotifications() {
        return NotificationHandler.getInstance().countUnreadAdminNotifications();
    }

    @Override
    public int countUnreadProducerNotifications(IUser user) {
        return NotificationHandler.getInstance().countUnreadProducerNotifications(user);
    }

    @Override
    public int getTotalCreditCount() {
        return ReportHandler.getInstance().getTotalCreditCount();
    }

    @Override
    public int generateProductionCreditsCount(IProduction production, String title) {
        return ReportHandler.getInstance().generateProductionCreditsCount(production, title);
    }

    @Override
    public int generateCreditTypeCount(String type) {
        return ReportHandler.getInstance().generateCreditTypeCount(type);
    }

    @Override
    public Map<Integer, Integer> generate10MostCredited() {
        return ReportHandler.getInstance().generate10MostCredited();
    }

    @Override
    public void generateCreditsReport() {
        throw new UnsupportedOperationException();
    }

    @Override
    public IProduction getProductionFromNotification(INotification notification) {
        return FacadeData.getInstance().getProduction(((AdminNotification)notification).getDb_id_production());
    }
}
