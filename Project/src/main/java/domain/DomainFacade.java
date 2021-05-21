package domain;

import Interfaces.*;
import data.PersistenceFacade;
import domain.CreditsManagement.CreditsSystem;
import domain.authentication.AuthenticationHandler;
import domain.notification.AdminNotification;
import domain.searchEngine.SearchEngineHandler;
import domain.session.CurrentSession;
import enumerations.ProductionGenre;
import enumerations.ProductionSorting;
import enumerations.ProductionType;
import enumerations.RightholderSorting;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;

public class DomainFacade implements IDomainFacade {
    private static final DomainFacade DOMAIN = new DomainFacade();

    private DomainFacade() {

    }

    public static DomainFacade getInstance() {
        return DOMAIN;
    }


    @Override
    public boolean login(IUser user) {
        return AuthenticationHandler.getLoginInstance().login(user);
    }

    public void addCredit(IProduction production, IRightsholder rightsholder, List<String> roles) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void removeCredit(IProduction production, IRightsholder rightsholder) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setProductionID(IProduction production, String productionID) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void addProduction(IProduction production) {
        IProduction returnedProduction = PersistenceFacade.getInstance().saveProduction(production);
        //IProduction returnedProduction = CreditsSystem.getInstance().
        String notificationMSG = "Produktionen med produktions ID'et  "
                + returnedProduction.getProductionID() + " har ændringer";
        PersistenceFacade.getInstance().createAdminNotification(new AdminNotification(notificationMSG, 0), returnedProduction);
    }

    @Override
    public void deleteProduction(IProduction production) {
        CreditsSystem.getInstance().deleteProduction(production);
    }

    @Override
    public void saveProduction(IProduction production) {
        CreditsSystem.getInstance().saveProduction(production);
    }

    public void saveChanges() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void cancelChanges() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setName(IProduction production, String name) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setRoles(IProduction production, Map<IRightsholder, List<String>> roles) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean editCredit(IRightsholder credit) {
        return false;
    }

    @Override
    public List<?> findMatch(List<ISearchable> list, String target) {
        return SearchEngineHandler.getInstance().findMatch(list, target);
    }

    @Override
    public List<IRightsholder> sortPersonBy(List<IRightsholder> list, RightholderSorting type) {
        return SearchEngineHandler.getInstance().sortPersonBy(list, type);
    }

    @Override
    public List<IProduction> sortProductionBy(List<IProduction> list, ProductionSorting target) {
        return SearchEngineHandler.getInstance().sortProductionBy(list, target);
    }

    @Override
    public List<IProduction> filterProduction(List<IProduction> list, int[] yearInterval, ProductionGenre genre, ProductionType type) {
        return SearchEngineHandler.getInstance().filterProduction(list, yearInterval, genre, type);
    }

    @Override
    public List<IProduction> getProductions() {
        return CreditsSystem.getInstance().getProductions();
    }

    @Override
    public boolean validateUser(IUser user) {
        return AuthenticationHandler.getUserInstance().validateUser(user);
    }


    @Override
    public List<IUser> getUsers() {
        return PersistenceFacade.getInstance().getUsers();
    }

    @Override
    public boolean deleteUser(IUser user) {
        return PersistenceFacade.getInstance().deleteUser(user);
    }

    @Override
    public boolean editUser(IUser user) {
        System.out.println("6");
        return PersistenceFacade.getInstance().editUser(user);
    }

    @Override
    public IUser getUser(IUser user) {
        return PersistenceFacade.getInstance().getUser(user);
    }

    @Override
    public boolean addUser(IUser user) {
        if (PersistenceFacade.getInstance().getUser(user) == null) {
            return PersistenceFacade.getInstance().addUser(user);
        }
        return false;
    }

    @Override
    public String getDatabasePassword(IUser user) {
        return PersistenceFacade.getInstance().getDatabasePassword(user);
    }

    @Override
    public IUser getCurrentUser() {
        return CurrentSession.getInstance().getCurrentUser();
    }

    @Override
    public void setCurrentUser(IUser user) {
        CurrentSession.getInstance().setCurrentUser(user);
    }

    @Override
    public boolean createProducerNotification(IUser user, INotification notification) {
        return PersistenceFacade.getInstance().createProducerNotification(user, notification);
    }

    @Override
    public boolean createAdminNotification(INotification notification, IProduction production) {
        return PersistenceFacade.getInstance().createAdminNotification(notification, production);
    }

    @Override
    public boolean deleteAdminNotification(INotification notification) {
        return PersistenceFacade.getInstance().deleteAdminNotification(notification);
    }

    @Override
    public boolean deleteProducerNotification(INotification notification) {
        return PersistenceFacade.getInstance().deleteProducerNotification(notification);
    }

    @Override
    public boolean editAdminNotification(INotification newNotification) {
        return PersistenceFacade.getInstance().editAdminNotification(newNotification);
    }

    @Override
    public boolean editProducerNotification(INotification newNotification) {
        return PersistenceFacade.getInstance().editProducerNotification(newNotification);
    }

    @Override
    public List<INotification> getAdminNotifications() {
        return PersistenceFacade.getInstance().getAdminNotifications();
    }

    @Override
    public List<INotification> getProducerNotifications(IUser user) {
        return PersistenceFacade.getInstance().getProducerNotifications(user);
    }

    @Override
    public int countUnreadAdminNotifications() {
        return PersistenceFacade.getInstance().countUnreadAdminNotifications();
    }

    @Override
    public int countUnreadProducerNotifications(IUser user) {
        return PersistenceFacade.getInstance().countUnreadProducerNotifications(user);
    }

    public String generateStrongPasswordHash(String password) {
        try {
            return AuthenticationHandler.getInstance().generateStrongPasswordHash(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
}
