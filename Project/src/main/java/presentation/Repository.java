package presentation;

import Interfaces.IProduction;
import javafx.stage.Stage;

public class Repository {
    private static Repository instance = new Repository();

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



}
