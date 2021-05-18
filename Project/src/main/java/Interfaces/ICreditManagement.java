package Interfaces;

import java.util.List;
import java.util.Map;

public interface ICreditManagement {
    //Adds a single rightsholder and their roles to a single production
    void addCredit(IProduction production, IRightsholder rightsholder, List<String> roles);



    //Removes a rightsholder from a production
    void removeCredit(IProduction production, IRightsholder rightsholder);

    void setProductionID(IProduction production, String productionID);

    //Creates a new production
    void addProduction(IProduction production);

    //Removes a production from the database
    void deleteProduction(IProduction production);

    //"Commits" all the changes
    void saveChanges();

    //Undo the changes made since the last saveChanges() call
    void cancelChanges();

    void setName(IProduction production, String name);

    void setRoles(IProduction production, Map<IRightsholder, List<String>> roles);

    boolean editCredit(IRightsholder credit);
}
