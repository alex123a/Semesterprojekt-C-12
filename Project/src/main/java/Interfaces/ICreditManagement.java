package Interfaces;

import java.util.List;

public interface ICreditManagement {
    void addCredit(IProduction production, IRightsholder rightsholder, List<String> roles);
    void removeCredit(IProduction production, IRightsholder rightsholder);
    void setProductionID(IProduction production, String productionID);
    void addProduction(IProduction production);
    void deleteProduction(String productionID);
    void saveChanges();
    void cancelChanges();
}
