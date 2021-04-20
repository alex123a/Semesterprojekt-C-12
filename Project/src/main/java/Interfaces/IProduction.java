package Interfaces;

import java.util.List;
import java.util.Map;

public interface IProduction {
    String getProductionID();
    void setProductionID(String productionID);
    String getName();
    Map<IRightsholder, List<String>> getRightsholder();
    List<String> getRightsholderRole(IRightsholder rightsholder);
}
