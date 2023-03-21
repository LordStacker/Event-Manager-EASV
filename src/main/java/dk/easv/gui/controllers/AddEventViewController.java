package dk.easv.gui.controllers;

import dk.easv.util.AlertHelper;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXSpinner;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

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
    private MFXTextField eventStartField;
    @FXML
    private MFXTextField eventEndField;
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(eventStartField.getText());
        } catch (ParseException e) {
            AlertHelper.showDefaultAlert("Please follow the format instructions when setting time of event!", Alert.AlertType.WARNING);
        }

        if (eventNameField == null ||eventNameField.getText().trim().isEmpty()){
            AlertHelper.showDefaultAlert("Please enter event name before submitting!", Alert.AlertType.WARNING);
        } else if (eventLocationField == null ||eventLocationField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter event location before submitting!", Alert.AlertType.WARNING);
        } else if (startDatePicker.getValue() == null) {
            AlertHelper.showDefaultAlert("Please enter event start date before submitting!", Alert.AlertType.WARNING);
        } else if (endDatePicker.getValue() == null) {
            AlertHelper.showDefaultAlert("Please enter event end date before submitting!", Alert.AlertType.WARNING);
        } else if (eventStartField == null || eventStartField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter event start hour before submitting!", Alert.AlertType.WARNING);
        } else if (eventEndField == null || eventEndField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter event end hour before submitting!", Alert.AlertType.WARNING);
        } else if (eventDirectionsField == null || eventDirectionsField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter event directions before submitting!", Alert.AlertType.WARNING);
        } else if (eventExtraNotesField == null || eventExtraNotesField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter extra notes about the event before submitting!", Alert.AlertType.WARNING);
        }
    }
}
