package presentation;

import Interfaces.ICreditManagement;
import Interfaces.IProduction;
import domain.CreditsManagement.CreditsSystem;
import javafx.stage.Stage;

public class Repository {
    private final static Repository instance = new Repository();

    public static Repository getInstance() {return instance;}

    private Stage window;
    private IProduction toEdit;

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

    public ICreditManagement creditsSystem = CreditsSystem.getInstance();

}
