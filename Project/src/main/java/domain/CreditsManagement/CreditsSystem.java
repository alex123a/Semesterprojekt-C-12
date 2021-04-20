package domain.CreditsManagement;

import Interfaces.ICreditManagement;
import Interfaces.IProduction;
import Interfaces.IRightsholder;
import Interfaces.ISeeCredits;

import java.util.List;

public class CreditsSystem implements ICreditManagement, ISeeCredits {
    @Override
    public void addCredit(IProduction production, IRightsholder rightsholder, List<String> roles) {

    }

    @Override
    public void removeCredit(IProduction production, IRightsholder rightsholder) {

    }

    @Override
    public void setProductionID(IProduction production, String productionID) {

    }

    @Override
    public void addProduction(IProduction production) {

    }

    @Override
    public void deleteProduction(String productionID) {

    }

    @Override
    public void saveChanges() {

    }

    @Override
    public void cancelChanges() {

    }

    @Override
    public List<IProduction> getProductions() {
        return null;
    }

    @Override
    public IProduction getProduction(String productionID) {
        return null;
    }
}
