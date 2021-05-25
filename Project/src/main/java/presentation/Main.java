package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/layout/menu.fxml"));

        Repository.getInstance().setWindow(primaryStage);
        primaryStage.setTitle(" Krediteringssystem");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Tv2Logo.png")));
        primaryStage.setScene(new Scene(root, 1300, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
