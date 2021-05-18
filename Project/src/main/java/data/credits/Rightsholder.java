package data.credits;

import Interfaces.IProduction;
import Interfaces.IRightsholder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rightsholder implements IRightsholder {
    private int id;
    private String name;
    private String description;
    private List<Integer> productions;

    public Rightsholder() {

    }

    public Rightsholder(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.productions = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProductions(List<Integer> productions) {
        this.productions = productions;
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
        List<IProduction> list = new ArrayList<>();
        for (int i: productions) {
            list.add(ProductionHandler.getInstance().getProduction(i));
        }
        return list;
    }

    @Override
    public String toString() {
        return "Credit: " + id + " " + name + " " + description + " " + productions;
    }
}
