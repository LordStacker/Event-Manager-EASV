package dk.easv.gui.controllers;

import dk.easv.util.AlertHelper;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEventViewController implements Initializable {
    @FXML
    private MFXButton submitButton;
    @FXML
    private MFXButton cancelButton;
    @FXML
    private MFXTextField eventExtraNotesField;
    @FXML
    private MFXTextField eventDirectionsField;
    @FXML
    private MFXDatePicker endDatePicker;
    @FXML
    private MFXDatePicker startDatePicker;
    @FXML
    private MFXTextField eventLocationField;
    @FXML
    private MFXTextField eventNameField;

    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initialed(){
        this.stage = (Stage) submitButton.getScene().getWindow();
        stage.setMinWidth(600);
        stage.setMinHeight(450);
    }

    public void checkForInput(){
        if (eventNameField == null ||eventNameField.getText().trim().isEmpty()){
            AlertHelper.showDefaultAlert("Please enter event name before submitting!", Alert.AlertType.WARNING);
        } else if (eventLocationField == null ||eventLocationField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter event location before submitting!", Alert.AlertType.WARNING);
        } else if (startDatePicker.getValue() == null) {
            AlertHelper.showDefaultAlert("Please enter event start date before submitting!", Alert.AlertType.WARNING);
        } else if (endDatePicker == null) {
            AlertHelper.showDefaultAlert("Please enter event end date before submitting!", Alert.AlertType.WARNING);
        } else if (eventDirectionsField == null || eventDirectionsField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter event directions before submitting!", Alert.AlertType.WARNING);
        } else if (eventExtraNotesField == null || eventExtraNotesField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter extra notes about the event before submitting!", Alert.AlertType.WARNING);
        }
    }
}
