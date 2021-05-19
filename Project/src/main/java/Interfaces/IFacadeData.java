package Interfaces;

import java.util.List;

public interface IFacadeData {

    IRightsholder getRightsholder(int id);

    List<IRightsholder> getRightsholders();

    void saveRightsholder(IRightsholder rightsholder);

    IProduction getProduction(String id);

    List<IProduction> getProductions();

    void saveProduction(IProduction production);

    void deleteProduction(IProduction production);
}
