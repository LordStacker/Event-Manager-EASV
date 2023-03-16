package dk.easv.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class UpcomingEventController {
    @FXML
    private Label eventLabel;
    @FXML
    private Pane imgPane;

    public void setEventName(String s) {
        eventLabel.setText(s);
    }
}
