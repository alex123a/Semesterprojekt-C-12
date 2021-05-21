package data.credits;

import Interfaces.IFacadeData;
import Interfaces.IProduction;
import Interfaces.IRightsholder;
import data.credits.ProductionHandler;
import data.credits.RightsHolderHandler;

import java.util.List;

public class FacadeData implements IFacadeData {
    private RightsHolderHandler rhHolder = RightsHolderHandler.getInstance();
    private ProductionHandler pHandler = ProductionHandler.getInstance();
    private static FacadeData instance;

    public static FacadeData getInstance() {
        if (instance == null) {
            instance = new FacadeData();
        }
        return instance;
    }

    private FacadeData() {

    }

    /**
     * Returns all rightsholders
     * @return List of Rightsholders
     */

    @Override
    public List<IRightsholder> getRightsholders() {
        return rhHolder.getRightsholders();
    }

    /**
     * Inserting or editing a rightsholder
     * @param rightsholder Rightsholder object
     */

    @Override
    public void saveRightsholder(IRightsholder rightsholder) {
        rhHolder.saveRightsholder(rightsholder);
    }

    @Override
    public void approveChangesToRightsholder(IRightsholder rightsholder) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns all productions
     * @return list with productions
     */

    @Override
    public List<IProduction> getProductions() {
        return pHandler.getProductions();
    }

    /**
     * Inserting or editing a rightsholder
     * @param production Production Object
     */

    @Override
    public IProduction saveProduction(IProduction production) {
        return pHandler.saveProduction(production);
    }

    /**
     * Delete a procution
     * @param production Production object
     */

    @Override
    public void deleteProduction(IProduction production) {
        pHandler.deleteProduction(production);
    }

    @Override
    public void approveChangesToProduction(IProduction production) {
        throw new UnsupportedOperationException();
    }

}
