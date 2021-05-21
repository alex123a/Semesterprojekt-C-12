package presentation;

import Interfaces.ICreditManagement;
import Interfaces.IProduction;
import Interfaces.IRightsholder;
import domain.CreditsManagement.CreditsSystem;
import domain.DomainFacade;
import javafx.stage.Stage;

public class Repository {
    private final static Repository instance = new Repository();

    public static Repository getInstance() {return instance;}

    private Stage window;
    private IProduction toEdit;
    private IProduction productionToBeShown;
    private IRightsholder rightsholderToBeShown;

    public Stage getWindow() {
        return window;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public IProduction getToEdit() {
        return toEdit;
    }

    public void setToEdit(IProduction toEdit) {
        this.toEdit = toEdit;
    }

    public IProduction getProductionToBeShown() {
        return productionToBeShown;
    }

    public void setProductionToBeShown(IProduction toBeShown) {
        this.productionToBeShown = toBeShown;
    }

    public IRightsholder getRightsholderToBeShown() {
        return rightsholderToBeShown;
    }

    public void setRightsholderToBeShown(IRightsholder toBeShown) {
        this.rightsholderToBeShown = toBeShown;
    }

    public ICreditManagement creditsSystem = CreditsSystem.getInstance();

    public DomainFacade domainFacade = DomainFacade.getInstance();

}
