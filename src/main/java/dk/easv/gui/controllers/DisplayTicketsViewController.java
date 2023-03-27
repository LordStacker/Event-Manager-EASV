package dk.easv.gui.controllers;

import dk.easv.be.Event;
import dk.easv.be.Ticket;
import dk.easv.dal.dao.TicketDAO;
import dk.easv.gui.models.EventModel;
import dk.easv.util.AlertHelper;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DisplayTicketsViewController implements Initializable {

    @FXML
    MFXLegacyTableView ticketTableView;
    @FXML
    private TableColumn<Ticket, String> ticketId, ticketNumber, ticketType, qrCodeColumn;
    @FXML
    private MFXButton cancelButton;
    @FXML
    private BorderPane borderPane;

    private EventModel model = new EventModel();
    private int eventId;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initialed(EventModel model){
        this.model = model;
        this.stage = (Stage) cancelButton.getScene().getWindow();
        stage.setMinWidth(600);
        stage.setMinHeight(450);
        model.getAllTickets(eventId);
        populateTable();
        cancelButton.setOnAction(e -> cancelButtonClicked());
    }

    public void populateTable(){
        ticketId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTicketID().toString()));
        ticketNumber.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTicketNumber())));
        ticketType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTicketType()));

        qrCodeColumn.setCellValueFactory(cellData -> {
            MFXButton qrCodeButton = new MFXButton("QR Code");
            qrCodeButton.setOnAction(event -> showQrCode("Free stuff for ticket with id: " + cellData.getValue().getTicketID()));
            return new SimpleObjectProperty(qrCodeButton);
        });

        ticketTableView.setItems(model.getObsTickets());
    }

    public void setEventId(int newEventId){
        this.eventId = newEventId;
    }

    private void cancelButtonClicked() {
        stage.close();
    }

    private void showQrCode(String codedText){
        // GENERATE QR CODE
        ByteArrayOutputStream preparedQrCode = QRCode.from(codedText).to(ImageType.PNG).withSize(200, 200).stream();
        ByteArrayInputStream createdQrCode = new ByteArrayInputStream(preparedQrCode.toByteArray());

        // SHOW QR CODE
        BorderPane root = new BorderPane();
        Image image = new Image(createdQrCode);
        ImageView view = new ImageView(image);
        view.setStyle("-fx-stroke-width: 2; -fx-stroke: blue");
        root.setCenter(view);
        Scene scene = new Scene(root, 200, 200);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
