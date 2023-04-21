package dk.easv.gui.controllers;
import dk.easv.be.Event;
import dk.easv.be.TicketType;
import dk.easv.gui.controllers.abstractController.RootController;
import dk.easv.util.AlertHelper;
import dk.easv.gui.models.EventModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.enums.FloatMode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URL;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

public class AddEventViewController extends RootController implements Initializable {
    @FXML
    private MFXButton submitButton;
    @FXML
    private MFXButton cancelButton;
    @FXML
    private MFXTextField eventExtraNotesField;
    @FXML
    private MFXTextField eventDirectionsField;
    @FXML
    private MFXDatePicker endDatePicker;
    @FXML
    private MFXDatePicker startDatePicker;
    @FXML
    private MFXTextField eventStartField;
    @FXML
    private MFXTextField eventEndField;
    @FXML
    private MFXTextField eventLocationField;
    @FXML
    private MFXTextField eventNameField;
    private EventModel model;
    private Stage stage;
    @FXML
    private VBox ticketTypesVBox;
    @FXML
    private MFXButton addTicketButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        submitButton.setOnAction(e -> submitButtonClicked());
        cancelButton.setOnAction(e -> cancelButtonClicked());
    }

    private void cancelButtonClicked() {
        stage.close();
    }

    private void submitButtonClicked() {
        if (isInputValid()){
            int eventID = model.addEvent(eventNameField.getText(), eventLocationField.getText(), startDatePicker.getValue(), endDatePicker.getValue(),
                    eventDirectionsField.getText(), eventExtraNotesField.getText(), eventStartField.getText(), eventEndField.getText());

            // Loop through all the ticket types
            for (int i = 0; i < ticketTypesVBox.getChildren().size(); i++) {
                HBox hBox = (HBox) ticketTypesVBox.getChildren().get(i);
                MFXTextField ticketNameField = (MFXTextField) hBox.getChildren().get(0);
                MFXTextField ticketPriceField = (MFXTextField) hBox.getChildren().get(1);
                MFXTextField ticketAmountTextField = (MFXTextField) hBox.getChildren().get(2);
                model.addTickets(eventID, ticketNameField.getText(), Double.parseDouble(ticketPriceField.getText()), Integer.parseInt(ticketAmountTextField.getText()));
            }

            model.getAllEvents();
            stage.close();
        }

    }

    public void initialed(EventModel model){
        this.model = model;
        this.stage = (Stage) submitButton.getScene().getWindow();
        stage.setMinWidth(600);
        stage.setMinHeight(450);
        stage.setTitle("Add event");
    }

    public boolean isInputValid(){

        if (eventNameField == null || eventNameField.getText().trim().isEmpty()){
            AlertHelper.showDefaultAlert("Please enter event name before submitting!", Alert.AlertType.WARNING);
        } else if (eventLocationField == null ||eventLocationField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter event location before submitting!", Alert.AlertType.WARNING);
        } else if (startDatePicker.getValue() == null) {
            AlertHelper.showDefaultAlert("Please enter event start date before submitting!", Alert.AlertType.WARNING);
        } else if (eventStartField == null || eventStartField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter event start hour before submitting!", Alert.AlertType.WARNING);
        } else if (eventDirectionsField == null || eventDirectionsField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter event directions before submitting!", Alert.AlertType.WARNING);
        } else if (eventExtraNotesField == null || eventExtraNotesField.getText().trim().isEmpty()) {
            AlertHelper.showDefaultAlert("Please enter extra notes about the event before submitting!", Alert.AlertType.WARNING);
        } else {
            for (int i = 0; i < ticketTypesVBox.getChildren().size(); i++) {
                HBox hBox = (HBox) ticketTypesVBox.getChildren().get(i);
                MFXTextField ticketNameField = (MFXTextField) hBox.getChildren().get(0);
                MFXTextField ticketPriceField = (MFXTextField) hBox.getChildren().get(1);
                MFXTextField ticketAmountTextField = (MFXTextField) hBox.getChildren().get(2);
                if (ticketNameField == null || ticketNameField.getText().trim().isEmpty()) {
                    AlertHelper.showDefaultAlert("Please enter ticket name before submitting!", Alert.AlertType.WARNING);
                    return false;
                } else if (ticketPriceField == null || ticketPriceField.getText().trim().isEmpty()) {
                    AlertHelper.showDefaultAlert("Please enter ticket price before submitting!", Alert.AlertType.WARNING);
                    return false;
                } else if (ticketAmountTextField == null || ticketAmountTextField.getText().trim().isEmpty()) {
                    AlertHelper.showDefaultAlert("Please enter ticket amount before submitting!", Alert.AlertType.WARNING);
                    return false;
                }
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(eventStartField.getText());
            } catch (ParseException e) {
                AlertHelper.showDefaultAlert("Please follow the format instructions when setting time of event!", Alert.AlertType.WARNING);
                return false;
            }
            return true;
        }
        return false;
    }

    @FXML
    private void addTicketAction(ActionEvent actionEvent) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        MFXTextField ticketNameField = new MFXTextField();
        ticketNameField.setPromptText("Ticket name");
        ticketNameField.setPrefWidth(200);
        ticketNameField.setFloatMode(FloatMode.BORDER);
        MFXTextField ticketPriceField = new MFXTextField();
        ticketPriceField.setPromptText("Ticket price");
        ticketPriceField.setPrefWidth(50);
        ticketPriceField.setFloatMode(FloatMode.BORDER);
        MFXTextField ticketAmountTextField = new MFXTextField();
        ticketAmountTextField.setPromptText("Ticket amount");
        ticketAmountTextField.setPrefWidth(80);
        ticketAmountTextField.setFloatMode(FloatMode.BORDER);
        MFXButton removeButton = new MFXButton("\uD83D\uDDD1");
        removeButton.setOnAction(e -> ticketTypesVBox.getChildren().remove(hBox));
        hBox.getChildren().addAll(ticketNameField, ticketPriceField, ticketAmountTextField, removeButton);
        ticketTypesVBox.getChildren().add(hBox);
    }

    public void editing(EventModel model, Event selectedItem) {
        this.model = model;
        this.stage = (Stage) submitButton.getScene().getWindow();
        stage.setMinWidth(600);
        stage.setMinHeight(450);

        eventNameField.setText(selectedItem.getEventName());
        eventLocationField.setText(selectedItem.getEventLocation());
        startDatePicker.setValue(selectedItem.getEventStartDate().toLocalDate());
        if (selectedItem.getEventEndDate() != null) {
            endDatePicker.setValue(selectedItem.getEventEndDate().toLocalDate());
            eventEndField.setText(selectedItem.getEventEndDate().toLocalTime().toString());
        }
        eventStartField.setText(selectedItem.getEventStartDate().toLocalTime().toString());
        eventDirectionsField.setText(selectedItem.getEventGuidance());
        eventExtraNotesField.setText(selectedItem.getEventNotes());
        createTicketTypesFields(model.getTicketTypes(selectedItem.getEventID()));
        submitButton.setOnAction(e -> editButtonClicked(selectedItem));
        submitButton.setText("Edit");
        addTicketButton.setVisible(false);
        stage.setTitle("Edit event");
    }

    private void createTicketTypesFields(List<TicketType> ticketTypes) {
        for (TicketType type : ticketTypes) {
            addTicketAction(null);
            HBox hBox = (HBox) ticketTypesVBox.getChildren().get(ticketTypesVBox.getChildren().size() - 1);
            MFXTextField ticketNameField = (MFXTextField) hBox.getChildren().get(0);
            MFXTextField ticketPriceField = (MFXTextField) hBox.getChildren().get(1);
            MFXTextField ticketAmountTextField = (MFXTextField) hBox.getChildren().get(2);
            ticketNameField.setText(type.getName());
            ticketPriceField.setText(String.valueOf(type.getPrice()));
            ticketAmountTextField.setText(String.valueOf(type.getTicketVolume()));
            ticketNameField.setEditable(false);
            ticketPriceField.setEditable(false);
            ticketAmountTextField.setEditable(false);
            MFXButton deleteButton = (MFXButton) hBox.getChildren().get(3);
            deleteButton.setVisible(false);
        }
    }

    private void editButtonClicked(Event selectedItem) {
        model.editEvent(selectedItem.getEventID(), eventNameField.getText(), eventLocationField.getText(), startDatePicker.getValue(), endDatePicker.getValue(),
                eventDirectionsField.getText(), eventExtraNotesField.getText(), eventStartField.getText(), eventEndField.getText());
        model.getAllEvents();
        stage.close();
    }
}
