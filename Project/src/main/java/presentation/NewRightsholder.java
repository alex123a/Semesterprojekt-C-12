package presentation;

import Interfaces.IProduction;
import Interfaces.IRightsholder;

import java.util.List;

public class NewRightsholder implements IRightsholder {

    String name;
    String description;

    public NewRightsholder(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public int getId() {
        return 0;
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
}
