package dk.easv.gui.controllers;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
        import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.Label;
        import javafx.scene.control.TableColumn;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowEventPController {

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

    @FXML
    void addEventAction(ActionEvent event) {

    }

    @FXML
    void editEventAction(ActionEvent event) {

    }

    @FXML
    void homeAction(ActionEvent event) {

    }

    @FXML
    void logOutAction(ActionEvent event) {

    }

    












}
