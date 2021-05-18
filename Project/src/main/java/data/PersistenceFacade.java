package data;

import Interfaces.*;
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
    public void insertRightsholder(IRightsholder rightsholder) {
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
    public void insertProduction(IProduction production) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteProduction(IProduction production) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createProducerNotification(IUser user, INotification notification) {
        return false;
    }

    @Override
    public boolean createAdminNotification(INotification notification) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteAdminNotification(INotification notification) {
        return false;
    }

    @Override
    public boolean deleteProducerNotification(INotification notification) {
        return false;
    }

    @Override
    public boolean editAdminNotification(INotification newNotification) {
        return false;
    }

    @Override
    public boolean editProducerNotification(INotification newNotification) {
        return false;
    }

    @Override
    public List<INotification> getAdminNotifications() {
        return null;
    }

    @Override
    public List<INotification> getProducerNotifications(IUser user) {
        return null;
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
