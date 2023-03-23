package dk.easv.gui.controllers;

import dk.easv.gui.models.EventModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditEventViewController implements Initializable {

@FXML
private MFXButton cancelButton;

@FXML
private MFXDatePicker endDatePicker;

@FXML
private MFXTextField eventDirectionsField;

@FXML
private MFXTextField eventEndField;

@FXML
private MFXTextField eventExtraNotesField;

@FXML
private MFXTextField eventLocationField;

@FXML
private MFXTextField eventNameField;

@FXML
private MFXTextField eventStartField;

@FXML
private MFXButton saveButton;

@FXML
private MFXDatePicker startDatePicker;

private EventModel model;

private Stage stage;

@FXML
private void checkForChange(ActionEvent actionEvent) {
    System.out.println("check");
}

@FXML
private void closeStage(ActionEvent actionEvent) {
    System.out.println("Close");
}


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void initialed(EventModel model){
        this.model = model;
        this.stage = (Stage) saveButton.getScene().getWindow();
        stage.setMinWidth(600);
        stage.setMinHeight(450);
    }
}
