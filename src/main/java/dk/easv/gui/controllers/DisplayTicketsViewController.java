package dk.easv.gui.controllers;

import dk.easv.be.Event;
import dk.easv.be.Ticket;
import dk.easv.dal.dao.TicketDAO;
import dk.easv.gui.models.EventModel;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DisplayTicketsViewController implements Initializable {

    @FXML
    MFXLegacyTableView ticketTableView;
    @FXML
    private TableColumn<Ticket, String> ticketId, ticketNumber, ticketType;

    private EventModel model = new EventModel();
    private int eventId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void populateTable(){
        ticketId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTicketID().toString()));
        ticketNumber.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTicketNumber())));
        ticketType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTicketType()));
        ticketTableView.setItems(model.getObsTickets());
    }

    public void setEventId(int newEventId){eventId = newEventId;}
}
