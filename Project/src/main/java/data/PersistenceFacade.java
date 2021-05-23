package data;

import Interfaces.*;
import data.credits.FacadeData;
import data.credits.Production;
import data.logger.LogHandler;
import data.notifications.AdminNotification;
import data.notifications.NotificationHandler;
import data.reporting.ReportHandler;
import data.userHandling.UserFacade;

import java.util.List;
import java.util.Map;

public class PersistenceFacade implements IPersistenceFacade {
    private static final PersistenceFacade PERSISTENCE_FACADE = new PersistenceFacade();
    private final IUserFacade USER_FACADE = UserFacade.getInstance();
    private final IFacadeData FACADE_DATA = FacadeData.getInstance();
    private final INotificationHandler NOTIFICATION_HANDLER = NotificationHandler.getInstance();
    private final IReporting REPORT_HANDLER = ReportHandler.getInstance();

    public static PersistenceFacade getInstance() {
        return PERSISTENCE_FACADE;
    }

    @Override
    public List<IUser> getUsers() {
        return USER_FACADE.getUsers();
    }

    @Override
    public List<IUser> getAllProducers() {
        return USER_FACADE.getAllProducers();
    }

    @Override
    public boolean deleteUser(IUser user) {
        return USER_FACADE.deleteUser(user);
    }

    @Override
    public boolean editUser(IUser user) {
        return USER_FACADE.editUser(user);
    }

    @Override
    public boolean addUser(IUser user) {
        return USER_FACADE.addUser(user);
    }

    @Override
    public IUser getUser(IUser user) {
        return USER_FACADE.getUser(user);
    }

    @Override
    public String getDatabasePassword(IUser user) {
        return USER_FACADE.getDatabasePassword(user);
    }

    @Override
    public List<IRightsholder> getRightsholders() {
        return FACADE_DATA.getRightsholders();

    }

    @Override
    public void saveRightsholder(IRightsholder rightsholder) {
        FACADE_DATA.saveRightsholder(rightsholder);
    }

    @Override
    public void approveChangesToRightsholder(IRightsholder rightsholder) {

    }

    @Override
    public List<IProduction> getProductions() {
        return FACADE_DATA.getProductions();
    }

    @Override
    public IProduction saveProduction(IProduction production) {
        return FACADE_DATA.saveProduction(production);
    }

    @Override
    public void deleteProduction(IProduction production) {
        FACADE_DATA.deleteProduction(production);
    }

    @Override
    public void approveChangesToProduction(IProduction production) {
        FACADE_DATA.approveChangesToProduction(production);
    }

    @Override
    public List<IProduction> getMyProductions(IUser user){
        return FACADE_DATA.getMyProductions(user);
    }

    @Override
    public boolean createProducerNotification(INotification notification) {
       return NOTIFICATION_HANDLER.createProducerNotification(notification);
    }

    @Override
    public boolean createAdminNotification(INotification notification) {
        return NOTIFICATION_HANDLER.createAdminNotification(notification);
    }

    @Override
    public boolean deleteAdminNotification(INotification notification) {
        return NOTIFICATION_HANDLER.deleteAdminNotification(notification);
    }

    @Override
    public boolean deleteProducerNotification(INotification notification) {
        return NOTIFICATION_HANDLER.deleteProducerNotification(notification);
    }

    @Override
    public boolean editAdminNotification(INotification newNotification) {
        return NOTIFICATION_HANDLER.editAdminNotification(newNotification);
    }

    @Override
    public boolean editProducerNotification(INotification newNotification) {
        return NOTIFICATION_HANDLER.editProducerNotification(newNotification);
    }

    @Override
    public List<INotification> getAdminNotifications() {
        return NOTIFICATION_HANDLER.getAdminNotifications();
    }

    @Override
    public List<INotification> getProducerNotifications(IUser user) {
        return NOTIFICATION_HANDLER.getProducerNotifications(user);
    }

    @Override
    public int countUnreadAdminNotifications() {
        return NOTIFICATION_HANDLER.countUnreadAdminNotifications();
    }

    @Override
    public int countUnreadProducerNotifications(IUser user) {
        return NOTIFICATION_HANDLER.countUnreadProducerNotifications(user);
    }

    @Override
    public int getTotalCreditCount() {
        return REPORT_HANDLER.getTotalCreditCount();
    }

    @Override
    public Map<String, Integer> generateProductionCreditsCount(IProduction production) {
        return REPORT_HANDLER.generateProductionCreditsCount(production);
    }

    @Override
    public Map<String, Integer> generateCreditTypeCount() {
        return REPORT_HANDLER.generateCreditTypeCount();
    }

    @Override
    public Map<String, Integer> generate10MostCredited() {
        return REPORT_HANDLER.generate10MostCredited();
    }

    @Override
    public List<String> generateCreditsReport() {
        return ReportHandler.getInstance().generateCreditsReport();
    }

    @Override
    public IProduction getProductionFromID(IProduction production) {
        return FacadeData.getInstance().getProduction(production);
    }

    @Override
    public List<IUser> getUsersBySearch(IUser user) {
        return USER_FACADE.getUsersBySearch(user);
    }

    @Override
    public void logAction(String text, IUser user) {
        LogHandler.getInstance().writeLog(text, user);
    }
}
