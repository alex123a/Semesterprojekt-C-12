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
    private String description;
    private int year;
    private ProductionGenre genre;
    private ProductionType type;
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
    public void setName(String name) {this.name = name;}

    @Override
    public int getYear() {
        return this.year;
    }

    @Override
    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public ProductionGenre getGenre() {
        return this.genre;
    }

    @Override
    public void setGenre(ProductionGenre genre) {
        this.genre = genre;
    }

    @Override
    public ProductionType getType() {
        return this.type;
    }

    @Override
    public void setType(ProductionType type) {
        this.type = type;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setRightsholders(Map<IRightsholder, List<String>> roles) {
        rightsholders = roles;
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
