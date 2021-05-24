package domain.CreditsManagement;

import Interfaces.*;
import data.PersistenceFacade;
import data.credits.FacadeData;
import domain.session.CurrentSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreditsSystem implements ICreditManagement, ISeeCredits {

    private static CreditsSystem instance = null;

    List<IProduction> changedProductions;

    private CreditsSystem() {
        changedProductions = new ArrayList<>();
    }

    public static CreditsSystem getInstance() {
        if (instance==null) {
            instance = new CreditsSystem();
        }
        return instance;
    }

    @Override
    public void deleteProduction(IProduction production) {
        PersistenceFacade.getInstance().deleteProduction(production);
    }

    @Override
    public IProduction saveProduction(IProduction production) {
        return PersistenceFacade.getInstance().saveProduction(production);
    }

    @Override
    public List<IProduction> getProductions() {
        //Could save the data in an attribute
        return PersistenceFacade.getInstance().getProductions();
    }

    @Override
    public List<IProduction> getMyProductions() {
        return FacadeData.getInstance().getMyProductions(CurrentSession.getInstance().getCurrentUser());
    }

    @Override
    public void approveChangesToProduction(IProduction production) {
        PersistenceFacade.getInstance().approveChangesToProduction(production);
    }

    public List<IRightsholder> getAllRightsholders() {
        return PersistenceFacade.getInstance().getRightsholders();
    }

}
