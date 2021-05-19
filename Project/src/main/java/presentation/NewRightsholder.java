package presentation;

import Interfaces.IProduction;
import Interfaces.IRightsholder;

import java.util.List;

public class NewRightsholder implements IRightsholder {
    String firstName;
    String lastName;
    String description;

    public NewRightsholder(String firstName, String lastName, String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
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
    public List<IProduction> getRightsholderFor() {
        //TODO Should we get this from the database or?? how does the rightsholder class work?
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public String toString() {
        return "Name: " + this.firstName + " " + this.lastName + " Description: " + this.description;
    }
}
