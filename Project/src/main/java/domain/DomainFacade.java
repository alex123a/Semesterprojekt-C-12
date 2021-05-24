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
    private final IPersistenceFacade PERSISTENCE_FACADE = PersistenceFacade.getInstance();
    private final ISession CURRENT_SESSION = CurrentSession.getInstance();
    private final CreditsSystem CREDITSYSTEM = CreditsSystem.getInstance();
    private final ISearchCredits SEARCH_CREDITS = SearchEngineHandler.getInstance();
    private final ISearchUser SEARCH_USER = SearchUserHandler.getInstance();
    private final IAuthenticationHandler AUTHENTICATION_FACADE = AuthenticationFacade.getInstance();

    private DomainFacade() {

    }

    public static DomainFacade getInstance() {
        return DOMAIN;
    }


    @Override
    public boolean login(IUser user) {
        boolean toReturn = AUTHENTICATION_FACADE.login(user);
        //logAction("User logged in", user);
        return toReturn;
    }

    @Override
    public void deleteProduction(IProduction production) {
        CREDITSYSTEM.deleteProduction(production);
        //logAction("Production deleted (waiting for approval): " + production);
    }

    @Override
    public IProduction saveProduction(IProduction production) {
        IProduction returnedProduction = CREDITSYSTEM.saveProduction(production);
        if (!validateUser(getCurrentUser())) {
            String notificationMSG = "Produktionen med produktions ID'et  "
                    + returnedProduction.getProductionID() + " har ændringer";
            PERSISTENCE_FACADE.createAdminNotification(new AdminNotification(notificationMSG, returnedProduction, 1));
            //logAction("Save production (Waiting for approval): " + production + " --- notification sent to producer");
        }
        return returnedProduction;
    }

    @Override
    public void approveChangesToProduction(IProduction production) {
        CREDITSYSTEM.approveChangesToProduction(production);
        //logAction("Changes to production " + production + " Approved");
    }

    @Override
    public List<?> findMatch(List<ISearchable> list, String target) {
        return SEARCH_CREDITS.findMatch(list, target);
    }

    @Override
    public List<IRightsholder> sortPersonBy(List<IRightsholder> list, RightholderSorting type) {
        return SEARCH_CREDITS.sortPersonBy(list, type);
    }

    @Override
    public List<IProduction> sortProductionBy(List<IProduction> list, ProductionSorting target) {
        return SEARCH_CREDITS.sortProductionBy(list, target);
    }

    @Override
    public List<IProduction> filterProduction(List<IProduction> list, int[] yearInterval, ProductionGenre genre, ProductionType type) {
        return SEARCH_CREDITS.filterProduction(list, yearInterval, genre, type);
    }

    @Override
    public List<IProduction> getProductions() {
        return CREDITSYSTEM.getProductions();
    }

    @Override
    public List<IProduction> getMyProductions() {
        return CREDITSYSTEM.getMyProductions();
    }

    public List<IRightsholder> getRightsholders() {
        return CREDITSYSTEM.getAllRightsholders();
    }

    @Override
    public boolean validateUser(IUser user) {
        return AUTHENTICATION_FACADE.validateUser(user);
    }


    @Override
    public List<IUser> getUsers() {
        return PERSISTENCE_FACADE.getUsers();
    }

    @Override
    public List<IUser> getAllProducers() {
        return PERSISTENCE_FACADE.getAllProducers();
    }

    @Override
    public boolean deleteUser(IUser user) {
        IUser currentUser = getCurrentUser();
        if (validateUser(currentUser) && user != null && !currentUser.getUsername().equals(user.getUsername())) {
            return PERSISTENCE_FACADE.deleteUser(user);
        }
        return false;
    }

    @Override
    public boolean editUser(IUser user) {
        IUser currentUser = getCurrentUser();
        if (validateUser(currentUser) && !user.getUsername().equals("") && !user.getPassword().equals("")) {
            return PERSISTENCE_FACADE.editUser(user);
        }
        return false;
    }

    @Override
    public IUser getUser(IUser user) {
        return PERSISTENCE_FACADE.getUser(user);
    }

    @Override
    public boolean addUser(IUser user) {
        IUser currentUser = getCurrentUser();
        if (validateUser(currentUser) && !user.getUsername().equals("") && !user.getPassword().equals("")) {
            try {
                user.setPassword(AUTHENTICATION_FACADE.generateStrongPasswordHash(user.getPassword()));
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
            return PERSISTENCE_FACADE.addUser(user);
        }
        return false;
    }

    @Override
    public String getDatabasePassword(IUser user) {
        return PERSISTENCE_FACADE.getDatabasePassword(user);
    }


    @Override
    public IUser getCurrentUser() {
        return CURRENT_SESSION.getCurrentUser();
    }

    @Override
    public void setCurrentUser(IUser user) {
        CURRENT_SESSION.setCurrentUser(user);
    }

    @Override
    public boolean createProducerNotification(INotification notification) {
        return PERSISTENCE_FACADE.createProducerNotification(notification);
    }

    @Override
    public boolean createAdminNotification(INotification notification) {
        return PERSISTENCE_FACADE.createAdminNotification(notification);
    }

    @Override
    public boolean deleteAdminNotification(INotification notification) {
        return PERSISTENCE_FACADE.deleteAdminNotification(notification);
    }

    @Override
    public boolean deleteProducerNotification(INotification notification) {
        return PERSISTENCE_FACADE.deleteProducerNotification(notification);
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
        return PERSISTENCE_FACADE.editAdminNotification(newNotification);
    }

    @Override
    public boolean editProducerNotification(INotification newNotification) {
        return PERSISTENCE_FACADE.editProducerNotification(newNotification);
    }

    @Override
    public List<INotification> getAdminNotifications() {
        return PERSISTENCE_FACADE.getAdminNotifications();
    }

    @Override
    public List<INotification> getProducerNotifications(IUser user) {
        return PERSISTENCE_FACADE.getProducerNotifications(user);
    }

    @Override
    public int countUnreadAdminNotifications() {
        return PERSISTENCE_FACADE.countUnreadAdminNotifications();
    }

    @Override
    public int countUnreadProducerNotifications(IUser user) {
        return PERSISTENCE_FACADE.countUnreadProducerNotifications(user);
    }

    @Override
    public String generateStrongPasswordHash(String password) {
        try {
            return AUTHENTICATION_FACADE.generateStrongPasswordHash(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getInfoFromSearch(String search, String resultType) {
        return SEARCH_USER.getInfoFromSearch(search, resultType);
    }

    @Override
    public List<IUser> getUsersBySearch(IUser user) {
        return PERSISTENCE_FACADE.getUsersBySearch(user);
    }

    private void logAction(String text) {
        logAction(text, this.getCurrentUser());
    }

    private void logAction(String text, IUser user) {
        PERSISTENCE_FACADE.logAction(text, user);
    }
}
