package Interfaces;

import enumerations.ProductionGenre;
import enumerations.ProductionType;

import java.util.List;
import java.util.Map;

public interface IProduction {
    String getProductionID();
    void setProductionID(String productionID);
    String getName();
    void setName(String name);
    String getDescription();
    void setDescription(String description);
    int getYear();
    void setYear(int year);
    ProductionGenre getGenre();
    void setGenre(ProductionGenre genre);
    ProductionType getType();
    void setType(ProductionType type);
    //TODO add get and set producer
    void setRightsholders(Map<IRightsholder, List<String>> roles);
    Map<IRightsholder, List<String>> getRightsholders();
    List<String> getRightsholderRole(IRightsholder rightsholder);
}
