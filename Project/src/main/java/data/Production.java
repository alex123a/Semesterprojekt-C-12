package data;

import Interfaces.IProduction;
import Interfaces.IRightsholder;

import java.util.List;
import java.util.Map;

public class Production implements IProduction {
    private String productionID;
    private String name;
    private Map<IRightsholder, List<String>> rightsholder;

    public Production() {

    }

    public Production(String productionID, String name, Map<IRightsholder, List<String>> rightsholder) {
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
}
