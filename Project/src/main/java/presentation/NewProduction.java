package presentation;

import Interfaces.IProduction;
import Interfaces.IRightsholder;

import java.util.List;
import java.util.Map;

//Create objects of this when you create a new production from the presentation-layer
public class NewProduction implements IProduction {

    private String productionID;
    private String name;
    Map<IRightsholder, List<String>> rightsholders;

    public NewProduction(String productionID, String name) {
        this.productionID = productionID;
        this.name = name;
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

    public void addRightsholder(IRightsholder rightsholder, List<String> roles){
        rightsholders.put(rightsholder, roles);
    }

    @Override
    public Map<IRightsholder, List<String>> getRightsholders() {
        return rightsholders;
    }

    @Override
    public List<String> getRightsholderRole(IRightsholder rightsholder) {
        return rightsholders.get(rightsholder);
    }
}
