package dk.easv.gui.controllers;

import dk.easv.gui.models.EventModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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


    private EventModel model = new EventModel();
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        submitButton.setOnAction(e -> submitButtonClicked());
    }

    private void submitButtonClicked() {
        model.addEvent("Test", "Test", startDatePicker.getValue(), endDatePicker.getValue(), "Test", "Test");
    }

    public void initialed(){
        this.stage = (Stage) submitButton.getScene().getWindow();
        stage.setMinWidth(600);
        stage.setMinHeight(450);
    }
}
