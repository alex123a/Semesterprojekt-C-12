package data;

import Interfaces.IProduction;
import Interfaces.IRightsholder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Production implements IProduction {
    private String productionID;
    private String name;
    private Map<IRightsholder, List<String>> rightsholder;

    public Production() {

    }

    public Production(String productionID, String name, Map<IRightsholder,List<String>> rightsholder) {
        this.productionID = productionID;
        this.name = name;
        this.rightsholder = rightsholder;
    }

    @Override
    public String getProductionID() {
        return this.productionID;
    }

    public void setProductionID(String productionID) {
        this.productionID = productionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<IRightsholder, List<String>> getRightsholder() {
        return rightsholder;
    }

    @Override
    public List<String> getRightsholderRole(IRightsholder rightsholder) {
        return this.rightsholder.get(rightsholder);
    }

    public void setRightsholder(Map<IRightsholder, List<String>> rightsholder) {
        this.rightsholder = rightsholder;
    }

    // Creating a map with ID integer as key instead of the whole rightholder object.
    public Map<Integer, List<String>> convertRHToIDs() {
        Map<Integer, List<String>> newMap = new HashMap<>();
        for (IRightsholder rh: rightsholder.keySet()) {
            int theId = ((Rightsholder) rh).getId();
            newMap.put(theId, rightsholder.get(rh));
        }
        return newMap;
    }

    public String mapToString() {
        Map<Integer, List<String>> map = convertRHToIDs();
        String rightholderString = "";
        for (Integer rh: map.keySet()) {
            rightholderString = rightholderString + "" + rh + ":[";

            for (int i = 0; i < map.get(rh).size(); i++) {
                rightholderString = rightholderString + map.get(rh).get(i);
                if (i < map.get(rh).size() - 1) {
                    rightholderString = rightholderString + ",";
                }
            }

            rightholderString = rightholderString + "]";
        }
        return rightholderString;
    }

    @Override
    public String toString() {
        return "This production has the id " + productionID + " and the name " + name + " and the rightholders: " + rightsholder;
    }


}
