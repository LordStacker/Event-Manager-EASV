package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.be.Event;
import dk.easv.gui.models.EventModel;
import dk.easv.util.AlertHelper;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    private HBox upcomingEventsHBox;
    @FXML
    private AnchorPane nextEventPane;

    private final ArrayList<AnchorPane> upcomingEvents = new ArrayList<>();
    private int currentVolume = 1;

    private Stage stage;
    @FXML
    private TableColumn<Event, String> upcomingNameColumn, pastNameColumn, upcomingAttendanceColumn, pastAttendanceColumn, upcomingColDel, pastColDel;
    @FXML
    private TableColumn<Event, String> pastDateColumn, upcomingDateColumn;
    @FXML
    private MFXLegacyTableView<Event> upcomingEventsTable;
    @FXML
    private MFXLegacyTableView<Event> pastEventsTable;
    @FXML
    private Label nextEventLabel;

    private final EventModel model = new EventModel();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTables();
    }

    private void setTables() {
        upcomingNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEventName()));
        upcomingDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEventStartDate().toString()));
        upcomingAttendanceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEventTicketsSold() + "/" + cellData.getValue().getEventTickets()));
        upcomingColDel.setCellValueFactory(cellData -> {
            MFXButton deleteButton = new MFXButton("Delete");
            deleteButton.setOnAction(event -> {
                var alert = AlertHelper.showOptionalAlertWindow("Sure to delete the next event? ", cellData.getValue().getEventName(), Alert.AlertType.CONFIRMATION);
                if(alert.isPresent() && alert.get().equals(ButtonType.OK)){
                    model.deleteEvent(cellData.getValue().getEventID());
                }
            });
            return new SimpleObjectProperty(deleteButton);
        });
        pastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEventName()));
        pastDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEventStartDate().toString()));
        pastAttendanceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEventTicketsSold() + "/" + cellData.getValue().getEventTickets()));
        pastColDel.setCellValueFactory(cellData -> {
            MFXButton deleteButton = new MFXButton("Delete");
            deleteButton.setOnAction(event -> {
                var alert = AlertHelper.showOptionalAlertWindow("Sure to delete the next event? ", cellData.getValue().getEventName(), Alert.AlertType.CONFIRMATION);
                if(alert.isPresent() && alert.get().equals(ButtonType.OK)){
                    model.deleteEvent(cellData.getValue().getEventID());
                }
            });
            return new SimpleObjectProperty(deleteButton);
        });

        upcomingEventsTable.setItems(model.getObsFutureEvents());
        pastEventsTable.setItems(model.getObsPastEvents());

        upcomingEventsTable.setMinHeight(400);
        pastEventsTable.setMinHeight(400);
    }

    public void initialed(Stage stage, int stageWidth, int stageHeight) {
        this.stage = stage;
        setupHBoxListener();

        initEventsHBox();
        String nextEventName;
        int nextEventId = 0;
        if (model.getObsFutureEvents().size() > 0) {
            nextEventName = model.getObsFutureEvents().get(0).getEventName();
            nextEventId = model.getObsFutureEvents().get(0).getEventID();
        } else {
            nextEventName = "No upcoming events";
        }
        setNextEvent(nextEventName, nextEventId);
        stage.setMinWidth(650);
        stage.setWidth(stageWidth);
        stage.setHeight(stageHeight);
        stage.setTitle("Event Manager");
    }

    private void initEventsHBox() {
        for (int i = 1; i < model.getObsFutureEvents().size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/upcoming-event-view.fxml"));
            try {
                AnchorPane anchorPane = fxmlLoader.load();
                UpcomingEventController upcomingEventController = fxmlLoader.getController();
                upcomingEventController.setEvent(model.getObsFutureEvents().get(i).getEventName());
                upcomingEvents.add(anchorPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        upcomingEventsHBox.getChildren().addAll(upcomingEvents.subList(0, currentVolume));
    }

    private void setupHBoxListener(){
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            int volume = (int) ((newValue.doubleValue()-40-2) / (570)); //-40 padding -2 for scroll bar 550 width 20 spacing
            if (volume != currentVolume) {
                currentVolume = volume;
                if (currentVolume > upcomingEvents.size()) {
                    currentVolume = upcomingEvents.size();
                }
                upcomingEventsHBox.getChildren().setAll(upcomingEvents.subList(0, currentVolume));

            }
            pastEventsTable.resizeColumn(pastNameColumn, newValue.doubleValue() - oldValue.doubleValue());
            upcomingEventsTable.resizeColumn(upcomingNameColumn, newValue.doubleValue() - oldValue.doubleValue());
        });
    }

    public void setNextEvent(String s, int eventID) {
        this.setNextEvent(s, "https://images.unsplash.com/photo-1501281668745-f7f57925c3b4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80", eventID);
    }

    public void setNextEvent(String s, String imageURL, int eventId) {
        nextEventPane.setStyle(nextEventPane.getStyle() + "-fx-background-image: url('" + imageURL + "');");
        nextEventLabel.setText(s);
        if (eventId != 0) {
            nextEventPane.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> openDisplayTicket(eventId));
        }
        nextEventPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            Rectangle clip = new Rectangle(
                    newValue.doubleValue(), nextEventPane.getHeight()
            );
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            nextEventPane.setClip(clip);
        });

        Rectangle clip = new Rectangle(
                nextEventPane.getWidth(), nextEventPane.getHeight()
        );
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        nextEventPane.setClip(clip);
    }

    @FXML
    private void addEventAction(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/add-event-view.fxml"));
            Parent root = fxmlLoader.load();
            AddEventViewController addEventViewController = fxmlLoader.getController();
            Scene scene = new Scene(root, this.stage.getWidth(), this.stage.getHeight());
            stage.setTitle("Add Event");
            stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/calendar-plus.png"))));
            stage.setScene(scene);
            stage.show();
            addEventViewController.initialed(model);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void editEventAction(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/add-event-view.fxml"));
            Parent root = fxmlLoader.load();
            AddEventViewController addEventViewController = fxmlLoader.getController();
            Scene scene = new Scene(root, this.stage.getWidth(), this.stage.getHeight());
            stage.setTitle("Add Event");
            stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/calendar-plus.png"))));
            stage.setScene(scene);
            stage.show();
            addEventViewController.editing(model, upcomingEventsTable.getSelectionModel().getSelectedItem());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void homeAction(ActionEvent actionEvent) {
    }

    @FXML
    private void logOutAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("views/Login.fxml")));
            Parent parent = fxmlLoader.load();
            LoginController loginController = fxmlLoader.getController();
            stage.setScene(new Scene(parent));
            loginController.setStage(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openDisplayTicket(int eventId){
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/display-tickets-view.fxml"));
            Parent root = fxmlLoader.load();
            DisplayTicketsViewController displayTicketsViewController = fxmlLoader.getController();
            Scene scene = new Scene(root, this.stage.getWidth(), this.stage.getHeight());
            stage.setTitle("Tickets");
            stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/calendar-plus.png"))));
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            displayTicketsViewController.setEventId(eventId);
            displayTicketsViewController.initialed(model);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void futureEventsTableClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2 && (upcomingEventsTable.getSelectionModel().getSelectedItem() != null) ) {
            openDisplayTicket(upcomingEventsTable.getSelectionModel().getSelectedItem().getEventID());
        }
    }
}