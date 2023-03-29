package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.be.Ticket;
import dk.easv.gui.models.EventModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.net.URL;
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
    @FXML
    private TableColumn<Ticket, MFXButton> assignTicketColumn;
    @FXML
    private TableColumn<Ticket, String > assignedTicketColumn;

    @FXML
    private TableColumn<Ticket, String> printTicketColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initialed(EventModel model) {
        this.model = model;
        this.stage = (Stage) cancelButton.getScene().getWindow();
        stage.setMinWidth(600);
        stage.setMinHeight(450);
        model.getAllTickets(eventId);
        populateTable();
        cancelButton.setOnAction(e -> cancelButtonClicked());
    }

    public void populateTable() {
        ticketId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTicketID().toString()));
        ticketNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTicketNumber()).asObject());
        ticketType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTicketType()));
        viewTicketColumn.setCellValueFactory(cellData -> {
            MFXButton viewButton = new MFXButton("View");
            viewButton.setOnAction(event -> {
                showTicket(cellData.getValue(), eventId);
            });
            return new SimpleObjectProperty(viewButton);
        });
        assignTicketColumn.setCellValueFactory(cellData -> {
            MFXButton viewButton = new MFXButton("Assign");
            viewButton.setOnAction(event -> {
                assignTicket(cellData.getValue());
            });
            return new SimpleObjectProperty(viewButton);
        });
        assignedTicketColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getCustomerId() != 0) {
                return new SimpleStringProperty("Assigned");
            } else {
                return new SimpleStringProperty("Not assigned");
            }
        });
        printTicketColumn.setCellValueFactory(cellData -> {
            MFXButton viewButton = new MFXButton("Print");
            viewButton.setOnAction(event -> {
                printTicket(cellData.getValue());
            });
            return new SimpleObjectProperty(viewButton);
        });
        ticketTableView.setItems(model.getObsTickets());
    }

    /*private void printTicket(Ticket value) {
        PrinterJob job = PrinterJob.getPrinterJob();
        if (job.printDialog()) {
            try {
                job.print();
            } catch (Exception ex) {

            }
        }
    }


     */

    public void printTicket(Ticket panel) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new Printable() {
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }
                graphics.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
                panel.printAll(graphics);
                return Printable.PAGE_EXISTS;
            }
        });
        if (job.printDialog()) {
            try {
                job.print();
            } catch (Exception ex) {

            }
        }
    }




    private void assignTicket(Ticket value) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/addCustomerView.fxml"));
            Parent root = fxmlLoader.load();
            AddCustomerViewController controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/calendar-plus.png"))));
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            value.setEventId(eventId);
            controller.initialed(value, model);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setEventId(int newEventId) {
        this.eventId = newEventId;
    }

    private void cancelButtonClicked() {
        stage.close();
    }

    @FXML
    private void tableMouseClicked(MouseEvent mouseEvent) {
        Ticket ticket = ticketTableView.getSelectionModel().getSelectedItem();
        if (mouseEvent.getClickCount() == 2 && (ticket != null)) {
            showTicket(ticket, eventId);
        }
    }

    private void showTicket(Ticket ticket, int eventId) {
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
            ticketViewController.initialed(ticket, eventId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
