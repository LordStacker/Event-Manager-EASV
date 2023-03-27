package dk.easv.gui.controllers;

import dk.easv.be.Ticket;
import dk.easv.gui.models.TicketViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TicketViewController implements Initializable {

    @FXML
    private AnchorPane bgAnchor;
    @FXML
    private ImageView imgView;

    private final TicketViewModel model = new TicketViewModel();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initialed(Ticket ticket){
        model.setTicket(ticket);
        imgView.setImage(model.getTicketImage());
    }
}
