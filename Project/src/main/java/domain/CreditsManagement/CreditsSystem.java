package domain.CreditsManagement;

import Interfaces.ICreditManagement;
import Interfaces.IProduction;
import Interfaces.IRightsholder;
import Interfaces.ISeeCredits;
import data.ProductionHandler;
import data.RightsHolderHandler;

import java.util.List;

public class CreditsSystem implements ICreditManagement, ISeeCredits {

    private static CreditsSystem instance = null;

    ProductionHandler productionHandler;
    RightsHolderHandler rightsholderHandler;

    List<IProduction> changedProductions;

    private CreditsSystem() {
        productionHandler = ProductionHandler.getInstance();
        rightsholderHandler = RightsHolderHandler.getInstance();
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

    @Override
    public void addProduction(IProduction production) {
        productionHandler.saveProduction(production);
    }

    @Override
    public void deleteProduction(IProduction production) {

    }

    @Override
    public void saveChanges() {
        for (IProduction production: changedProductions) {
            productionHandler.saveProduction(production);
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
        return productionHandler.readPFile();
    }
}
