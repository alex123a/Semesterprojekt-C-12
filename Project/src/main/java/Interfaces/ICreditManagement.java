package Interfaces;

import java.util.List;
import java.util.Map;

public interface ICreditManagement {

    //Removes a production from the database
    void deleteProduction(IProduction production);

    //"Commits" all the changes
    IProduction saveProduction(IProduction production);

    void approveChangesToProduction(IProduction production);

}
