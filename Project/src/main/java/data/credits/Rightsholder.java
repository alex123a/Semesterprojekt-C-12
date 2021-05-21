package data.credits;

import Interfaces.IProduction;
import Interfaces.IRightsholder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Rightsholder implements IRightsholder {
    private int id;
    private String firstName;
    private String lastName;
    private String description;
    private List<Integer> productions;

    public Rightsholder() {

    }

    public Rightsholder(int id, String firstName, String lastName, String description, List<Integer> productions) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.productions = productions;
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
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
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
        return "Credit: " + id + " " + firstName + " " + lastName + " " + description + " " + productions;
    }

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }
}
