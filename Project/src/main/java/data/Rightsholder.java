package data;

import Interfaces.IProduction;
import Interfaces.IRightsholder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Rightsholder implements IRightsholder {
    private int id;
    private String name;
    private String description;
    private List<String> productions;

    public Rightsholder() {

    }

    public Rightsholder(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.productions = new ArrayList<>();
    }

    public Rightsholder(int id, String name, String description, String[] productions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.productions = Arrays.asList(productions);
    }

    public String listToString() {
        String word = "[";
        for (int i = 0; i < productions.size(); i++) {
            word = word + productions.get(i);
            if (i < productions.size() - 1) {
                word = word + ",";
            }
        }
        word = word + "]";
        return word;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProductions(String[] productions) {
        this.productions = Arrays.asList(productions);
    }

    public void setProductions(List<String> productions) {
        this.productions = productions;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public List<IProduction> getRightsholderFor() {
        ProductionHandler prhandler = ProductionHandler.getInstance();
        List<IProduction> productionsList = new ArrayList<>();

        for (String id: this.productions) {
            productionsList.add(prhandler.readProduction(id));
        }

        return productionsList;
    }

    @Override
    public String toString() {
        return "Credit: " + id + " " + name + " " + description + " " + productions;
    }
}
