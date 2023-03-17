package dk.easv.gui.controllers;

import dk.easv.Main;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    private HBox upcomingEventsHBox;
    @FXML
    private AnchorPane nextEventPane;

    private ArrayList<AnchorPane> upcomingEvents = new ArrayList<>();
    private int currentVolume = 2;

    private Stage stage;
    @FXML
    private MFXScrollPane mainPane;
    @FXML
    private TableColumn upcomingNameColumn, upcomingDateColumn, upcomingAttendanceColumn;
    @FXML
    private TableColumn pastNameColumn, pastDateColumn, pastAttendanceColumn;
    @FXML
    private MFXLegacyTableView upcomingEventsTable;
    @FXML
    private MFXLegacyTableView pastEventsTable;
    @FXML
    private Label nextEventLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initEventsHBox();

        setNextEvent("Next Event");
    }

    public void initialed() {
        stage = (Stage) upcomingEventsHBox.getScene().getWindow();
        setupHBoxListener();
        stage.setMinWidth(1195);
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

    private void setupHBoxListener(){
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            int volume = (int) (newValue.doubleValue()-40-2) / (550 + 20); //-40 padding 550 width 20 spacing -10 for scroll bar
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
    }
}