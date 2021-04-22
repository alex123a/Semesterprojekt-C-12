package data;

import Interfaces.IProduction;
import Interfaces.IRightsholder;

import java.util.List;

public class FacadeData {
    private RightsHolderHandler rhHolder = RightsHolderHandler.getInstance();
    private ProductionHandler pHandler = ProductionHandler.getInstance();

    /**
     * Returns one rightsholder
     * @param id Identification of the rightsholder
     * @return Rightsholder Object
     */
    public IRightsholder getRightsholder(int id) {
        return rhHolder.readRightsholder(id);
    }

    /**
     * Returns all rightsholders
     * @return List of Rightsholders
     */
    public List<IRightsholder> getRightsholders() {
        return rhHolder.readRHFile();
    }

    /**
     * Inserting or editing a rightsholder
     * @param rightsholder Rightsholder object
     */
    public void insertRightsholder(IRightsholder rightsholder) {
        rhHolder.saveRightsholder(rightsholder);
    }

    /**
     * Returns one production
     * @param id Identification of the production
     */
    public IProduction getProduction(String id) {
        return pHandler.readProduction(id);
    }

    /**
     * Returns all productions
     * @return list with productions
     */
    public List<IProduction> getProductions() {
        return pHandler.readPFile();
    }

    /**
     * Inserting or editing a rightsholder
     * @param production Production Object
     */
    public void insertProduction(IProduction production) {
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
