package dk.easv.gui.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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

@FXML
    void chechForChange(ActionEvent event) {

            }

@FXML
    void closeStage(ActionEvent event) {

            }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
