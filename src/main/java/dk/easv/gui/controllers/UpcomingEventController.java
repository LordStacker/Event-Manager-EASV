package dk.easv.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class UpcomingEventController implements Initializable {
    @FXML
    private Label eventLabel;
    @FXML
    private Pane imgPane;
    public void setEvent(String s) {
        this.setEvent(s, "https://images.unsplash.com/photo-1472653431158-6364773b2a56?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2740&q=80");
    }

    public void setEvent(String s, String imageURL) {
        imgPane.setStyle(imgPane.getStyle() + "-fx-background-image: url('" + imageURL + "');");
        eventLabel.setText(s);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
