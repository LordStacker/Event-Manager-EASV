package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.be.Event;
import dk.easv.be.Ticket;
import dk.easv.dal.dao.TicketDAO;
import dk.easv.gui.models.EventModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class DisplayTicketsViewController implements Initializable {

    @FXML
    MFXLegacyTableView<Ticket> ticketTableView;
    @FXML
    private TableColumn<Ticket, String> ticketId, ticketType;
    @FXML
    private TableColumn<Ticket, Integer> ticketNumber;
    @FXML
    private MFXButton cancelButton;
    @FXML
    private BorderPane borderPane;

    private EventModel model = new EventModel();
    private int eventId;
    private Stage stage;
    @FXML
    private TableColumn<Ticket, MFXButton> viewTicketColumn;

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
        ticketNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTicketNumber()).asObject());
        ticketType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTicketType()));
        viewTicketColumn.setCellValueFactory(cellData -> {
            MFXButton viewButton = new MFXButton("View");
            viewButton.setOnAction(event -> {
                try {
                    Stage stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/ticket-view.fxml"));
                    Parent root = fxmlLoader.load();
                    TicketViewController ticketViewController = fxmlLoader.getController();
                    Scene scene = new Scene(root);
                    stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/calendar-plus.png"))));
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();
                    ticketViewController.initialed(cellData.getValue());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            return new SimpleObjectProperty(viewButton);
        });
        ticketTableView.setItems(model.getObsTickets());
    }

    public void setEventId(int newEventId){
        this.eventId = newEventId;
    }

    private void cancelButtonClicked() {
        stage.close();
    }
}
