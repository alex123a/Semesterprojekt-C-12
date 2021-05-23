package domain;

import Interfaces.*;
import data.PersistenceFacade;
import domain.CreditsManagement.CreditsSystem;
import domain.authentication.AuthenticationFacade;
import domain.notification.AdminNotification;
import domain.notification.ProducerNotification;
import domain.searchEngine.SearchEngineHandler;
import domain.searchEngine.SearchUserHandler;
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
        boolean toReturn = AuthenticationFacade.getInstance().login(user);
        //logAction("User logged in", user);
        return toReturn;
    }

    @Override
    public void deleteProduction(IProduction production) {
        CreditsSystem.getInstance().deleteProduction(production);
        //logAction("Production deleted (waiting for approval): " + production);
    }

    @Override
    public IProduction saveProduction(IProduction production) {
        IProduction returnedProduction = CreditsSystem.getInstance().saveProduction(production);
        if (!validateUser(getCurrentUser())) {
            String notificationMSG = "Produktionen med produktions ID'et  "
                    + returnedProduction.getProductionID() + " har ændringer";
            PersistenceFacade.getInstance().createAdminNotification(new AdminNotification(notificationMSG, returnedProduction, 1));
            //logAction("Save production (Waiting for approval): " + production + " --- notification sent to producer");
        }
        return returnedProduction;
    }

    @Override
    public void approveChangesToProduction(IProduction production) {
        CreditsSystem.getInstance().approveChangesToProduction(production);
        //logAction("Changes to production " + production + " Approved");
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
    public List<IProduction> getMyProductions() {
        return CreditsSystem.getInstance().getMyProductions();
    }

    public List<IRightsholder> getRightsholders() {
        return CreditsSystem.getInstance().getAllRightsholders();
    }

    @Override
    public boolean validateUser(IUser user) {
        return AuthenticationFacade.getInstance().validateUser(user);
    }


    @Override
    public List<IUser> getUsers() {
        return PersistenceFacade.getInstance().getUsers();
    }

    @Override
    public List<IUser> getAllProducers() {
        return PersistenceFacade.getInstance().getAllProducers();
    }

    @Override
    public boolean deleteUser(IUser user) {
        IUser currentUser = getCurrentUser();
        if (validateUser(currentUser) && user != null && !currentUser.getUsername().equals(user.getUsername())) {
            return PersistenceFacade.getInstance().deleteUser(user);
        }
        return false;
    }

    @Override
    public boolean editUser(IUser user) {
        IUser currentUser = getCurrentUser();
        if (validateUser(currentUser) && !user.getUsername().equals("") && !user.getPassword().equals("")) {
            return PersistenceFacade.getInstance().editUser(user);
        }
        return false;
    }

    @Override
    public IUser getUser(IUser user) {
        return PersistenceFacade.getInstance().getUser(user);
    }

    @Override
    public boolean addUser(IUser user) {
        IUser currentUser = getCurrentUser();
        if (validateUser(currentUser) && !user.getUsername().equals("") && !user.getPassword().equals("")) {
            try {
                user.setPassword(AuthenticationFacade.getInstance().generateStrongPasswordHash(user.getPassword()));
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
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
    public boolean createProducerNotification(INotification notification) {
        return PersistenceFacade.getInstance().createProducerNotification(notification);
    }

    @Override
    public boolean createAdminNotification(INotification notification) {
        return PersistenceFacade.getInstance().createAdminNotification(notification);
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
        if (newNotification.getApproval() == 2) {
            String msg = "Produktionen med produktions id " + newNotification.getProduction().getProductionID() +
                    " er blevet godkendt";
            createProducerNotification(new ProducerNotification(newNotification.getProduction(), msg, false, newNotification.getProduction().getProducer()));
        } else if (newNotification.getApproval() == 3) {
            String msg = "Produktionen med produktions id " + newNotification.getProduction().getProductionID() +
                    " er blevet afvist";
            createProducerNotification(new ProducerNotification(newNotification.getProduction(), msg, false, newNotification.getProduction().getProducer()));
        }
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

    @Override
    public String generateStrongPasswordHash(String password) {
        try {
            return AuthenticationFacade.getInstance().generateStrongPasswordHash(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getInfoFromSearch(String search, String resultType) {
        return SearchUserHandler.getInstance().getInfoFromSearch(search, resultType);
    }

    @Override
    public List<IUser> getUsersBySearch(IUser user) {
        return PersistenceFacade.getInstance().getUsersBySearch(user);
    }

    private void logAction(String text) {
        logAction(text, this.getCurrentUser());
    }

    private void logAction(String text, IUser user) {
        PersistenceFacade.getInstance().logAction(text, user);
    }
}
