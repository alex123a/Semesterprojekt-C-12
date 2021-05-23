package domain.CreditsManagement;

import Interfaces.*;
import data.PersistenceFacade;
import data.credits.FacadeData;
import domain.session.CurrentSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreditsSystem implements ICreditManagement, ISeeCredits {

    private static CreditsSystem instance = null;

    List<IProduction> changedProductions;

    private CreditsSystem() {
        changedProductions = new ArrayList<>();
    }

    public static CreditsSystem getInstance() {
        if (instance==null) {
            instance = new CreditsSystem();
        }
        return instance;
    }

    public void addCredit(IProduction production, IRightsholder rightsholder, List<String> roles) {
        production.getRightsholders().put(rightsholder, roles);
        if (!changedProductions.contains(production)){
            changedProductions.add(production);
        }
    }

    public void removeCredit(IProduction production, IRightsholder rightsholder) {
        production.getRightsholders().remove(rightsholder);
        if (!changedProductions.contains(production)){
            changedProductions.add(production);
        }
    }

    public void setProductionID(IProduction production, String productionID) {
        production.setProductionID(productionID);
        changedProductions.add(production);
    }

    public void setName(IProduction production, String name) {
        production.setName(name);
        changedProductions.add(production);
    }

    public void setRoles(IProduction production, Map<IRightsholder, List<String>> roles) {
        production.setRightsholders(roles);
        changedProductions.add(production);
    }

    public boolean editCredit(IRightsholder credit) {
        return false;
    }

    public void addProduction(IProduction production) {
        PersistenceFacade.getInstance().saveProduction(production);
    }

    @Override
    public void deleteProduction(IProduction production) {
        PersistenceFacade.getInstance().deleteProduction(production);
    }

    @Override
    public void saveProduction(IProduction production) {
        PersistenceFacade.getInstance().saveProduction(production);
    }

    public void saveChanges() {
        for (IProduction production: changedProductions) {
            PersistenceFacade.getInstance().saveProduction(production);
        }
        changedProductions.clear();
    }

    public void cancelChanges() {
        changedProductions.clear();
    }

    @Override
    public List<IProduction> getProductions() {
        //Could save the data in an attribute
        return PersistenceFacade.getInstance().getProductions();
    }

    @Override
    public List<IProduction> getMyProductions() {
        return FacadeData.getInstance().getMyProductions(CurrentSession.getInstance().getCurrentUser());
    }

    public List<IRightsholder> getAllRightsholders() {
        return PersistenceFacade.getInstance().getRightsholders();
    }

}
