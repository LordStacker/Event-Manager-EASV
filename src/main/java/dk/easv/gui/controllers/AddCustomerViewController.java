package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.be.Ticket;
import dk.easv.dal.dao.CustomerDAO;
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

    @FXML
    private void submitButtonAction(ActionEvent actionEvent) {
        model.assignTicketToCustomer(nameTextField.getText(), emailTextField.getText(), ticket);
        stage.close();
    }

    @FXML
    private void cancelButtonAction(ActionEvent actionEvent) {
        stage.close();
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
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
