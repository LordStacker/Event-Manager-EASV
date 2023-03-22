package dk.easv.gui.controllers;

import dk.easv.Main;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
        import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
        import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindowEventPController implements Initializable {

    @FXML
    private MFXScrollPane mainPane;

    @FXML
    private Label nextEventLabel;

    @FXML
    private AnchorPane nextEventPane;

    @FXML
    private TableColumn<?, ?> pastAttendanceColumn;

    @FXML
    private TableColumn<?, ?> pastDateColumn;

    @FXML
    private MFXLegacyTableView<?> pastEventsTable;

    @FXML
    private TableColumn<?, ?> pastNameColumn;

    @FXML
    private TableColumn<?, ?> upcomingAttendanceColumn;

    @FXML
    private TableColumn<?, ?> upcomingDateColumn;

    @FXML
    private HBox upcomingEventsHBox;

    @FXML
    private MFXLegacyTableView<?> upcomingEventsTable;

    @FXML
    private TableColumn<?, ?> upcomingNameColumn;

    private ArrayList<AnchorPane> upcomingEvents = new ArrayList<>();
    private int currentVolume = 2;

    private Stage stage;


    @FXML
    void editEventAction(ActionEvent event) {

    }

    @FXML
    void homeAction(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initialed() {
        stage = (Stage) upcomingEventsHBox.getScene().getWindow();
        setupHBoxListener();
        initEventsHBox();
        setNextEvent("Next Event");
        stage.setMinWidth(1210);
    }

    private void initEventsHBox() {
        for (int i = 0; i < 10; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/upcoming-event-view.fxml"));
            try {
                AnchorPane anchorPane = fxmlLoader.load();
                UpcomingEventController upcomingEventController = fxmlLoader.getController();
                upcomingEventController.setEvent("Event " + i);
                upcomingEvents.add(anchorPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        upcomingEventsHBox.getChildren().addAll(upcomingEvents.subList(0, currentVolume));
    }

    private void setupHBoxListener() {
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            int volume = (int) ((newValue.doubleValue() - 40 - 2) / (upcomingEvents.get(0).getWidth() + 20)); //-40 padding -2 for scroll bar 550 width 20 spacing
            if (volume != currentVolume) {
                currentVolume = volume;
                upcomingEventsHBox.getChildren().setAll(upcomingEvents.subList(0, currentVolume));

            }
            pastEventsTable.resizeColumn(pastNameColumn, newValue.doubleValue() - oldValue.doubleValue());
            upcomingEventsTable.resizeColumn(upcomingNameColumn, newValue.doubleValue() - oldValue.doubleValue());
        });
    }

    public void setNextEvent(String s) {
        this.setNextEvent(s, "https://images.unsplash.com/photo-1501281668745-f7f57925c3b4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80");
    }

    public void setNextEvent(String s, String imageURL) {
        nextEventPane.setStyle(nextEventPane.getStyle() + "-fx-background-image: url('" + imageURL + "');");
        nextEventLabel.setText(s);

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
            Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
            stage.setTitle("Add Event");
            stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/calendar-plus.png"))));
            stage.setScene(scene);
            stage.show();
            addEventViewController.initialed();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void logOutAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("views/Login.fxml")));
            Parent parent = fxmlLoader.load();
            LoginController loginController = fxmlLoader.getController();
            loginController.setStage(stage);
            //TODO clear all existing variables
            //NICOLA
            stage.setScene(new Scene(parent));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStage(Stage oldStage) {
        this.stage = oldStage;
        stage.setMinHeight(mainPane.getPrefHeight());
        stage.setMinWidth(mainPane.getMinWidth());
        stage.setHeight(oldStage.getHeight());
        stage.setWidth(oldStage.getWidth());
    }
}










