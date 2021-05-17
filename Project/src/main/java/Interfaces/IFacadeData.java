package Interfaces;

import java.util.List;

public interface IFacadeData {

    IRightsholder getRightsholder(int id);

    List<IRightsholder> getRightsholders();

    void insertRightsholder(IRightsholder rightsholder);

    IProduction getProduction(String id);

    List<IProduction> getProductions();

    void insertProduction(IProduction production);

    void deleteProduction(IProduction production);
}
