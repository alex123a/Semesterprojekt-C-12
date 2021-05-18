package presentation;

import Interfaces.IProduction;
import Interfaces.IRightsholder;
import enumerations.ProductionGenre;
import enumerations.ProductionType;

import java.util.List;
import java.util.Map;

//Create objects of this when you create a new production from the presentation-layer
public class NewProduction implements IProduction {

    private String productionID;
    private String name;
    Map<IRightsholder, List<String>> rightsholders;

    public NewProduction(String productionID, String name, Map<IRightsholder, List<String>> rightsholders) {
        this.productionID = productionID;
        this.name = name;
        this.rightsholders  = rightsholders;
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
    public int getYear() {
        return 0;
    }

    @Override
    public void setYear() {

    }

    @Override
    public ProductionGenre getGenre() {
        return null;
    }

    @Override
    public void setGenre(ProductionGenre genre) {

    }

    @Override
    public ProductionType getType() {
        return null;
    }

    @Override
    public void setType(ProductionType type) {

    }

    @Override
    public void setName(String name) {this.name = name;}

    @Override
    public void setRoles(Map<IRightsholder, List<String>> roles) {
        rightsholders = roles;
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
