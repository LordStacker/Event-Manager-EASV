package dk.easv.gui.controllers;

import dk.easv.be.Ticket;
import dk.easv.gui.models.TicketViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
        Stage stage = (Stage) bgAnchor.getScene().getWindow();
        Image image = model.getTicketImage();
        stage.setMinWidth(image.getWidth());
        stage.setMinHeight(image.getHeight()+30);
        imgView.setImage(image);
        imgView.setFitWidth(image.getWidth());
        imgView.setFitHeight(image.getHeight());
        imgView.setViewport(new Rectangle2D(0, 0, image.getWidth(), image.getHeight()));
        stage.centerOnScreen();
        stage.setResizable(false);
    }
}
