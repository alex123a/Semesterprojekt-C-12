package data.credits;

import Interfaces.IProducer;
import Interfaces.IProduction;
import Interfaces.IRightsholder;
import enumerations.ProductionGenre;
import enumerations.ProductionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Production implements IProduction {
    private int id;
    private String productionID;
    private String name;
    private String description;
    private int year;
    private ProductionGenre genre;
    private ProductionType type;
    private IProducer producer;
    private Map<Integer, List<String>> rightsholders;
    //Saves the rightsholder, so the list above doesn't need to be converted each time
    private Map<IRightsholder, List<String>> cachedMap;

    public Production() {

    }

    public Production(int id) {
        this.id = id;
    }

    public Production(int id, String productionID, String name, String description, int year, ProductionGenre genre, ProductionType type) {
        this.id = id;
        this.productionID = productionID;
        this.name = name;
        this.description = description;
        this.year = year;
        this.genre = genre;
        this.type = type;
    }

    public Production(int id, String productionID, String name, String description, int year, ProductionGenre genre, ProductionType type, IProducer producer, Map<Integer, List<String>> rightsholders) {
        this.id = id;
        this.productionID = productionID;
        this.name = name;
        this.description = description;
        this.year = year;
        this.genre = genre;
        this.type = type;
        this.producer = producer;
        this.rightsholders = rightsholders;
    }

    public int getID() {
        return this.id;
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
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

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
    public IProducer getProducer() {
        return this.producer;
    }

    @Override
    public void setProducer(IProducer producer) {
        this.producer = producer;
    }

    @Override
    public void setRightsholders(Map<IRightsholder, List<String>> roles) {
        cachedMap = roles;
    }

    @Override
    public Map<IRightsholder, List<String>> getRightsholders() {
        //Converts a map of <int, List<String>> to map of <Rightsholder, List<String>>
        if (cachedMap != null) {
            return cachedMap;
        }
        Map<IRightsholder, List<String>> map = new HashMap<>();
        for (int i: rightsholders.keySet()) {
            map.put(RightsHolderHandler.getInstance().getRightsholder(i), rightsholders.get(i));
        }
        cachedMap = map;
        return map;
    }

    @Override
    public List<String> getRightsholderRole(IRightsholder rightsholder) {
        return getRightsholders().get(rightsholder);
    }

    @Override
    public String toString() {
        return id + " " + productionID + ": " + name;
    }


}
