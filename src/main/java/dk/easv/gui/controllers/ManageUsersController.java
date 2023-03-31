package dk.easv.gui.controllers;

import dk.easv.be.User;
import dk.easv.gui.controllers.abstractController.RootController;
import dk.easv.gui.models.UserModel;
import dk.easv.util.AlertHelper;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageUsersController extends RootController implements Initializable {
    @FXML
    private MFXLegacyTableView<User> usersTableView;
    @FXML
    private TableColumn<User, String> username, roles, email, actionsUser;

    @FXML
    private MFXButton cancelButton, createUser;

    @FXML
    private BorderPane borderPane;

    private Stage stage;

    private final UserModel model = new UserModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initialed(ObservableList<User> userPlanners){
        this.stage = (Stage) cancelButton.getScene().getWindow();
        stage.setMinWidth(600);
        stage.setMinHeight(450);
        populateTable(userPlanners);
        cancelButton.setOnAction(e -> cancelButtonClicked());
    }

    private void cancelButtonClicked() {
        stage.close();
    }


    public void populateTable(ObservableList<User> userPlanners){
        username.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().userName()));
        roles.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().role())));
        email.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().userEmail()));
        actionsUser.setCellValueFactory(cellData -> {
            MFXButton deleteButton = new MFXButton("Delete");
            deleteButton.setOnAction(event -> {
                var alert = AlertHelper.showOptionalAlertWindow("Sure to delete the next user? ", cellData.getValue().userName(), Alert.AlertType.CONFIRMATION);
                if(alert.isPresent() && alert.get().equals(ButtonType.OK)){
                    model.deleteUser(cellData.getValue().userID());
                }
            });
            return new SimpleObjectProperty(deleteButton);
        });
        usersTableView.setItems(userPlanners);
    }
}
