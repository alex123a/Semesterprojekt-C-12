package data;

import Interfaces.IProduction;
import Interfaces.IRightsholder;

import java.util.List;

public class Rightsholder implements IRightsholder {
    private int id;
    private String name;
    private String description;
    private String[] productions;

    public Rightsholder() {

    }

    public Rightsholder(int id, String name, String description, String[] productions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.productions = productions;
    }

    // TODO Convert productions string from only ids to Production instances.


    public int getId() {
        return id;
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
        this.productions = productions;
    }

    public String[] getProductions() {
        return productions;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public List<IProduction> getRightsholderFor() {
        return null;
    }
}
