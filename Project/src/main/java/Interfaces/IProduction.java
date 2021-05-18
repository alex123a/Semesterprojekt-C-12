package Interfaces;

import enumerations.ProductionGenre;
import enumerations.ProductionType;

import java.util.List;
import java.util.Map;

public interface IProduction {
    String getProductionID();
    void setProductionID(String productionID);
    String getName();
    int getYear();
    void setYear();
    ProductionGenre getGenre();
    void setGenre(ProductionGenre genre);
    ProductionType getType();
    void setType(ProductionType type);
    void setName(String name);
    void setRoles(Map<IRightsholder, List<String>> roles);
    Map<IRightsholder, List<String>> getRightsholders();
    List<String> getRightsholderRole(IRightsholder rightsholder);
}
