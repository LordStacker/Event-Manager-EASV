package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.be.User;
import dk.easv.gui.models.UserModel;
import dk.easv.util.AlertHelper;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class manageUsersController implements Initializable {
    @FXML
    private MFXLegacyTableView usersTableView;
    @FXML
    private TableColumn<User, String> username, roles, email, actionsUser;

    @FXML
    private MFXButton cancelButton, createUser;

    @FXML
    private BorderPane borderPane;

    private Stage stage;

    private UserModel model = new UserModel();

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
        username.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().userName().toString()));
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
    @FXML
    public void openUserCreation(ActionEvent actionEvent) {
        try{
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/addCustomerView.fxml"));
            Parent root = fxmlLoader.load();
            AddCustomerViewController controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            stage.setTitle("Create Customer");
            stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/calendar-plus.png"))));
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
            controller.initialed();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openCoordinatorCreation(ActionEvent actionEvent) {
        System.out.println("here coordinator creation");
    }
}
