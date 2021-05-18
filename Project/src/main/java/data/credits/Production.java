package data.credits;

import Interfaces.IProduction;
import Interfaces.IRightsholder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Production implements IProduction {
    private int id;
    private String productionID;
    private String name;
    private Map<Integer, List<String>> rightsholders;

    public Production() {

    }

    public Production(int id, String productionID, String name, Map<Integer,List<String>> rightsholders) {
        this.id = id;
        this.productionID = productionID;
        this.name = name;
        this.rightsholders = rightsholders;
    }

    @Override
    public String getProductionID() {
        return productionID;
    }

    @Override
    public void setProductionID(String productionID) {
        this.productionID = productionID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setRoles(Map<IRightsholder, List<String>> roles) {
        //TODO Implement
    }

    @Override
    public Map<IRightsholder, List<String>> getRightsholders() {
        //Converts a map of <int, List<String>> to map of <Rightsholder, List<String>>
        Map<IRightsholder, List<String>> map = new HashMap<>();
        for (int i: rightsholders.keySet()) {
            map.put(RightsHolderHandler.getInstance().getRightsholder(i), rightsholders.get(i));
        }
        return map;
    }

    @Override
    public List<String> getRightsholderRole(IRightsholder rightsholder) {
        return rightsholders.get(rightsholder);
    }

    @Override
    public String toString() {
        return productionID + ": " + name;
    }


}
