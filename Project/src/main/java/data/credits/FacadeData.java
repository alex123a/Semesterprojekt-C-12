package data.credits;

import Interfaces.IFacadeData;
import Interfaces.IProduction;
import Interfaces.IRightsholder;
import Interfaces.IUser;
import data.credits.ProductionHandler;
import data.credits.RightsHolderHandler;

import java.util.List;

public class FacadeData implements IFacadeData {
    private RightsHolderHandler rhHolder = RightsHolderHandler.getInstance();
    private ProductionHandler pHandler = ProductionHandler.getInstance();
    private static final FacadeData FDATA = new FacadeData();

    private FacadeData() {

    }

    public static FacadeData getInstance() {
        return FDATA;
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

    @Override
    public List<IProduction> getMyProductions(IUser user) {return pHandler.getProductionChanged(user);}

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
        pHandler.approveChangesToProduction(production);
    }

    public IProduction getProduction(IProduction production) {
        return pHandler.getProduction(production);
    }

}
