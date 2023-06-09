package dk.easv.gui.controllers;

import dk.easv.be.Ticket;
import dk.easv.gui.controllers.abstractController.RootController;
import dk.easv.gui.models.TicketViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.print.PrinterException;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class TicketViewController extends RootController implements Initializable {

    @FXML
    private AnchorPane bgAnchor;
    @FXML
    private ImageView imgView;

    private final TicketViewModel model = new TicketViewModel();
    private Stage stage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initialed(Ticket ticket, int eventId){
        double imgScale = 0.5;
        this.stage = (Stage) bgAnchor.getScene().getWindow();
        Image image = model.getTicketImage(ticket, eventId);
        stage.setMinWidth(image.getWidth()*imgScale);
        stage.setMinHeight(image.getHeight()*imgScale+80);
        imgView.setImage(image);
        imgView.setFitWidth(image.getWidth()*imgScale);
        imgView.setFitHeight(image.getHeight()*imgScale);
        imgView.setViewport(new Rectangle2D(0, 0, image.getWidth(), image.getHeight()));
        stage.centerOnScreen();
        stage.setResizable(false);
        bgAnchor.setMinSize(image.getWidth()*imgScale, image.getHeight()*imgScale);
        bgAnchor.setPrefSize(image.getWidth()*imgScale, image.getHeight()*imgScale);
    }

    @FXML
    private void saveAsPDFAction(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose a directory to save the ticket");
        File selectedDirectory = directoryChooser.showDialog(stage);
        model.saveAsPDF(selectedDirectory);

    }

    @FXML
    private void printAction(ActionEvent actionEvent) throws PrinterException {
        model.printTicket();
    }
}
