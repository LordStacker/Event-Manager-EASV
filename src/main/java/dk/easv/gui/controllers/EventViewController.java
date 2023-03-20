package dk.easv.gui.controllers;

import dk.easv.util.AlertHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EventViewController implements Initializable {

    @FXML
    TextField eventNameField, eventLocationField, eventStartField, eventEndField, eventDirectionsField, eventExtraNotesField;

    @FXML
    Button createEventSubmitButton, createEventCancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void checkForInput(){
        if (eventNameField == null ||eventNameField.getText().trim().isEmpty()){
            AlertHelper.showDefaultAlert("Please enter event name before submitting!", Alert.AlertType.WARNING);
        } else if (eventLocationField == null ||eventLocationField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter event location before submitting!", Alert.AlertType.WARNING);
        } else if (eventStartField == null || eventStartField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter event start date before submitting!", Alert.AlertType.WARNING);
        } else if (eventEndField == null || eventEndField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter event end date before submitting!", Alert.AlertType.WARNING);
        } else if (eventDirectionsField == null || eventDirectionsField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter event directions before submitting!", Alert.AlertType.WARNING);
        } else if (eventExtraNotesField == null || eventExtraNotesField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter extra notes about the event before submitting!", Alert.AlertType.WARNING);
        }
    }
}
