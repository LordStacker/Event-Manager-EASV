package dk.easv.gui.controllers;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.dal.dao.EventDAO;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEventViewController implements Initializable {

    private EventDAO eventDAO = new EventDAO();
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


    @FXML
    public void onSubmit(ActionEvent actionEvent) throws SQLServerException {
        eventNameField.getText().toString();
        eventExtraNotesField.getText().toString();
        eventLocationField.getText().toString();
        startDatePicker.getValue();
        endDatePicker.getValue();
        eventDirectionsField.getText().toString();
        eventDAO.eventPostMethod(eventNameField.getText().toString(), eventExtraNotesField.getText().toString(), eventLocationField.getText().toString(),startDatePicker.getValue(),endDatePicker.getValue(),  eventDirectionsField.getText().toString()  );
    }


}
