package presentation.controllers;

import Interfaces.IProducer;
import domain.DomainFacade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    public Label tv2Default;
    public Label tv2Sport;
    public Label tv2Charlie;
    public Label tv2SportX;
    public Label tv2News;
    public Label tv2Zulu;
    public Label tv2Fri;
    public Label tv2DefaultTime;
    public Label tv2SportTime;
    public Label tv2SportXTime;
    public Label tv2CharlieTime;
    public Label tv2NewsTime;
    public Label tv2ZuluTime;
    public Label tv2FriTime;
    public Label subject;
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
    private Label numberOfNotifications = new Label();

    private DomainFacade domain = Repository.getInstance().domainFacade;

    @FXML
    void onBroadcastClicked(MouseEvent event) {
        // Opens the page with the producers productions
        try {
            Repository.getInstance().setLastPage("menu");
            Parent root = FXMLLoader.load(getClass().getResource("/layout/my_productions.fxml"));
            Stage window = (Stage) tv2Default.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHelpClicked(MouseEvent event) {
        // Opens the help page
        try {
            Repository.getInstance().setLastPage("menu");
            Parent root = FXMLLoader.load(getClass().getResource("/layout/helppage.fxml"));
            Stage window = (Stage) tv2Default.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHomepageClicked(MouseEvent event) {
        // Opens TV2.dk in a browser
        try {
            java.awt.Desktop.getDesktop().browse(new URI("https://tv2.dk/"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void onEditUserClicked(MouseEvent event) {
        // Opens the page where an admin can manage a user
        try {
            Repository.getInstance().setLastPage("menu");
            Parent root = FXMLLoader.load(getClass().getResource("/layout/ManageUser.fxml"));
            Stage window = Repository.getInstance().getWindow();
            window.setScene(new Scene(root, window.getWidth(), window.getHeight()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Edit User");

    }

    @FXML
    void onSearchClicked() {
        // Opens the search page
        try {
            Repository.getInstance().setLastPage("menu");
            Parent root = FXMLLoader.load(getClass().getResource("/layout/search.fxml"));
            Stage window = (Stage) menuMyBroadcast.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void shiftPage(Label channel) {
        // Gets the clicked channel and what's being shown
        // and opens the search page and inserts the program name
        Parent root;
        try {
            Repository.getInstance().setProgramName(channel.getText());
            root = FXMLLoader.load(getClass().getResource("/layout/search.fxml"));
            Stage window = (Stage) menuMyBroadcast.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onDefaultBoxClicked(MouseEvent event) {
        shiftPage(tv2Default);
    }

    @FXML
    void onSportBoxClicked(MouseEvent event) {
        shiftPage(tv2Sport);
    }

    @FXML
    void onSportXBoxClicked(MouseEvent event) {
        shiftPage(tv2SportX);
    }

    @FXML
    void onCharlieBoxClicked(MouseEvent event) {
        shiftPage(tv2Charlie);
    }

    @FXML
    void onNewsBoxClicked(MouseEvent event) {
        shiftPage(tv2News);
    }

    @FXML
    void onZuluBoxClicked(MouseEvent event) {
        shiftPage(tv2Zulu);
    }

    @FXML
    void onFriBoxClicked(MouseEvent event) {
        shiftPage(tv2Fri);
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the date
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM");
        subject.setText("TV-GUIDE " + formatter.format(new Date()));

        // Gets all the programs from TV2.dk
        List<String> strings = new ArrayList<>();
        try {
            URL url = new URL("https://tv.tv2.dk/");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("data-start")) {
                    strings.add(line);
                }
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sets up the now showing programs in the boxes
        long start;
        long end;
        Long time = System.currentTimeMillis() / 1000;
        Label[] programs = {tv2Default, tv2Sport, tv2SportX, tv2Charlie, tv2News, tv2Zulu, tv2Fri};
        Label[] times = {tv2DefaultTime, tv2SportTime, tv2SportXTime, tv2CharlieTime, tv2NewsTime, tv2ZuluTime, tv2FriTime};
        int count = 0;
        for (String s : strings) {
            start = Long.parseLong(s.substring(s.lastIndexOf("data-start") + 12, s.lastIndexOf("data-stop") - 2));
            end = Long.parseLong(s.substring(s.lastIndexOf("data-stop") + 11, s.lastIndexOf("style") - 2));
            if (time > start && time < end) {
                formatter = new SimpleDateFormat("HH:mm");
                Date startDate = new Date(start * 1000);
                Date endDate = new Date(end * 1000);
                programs[count].setText(s.substring(s.lastIndexOf("title=\"") + 7, s.lastIndexOf("data-program-id") - 2));
                times[count].setText(formatter.format(startDate) + " - " + formatter.format(endDate));
                count++;

                if (domain.validateUser(domain.getCurrentUser())) {
                    numberOfNotifications.setText("" + domain.countUnreadAdminNotifications());
                } else if (domain.getCurrentUser() instanceof IProducer) {
                    numberOfNotifications.setText("" + domain.countUnreadProducerNotifications(domain.getCurrentUser()));
                }
            }
        }
    }

    public void goToNotifications(MouseEvent mouseEvent) {
        // Opens notifications
        try {
            Repository.getInstance().setLastPage("menu");
            Parent root = FXMLLoader.load(getClass().getResource("/layout/notification.fxml"));
            Stage window = (Stage) menuMyBroadcast.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goToLogin(MouseEvent mouseEvent) {
        // Opens the login page
        try {
            Repository.getInstance().setLastPage("menu");
            Parent root = FXMLLoader.load(getClass().getResource("/layout/login.fxml"));
            Stage window = (Stage) menuMyBroadcast.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logOut(MouseEvent mouseEvent) {
        // Sends the user back to the standard menu for the standard users
        // and logs out the user
        Parent root = null;
        try {
            Repository.getInstance().setLastPage("menu");
            root = FXMLLoader.load(getClass().getResource("/layout/menu.fxml"));
            Stage window = (Stage) menuMyBroadcast.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DomainFacade.getInstance().setCurrentUser(null);
    }
}
