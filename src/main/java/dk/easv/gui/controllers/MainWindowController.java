package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.be.Event;
import dk.easv.bll.helpers.ViewType;
import dk.easv.gui.controllers.abstractController.RootController;
import dk.easv.gui.controllers.controllerFactory.ControllerFactory;
import dk.easv.be.Roles;
import dk.easv.be.User;
import dk.easv.gui.models.EventModel;
import dk.easv.gui.models.UserModel;
import dk.easv.util.AlertHelper;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindowController extends RootController implements Initializable {
    final ControllerFactory controllerFactory = new ControllerFactory();
    @FXML
    private HBox upcomingEventsHBox;
    @FXML
    private AnchorPane nextEventPane;

    private final ArrayList<AnchorPane> upcomingEvents = new ArrayList<>();
    private int currentVolume;

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

    @FXML
    private HBox viewRole;

    private final EventModel model = new EventModel();

    private final UserModel userModel = new UserModel();

    private ObservableList<User> userPlanners;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTables();

        // check for tmp folder
        Path path = Path.of("src/main/resources/dk/easv/tmp");
        if (!Files.exists(path)){
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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

    public void initialed(Stage stage, int stageWidth, int stageHeight, User user) {
        this.stage = stage;
        setupHBoxListener();
        renderConditionalView(user);
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
        if (model.getObsFutureEvents().size() > 1) {
            for (int i = 1; i < model.getObsFutureEvents().size(); i++) {
                try {
                    UpcomingEventController controller = (UpcomingEventController) controllerFactory.loadFxmlFile(ViewType.UPCOMING_EVENT);
                    AnchorPane anchorPane = (AnchorPane) controller.getView();
                    controller.setEvent(model.getObsFutureEvents().get(i).getEventName());
                    upcomingEvents.add(anchorPane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            upcomingEvents.add(new AnchorPane(new Label("No upcoming events")));
        }
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
        stage.setWidth(stage.getWidth() + 1);
    }

    public void setNextEvent(String s, int eventID) {
        this.setNextEvent(s, "https://images.unsplash.com/photo-1501281668745-f7f57925c3b4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80", eventID);
    }

    public void renderConditionalView(User user){
        if(user != null){
            if(user.role() == Roles.ADMIN){
                Button button = new MFXButton("Manage Users");
                viewRole.getChildren().add(button);
                button.minWidth(26.0);
                button.minHeight(108.0);
                button.setOnAction(event -> {
                    userPlanners = userModel.usersPlanners(Roles.EVENT_COORDINATOR);
                    //TODO Refactor @Patrik Factory manegement
                    try {
                        Stage stage = new Stage();
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/manage-users-view.fxml"));
                        Parent root = fxmlLoader.load();
                        manageUsersController manageUsersController = fxmlLoader.getController();
                        Scene scene = new Scene(root, this.stage.getWidth(), this.stage.getHeight());
                        stage.setTitle("Manage Users");
                        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/calendar-plus.png"))));
                        stage.setScene(scene);
                        stage.show();
                        manageUsersController.initialed(userPlanners);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                });
            }
        }

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
            addEventLoader().initialed(model);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void editEventAction(ActionEvent actionEvent) {
        try {
            addEventLoader().editing(model, upcomingEventsTable.getSelectionModel().getSelectedItem());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private AddEventViewController addEventLoader() throws IOException {
        Stage stage = new Stage();
        AddEventViewController addEventViewController = (AddEventViewController) controllerFactory.loadFxmlFile(ViewType.ADD_EVENT);
        Scene scene = new Scene(addEventViewController.getView(), this.stage.getWidth(), this.stage.getHeight());
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/calendar-plus.png"))));
        stage.setScene(scene);
        stage.show();
        return addEventViewController;
    }

    @FXML
    private void homeAction(ActionEvent actionEvent) {
    }

    @FXML
    private void logOutAction(ActionEvent actionEvent) {
        try {
            LoginController loginController = (LoginController) controllerFactory.loadFxmlFile(ViewType.LOGIN);
            stage.setScene(new Scene(loginController.getView()));
            loginController.setStage(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openDisplayTicket(int eventId){
        try {
            Stage stage = new Stage();
            DisplayTicketsViewController displayTicketsViewController = (DisplayTicketsViewController) controllerFactory.loadFxmlFile(ViewType.DISPLAY_TICKETS);
            Scene scene = new Scene(displayTicketsViewController.getView(), this.stage.getWidth(), this.stage.getHeight());
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