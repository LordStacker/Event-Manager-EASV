package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.be.Ticket;
import dk.easv.gui.controllers.abstractController.RootController;
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

public class AddCustomerViewController extends RootController implements Initializable {
    @FXML
    private MFXTextField nameTextField;
    @FXML
    private MFXTextField emailTextField;
    private Ticket ticket;
    private Stage stage;
    private TicketViewModel model = new TicketViewModel();
    private EventModel eventModel;

    @FXML
    private void submitButtonAction(ActionEvent actionEvent) {
        if (isInputValid()) {
            model.assignTicketToCustomer(nameTextField.getText(), emailTextField.getText(), ticket);
            eventModel.getAllTickets(ticket.getEventId());

            stage.close();
        }
    }

    private boolean isInputValid(){
        if (emailTextField == null || emailTextField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter customer E-mail before submitting!", Alert.AlertType.WARNING);
        } else if (nameTextField == null || nameTextField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter customer name before submitting!", Alert.AlertType.WARNING);
        } else
            return true;
        return false;
    }
    @FXML
    private void cancelButtonAction(ActionEvent actionEvent) {
        stage.close();
    }

    @FXML
    private void removeCustomerData(ActionEvent actionEvent){
        model.deassignTicket(ticket.getTicketID());
        nameTextField.setText("");
        emailTextField.setText("");
    }

    public void initialed(Ticket ticket, EventModel eventModel) {
        this.ticket = ticket;
        this.eventModel = eventModel;
        if (ticket.getCustomerId() != 0) {
            Customer customer = model.getCustomer(ticket.getCustomerId());
            nameTextField.setText(customer.getCustomerName());
            emailTextField.setText(customer.getCustomerEmail());
        }
        this.stage = (Stage) nameTextField.getScene().getWindow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
