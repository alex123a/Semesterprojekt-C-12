package Interfaces;

import java.util.List;
import java.util.Map;

public interface IProduction {
    String getProductionID();
    String getName();
    List<IRightsholder> getRightsholders();
    List<String> getRightsholdersRoles();
    Map<IRightsholder, List<String>> getRightsholder();
    List<String> getRightsholderRole(IRightsholder rightsholder);
}
