package presentation;

import Interfaces.IProduction;
import Interfaces.IRightsholder;

import java.util.List;

public class NewRightsholder implements IRightsholder {
    String name;
    String description;
    List<String> roles;


    public NewRightsholder(String name, String description, List<String> roles) {
        this.name = name;
        this.description = description;
        this.roles = roles;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }



    @Override
    public List<IProduction> getRightsholderFor() {
        //TODO Should we get this from the database or?? how does the rightsholder class work?
        return null;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + " Description: " + this.description + " Roles: " + this.roles;
    }
}
