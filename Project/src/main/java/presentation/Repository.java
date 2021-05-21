package presentation;

import Interfaces.ICreditManagement;
import Interfaces.IProduction;
import Interfaces.IRightsholder;
import domain.CreditsManagement.CreditsSystem;
import domain.searchEngine.SearchEngineHandler;
import javafx.stage.Stage;

public class Repository {
    private final static Repository instance = new Repository();

    public static Repository getInstance() {return instance;}

    private Stage window;
    private IProduction toEdit;
    private IProduction productionToBeShown;
    private IRightsholder rightsholderToBeShown;
    private String lastPage;

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

    public SearchEngineHandler searchEngineHandler = SearchEngineHandler.getInstance();

    public void setLastPage(String lastPage) {
        this.lastPage = lastPage;
    }

    public String getLastPage() {
        return lastPage;
    }
}
