package domain;

import Interfaces.*;
import data.PersistenceFacade;
import domain.authentication.AuthenticationHandler;
import domain.searchEngine.SearchUserHandler;
import domain.session.CurrentSession;
import enumerations.ProductionGenre;
import enumerations.ProductionSorting;
import enumerations.ProductionType;
import enumerations.RightholderSorting;
import presentation.Repository;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;

public class DomainFacade implements IDomainFacade {
    private static final DomainFacade domain = new DomainFacade();

    @Override
    public boolean login(IUser user) {
        return AuthenticationHandler.getLoginInstance().login(user);
    }

    @Override
    public void addCredit(IProduction production, IRightsholder rightsholder, List<String> roles) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void removeCredit(IProduction production, IRightsholder rightsholder) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setProductionID(IProduction production, String productionID) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void addProduction(IProduction production) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void deleteProduction(IProduction production) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void saveChanges() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void cancelChanges() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setName(IProduction production, String name) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setRoles(IProduction production, Map<IRightsholder, List<String>> roles) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean editCredit(IRightsholder credit) {
        return false;
    }

    @Override
    public List<?> findMatch(List<ISearchable> list, String target) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<IRightsholder> sortPersonBy(List<IRightsholder> list, RightholderSorting type) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<IProduction> sortProductionBy(List<IProduction> list, ProductionSorting target) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<IProduction> filterProduction(List<IProduction> list, int[] yearInterval, ProductionGenre genre, ProductionType type) {
        return null;
    }


    @Override
    public List<IProduction> getProductions() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public IProduction getProduction(String id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean validateUser(IUser user) {
        return AuthenticationHandler.getUserInstance().validateUser(user);
    }

    public static DomainFacade getInstance() {
        return domain;
    }

    @Override
    public List<IUser> getUsers() {
        return PersistenceFacade.getInstance().getUsers();
    }

    @Override
    public boolean deleteUser(IUser user) {
        IUser currentUser = Repository.getInstance().domainFacade.getCurrentUser();
        if (Repository.getInstance().domainFacade.validateUser(currentUser) && user != null) {
            return PersistenceFacade.getInstance().deleteUser(user);
        }
        return false;
    }

    @Override
    public boolean editUser(IUser user) {
        IUser currentUser = Repository.getInstance().domainFacade.getCurrentUser();
        if (Repository.getInstance().domainFacade.validateUser(currentUser)) {
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
    public List<IUser> getUsersBySearch(IUser user) {
        return PersistenceFacade.getInstance().getUsersBySearch(user);
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
    public String generateStrongPasswordHash(String password) {
        try {
            return AuthenticationHandler.getInstance().generateStrongPasswordHash(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getInfoFromSearch(String search, String resultType) {
        return SearchUserHandler.getInstance().getInfoFromSearch(search, resultType);
    }
}
