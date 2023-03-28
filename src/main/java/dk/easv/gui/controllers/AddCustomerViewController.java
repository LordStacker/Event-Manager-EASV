package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.be.Ticket;
import dk.easv.gui.models.EventModel;
import dk.easv.gui.models.TicketViewModel;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @FXML
    private void submitButtonAction(ActionEvent actionEvent) {
        model.assignTicketToCustomer(nameTextField.getText(), emailTextField.getText(), ticket);
        eventModel.getAllTickets(ticket.getEventId());
        stage.close();
    }

    @FXML
    private void cancelButtonAction(ActionEvent actionEvent) {
        stage.close();
    }

    @FXML
    private void removeCustomerData(ActionEvent actionEvent){
        model.deassignTicket(ticket.getTicketID());
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
