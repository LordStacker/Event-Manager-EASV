package dk.easv.gui.controllers;

import dk.easv.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    private HBox upcomingEventsHBox;
    @FXML
    private AnchorPane nextEventPane;
    @FXML
    private Label nextEvenLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initEventsHBox();
        setupHBoxListener();
        setNextEvent("Next Event");
    }

    private void initEventsHBox() {
        for (int i = 0; i < 5; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/upcoming-event-view.fxml"));
            try {
                AnchorPane anchorPane = fxmlLoader.load();
                UpcomingEventController upcomingEventController = fxmlLoader.getController();
                upcomingEventController.setEvent("Event " + i);
                upcomingEventsHBox.getChildren().add(anchorPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupHBoxListener(){
        upcomingEventsHBox.widthProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Width: " + newValue);
        });
    }

    public void setNextEvent(String s) {
        this.setNextEvent(s, "https://images.unsplash.com/photo-1501281668745-f7f57925c3b4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80");
    }

    public void setNextEvent(String s, String imageURL) {
        nextEventPane.setStyle(nextEventPane.getStyle() + "-fx-background-image: url('" + imageURL + "');");
        nextEvenLabel.setText(s);
    }
}