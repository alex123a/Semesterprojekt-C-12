package domain.CreditsManagement;

import Interfaces.ICreditManagement;
import Interfaces.IProduction;
import Interfaces.IRightsholder;
import Interfaces.ISeeCredits;
import data.FacadeData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreditsSystem implements ICreditManagement, ISeeCredits {

    private static CreditsSystem instance = null;

    FacadeData facadeData = new FacadeData();

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
    
    @Override
    public void addCredit(IProduction production, IRightsholder rightsholder, List<String> roles) {
        production.getRightsholders().put(rightsholder, roles);
        if (!changedProductions.contains(production)){
            changedProductions.add(production);
        }
    }

    @Override
    public void removeCredit(IProduction production, IRightsholder rightsholder) {
        production.getRightsholders().remove(rightsholder);
        if (!changedProductions.contains(production)){
            changedProductions.add(production);
        }
    }

    @Override
    public void setProductionID(IProduction production, String productionID) {
        production.setProductionID(productionID);
        changedProductions.add(production);
    }

    public void setName(IProduction production, String name) {
        production.setName(name);
        changedProductions.add(production);
    }

    public void setRoles(IProduction production, Map<IRightsholder, List<String>> roles) {
        production.setRoles(roles);
        changedProductions.add(production);
    }

    @Override
    public boolean editCredit(IRightsholder credit) {
        return false;
    }

    @Override
    public void addProduction(IProduction production) {
        facadeData.insertProduction(production);
    }

    @Override
    public void deleteProduction(IProduction production) {
        facadeData.deleteProduction(production);
    }

    @Override
    public void saveChanges() {
        for (IProduction production: changedProductions) {
            facadeData.insertProduction(production);
        }
        changedProductions.clear();
    }

    @Override
    public void cancelChanges() {
        changedProductions.clear();
    }

    @Override
    public List<IProduction> getProductions() {
        //Could save the data in an attribute
        return facadeData.getProductions();
    }

    @Override
    public IProduction getProduction(String id) {
        return facadeData.getProduction(id);
    }


    public List<IRightsholder> getAllRightsholders() {
        return facadeData.getRightsholders();
    }

}
