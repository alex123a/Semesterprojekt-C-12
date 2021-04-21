package presentation.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import presentation.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


public class Controller {

        @FXML
        private Button menuMyBroadcast;
        @FXML
        private Button menuHelp;
        @FXML
        private Button menuHomepage;
        @FXML
        private Button menuEditUser;
        @FXML
        private VBox defaultBox;
        @FXML
        private VBox sportBox;
        @FXML
        private Label sportProgramName;
        @FXML
        private Label sportProgramTime;
        @FXML
        private VBox sportXBox;
        @FXML
        private Label sportXProgramName;
        @FXML
        private Label sportXProgramTime;
        @FXML
        private VBox charlieBox;
        @FXML
        private Label charlieProgramName;
        @FXML
        private Label charlieProgramTIme;
        @FXML
        private VBox newBox;
        @FXML
        private Label newsProgramName;
        @FXML
        private Label newsProgramTime;
        @FXML
        private VBox zuluBox;
        @FXML
        private Label zuluProgramName;
        @FXML
        private Label zuluProgramTime;
        @FXML
        private VBox friBox;
        @FXML
        private Label friProgramName;
        @FXML
        private Label friProgramTime;
        @FXML
        private Label loginBtn;
        @FXML
        private ImageView accountImage;
        @FXML
        private ImageView searchImage;


        @FXML
        void onBroadcastClicked(MouseEvent event) {
                try {
                        Parent root = FXMLLoader.load(getClass().getResource("/layout/my_productions.fxml"));
                        Stage window = Repository.getInstance().getWindow();
                        window.setScene(new Scene(root, window.getWidth(), window.getHeight()));

                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
            System.out.println("Broadcast");
        }

        @FXML
        void onHelpClicked(MouseEvent event) {
            //todo onHelp
            System.out.println("Help");
        }

        @FXML
        void onHomepageClicked(MouseEvent event) {
            try {
                java.awt.Desktop.getDesktop().browse(new URI("https://tv2.dk/"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }

        }

        @FXML
        void onEditUserClicked(MouseEvent event) {
            //todo onEditUser
            System.out.println("Edit User");

        }

        @FXML
        void onSearchClicked(){
                try {
                        Parent root = FXMLLoader.load(getClass().getResource("/layout/search.fxml"));
                        Stage window = Repository.getInstance().getWindow();
                        window.setScene(new Scene(root, window.getWidth(), window.getHeight()));

                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
                System.out.println("Search");
        }


        @FXML
        void onLoginClicked(MouseEvent event) {
            //todo onLogin

        }

        @FXML
        void onDefaultBoxClicked(MouseEvent event) {
            //todo implement default box
            System.out.println("Default");
        }

        @FXML
        void onSportBoxClicked(MouseEvent event) {
            //todo implement sport box
            System.out.println("Sport");
        }

        @FXML
        void onSportXBoxClicked(MouseEvent event) {
            //todo implement sport X box
            System.out.println("Sport X");
        }

        @FXML
        void onCharlieBoxClicked(MouseEvent event) {
            //todo implement Charlie box
            System.out.println("Charlie");
        }

        @FXML
        void onNewsBoxClicked(MouseEvent event) {
            //todo implement news box
            System.out.println("News");
        }

        @FXML
        void onZuluBoxClicked(MouseEvent event) {
            //todo implement Zulu box
            System.out.println("Zulu");
        }

        @FXML
        void onFriBoxClicked(MouseEvent event) {
            //todo implement fri box
            System.out.println("Fri");
        }

        public void onMouseEnteredAccount(MouseEvent mouseEvent) {
                Image image = new Image(getClass().getResourceAsStream("/images/Account_Red.jpg"));
                accountImage.setImage(image);
        }

        public void onMouseExitedAccount(MouseEvent mouseEvent) {
                Image image = new Image(getClass().getResourceAsStream("/images/Account_Grey.jpg"));
                accountImage.setImage(image);
        }

        public void onMouseEnteredSearch(MouseEvent mouseEvent) {
                Image image = new Image(getClass().getResourceAsStream("/images/Search_Red.jpg"));
                searchImage.setImage(image);
        }

        public void onMouseExitedSearch(MouseEvent mouseEvent) {
                Image image = new Image(getClass().getResourceAsStream("/images/Search_Grey.jpg"));
                searchImage.setImage(image);
        }


    }
