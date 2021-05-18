package data.credits;

import Interfaces.IProduction;
import Interfaces.IRightsholder;
import enumerations.ProductionGenre;
import enumerations.ProductionType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Production implements IProduction {
    private int id;
    private String productionID;
    private String name;
    private int year;
    private ProductionGenre genre;
    private ProductionType type;
    private Map<Integer, List<String>> rightsholders;

    public Production() {

    }

    public Production(int id, String productionID, String name, int year, ProductionGenre genre, ProductionType type, Map<Integer, List<String>> rightsholders) {
        this.id = id;
        this.productionID = productionID;
        this.name = name;
        this.year = year;
        this.genre = genre;
        this.type = type;
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
    public int getYear() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public void setYear() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public ProductionGenre getGenre() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public void setGenre(ProductionGenre genre) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public ProductionType getType() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public void setType(ProductionType type) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public void setRoles(Map<IRightsholder, List<String>> roles) {
        throw new UnsupportedOperationException("not implemented yet");
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
        return "Production{" +
                "id=" + id +
                ", productionID='" + productionID + '\'' +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", genre=" + genre +
                ", type=" + type +
                ", rightsholders=" + rightsholders +
                '}';
    }
}
