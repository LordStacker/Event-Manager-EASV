package dk.easv.gui.controllers;

import dk.easv.be.Ticket;
import dk.easv.gui.models.TicketViewModel;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AddCustomerViewController {
    @FXML
    private MFXTextField nameTextField;
    @FXML
    private MFXTextField emailTextField;
    private Ticket ticket;
    private TicketViewModel model = new TicketViewModel();

    @FXML
    private void submitButtonAction(ActionEvent actionEvent) {
        model.assignTicketToCustomer(nameTextField.getText(), emailTextField.getText(), ticket);
    }

    @FXML
    private void cancelButtonAction(ActionEvent actionEvent) {
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
