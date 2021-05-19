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

    /**
     * Returns one rightsholder
     * @param id Identification of the rightsholder
     * @return Rightsholder Object
     */
    public IRightsholder getRightsholder(int id) {
        return rhHolder.getRightsholder(id);
    }

    /**
     * Returns all rightsholders
     * @return List of Rightsholders
     */
    public List<IRightsholder> getRightsholders() {
        return rhHolder.getRightsholders();
    }

    /**
     * Inserting or editing a rightsholder
     * @param rightsholder Rightsholder object
     */
    public void saveRightsholder(IRightsholder rightsholder) {
        rhHolder.saveRightsholder(rightsholder);
    }

    /**
     * Returns one production
     * @param id Identification of the production
     */
    public IProduction getProduction(String id) {
        return pHandler.getProduction(Integer.parseInt(id));
    }

    /**
     * Returns all productions
     * @return list with productions
     */
    public List<IProduction> getProductions() {
        return pHandler.getProductions();
    }

    /**
     * Inserting or editing a rightsholder
     * @param production Production Object
     */
    public void saveProduction(IProduction production) {
        pHandler.saveProduction(production);
    }

    /**
     * Delete a procution
     * @param production Production object
     */
    public void deleteProduction(IProduction production) {
        pHandler.deleteProduction(production);
    }

}
