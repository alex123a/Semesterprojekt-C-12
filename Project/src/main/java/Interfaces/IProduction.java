package Interfaces;

import java.util.List;
import java.util.Map;

public interface IProduction {
    String getProductionID();
    void setProductionID(String productionID);
    String getName();
    void setName(String name);
    Map<IRightsholder, List<String>> getRightsholders();
    List<String> getRightsholderRole(IRightsholder rightsholder);
}
