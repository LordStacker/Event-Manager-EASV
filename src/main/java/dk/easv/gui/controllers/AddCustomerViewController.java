package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.be.Ticket;
import dk.easv.bll.LogicManager;
import dk.easv.gui.models.EventModel;
import dk.easv.gui.models.TicketViewModel;
import dk.easv.util.AlertHelper;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerViewController implements Initializable {
    @FXML
    private MFXTextField nameTextField;
    @FXML
    private MFXTextField emailTextField;
    private Ticket ticket;
    private Stage stage;
    private TicketViewModel model = new TicketViewModel();
    private EventModel eventModel;

    private LogicManager bll = new LogicManager();
    @FXML
    private void createButtonAction(ActionEvent actionEvent) {
        if (isInputValid()) {
            bll.createCustomer(nameTextField.getText().toString(), emailTextField.getText().toString());
            System.out.println("Succesfully created!");
            stage.close();
        }
    }

    private boolean isInputValid(){
        if (emailTextField == null || emailTextField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter customer E-mail before Creating!", Alert.AlertType.WARNING);
        } else if (nameTextField == null || nameTextField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter customer name before Creating!", Alert.AlertType.WARNING);
        } else
            return true;
        return false;
    }
    @FXML
    private void cancelButtonAction(ActionEvent actionEvent) {
        stage.close();
    }

    public void initialed() {
        this.stage = (Stage) nameTextField.getScene().getWindow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
