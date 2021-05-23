package Interfaces;

import java.util.List;

public interface IFacadeData {
    List<IRightsholder> getRightsholders();

    void saveRightsholder(IRightsholder rightsholder);

    void approveChangesToRightsholder(IRightsholder rightsholder);

    List<IProduction> getProductions();

    //Returns the new IProduction from the persistence layer
    IProduction saveProduction(IProduction production);

    void deleteProduction(IProduction production);

    void approveChangesToProduction(IProduction production);

}
