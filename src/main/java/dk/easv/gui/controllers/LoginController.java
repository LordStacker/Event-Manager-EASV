package dk.easv.gui.controllers;

import dk.easv.be.User;
import dk.easv.bll.helpers.ViewType;
import dk.easv.gui.controllers.abstractController.RootController;
import dk.easv.gui.controllers.controllerFactory.ControllerFactory;
import dk.easv.gui.models.UserModel;
import dk.easv.util.AlertHelper;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController extends RootController implements Initializable {

    @FXML
    private MFXTextField usernameTextField;
    @FXML
    private MFXPasswordField passwordTextField;
    @FXML
    private GridPane loginGrid;
    private Stage stage;

    private List<User> user;

    private final UserModel userModel = new UserModel();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void loginButton(ActionEvent actionEvent) throws IOException {
        final ControllerFactory controllerFactory = new ControllerFactory();
        if (usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
            AlertHelper.showDefaultAlert("Fill the next fields "+ (usernameTextField.getText().isEmpty() ? "username" : passwordTextField.getText().isEmpty() ? "password" : " "), Alert.AlertType.WARNING );
        }
        if (usernameTextField.getText() != null && passwordTextField.getText() !=null)
        {
            user = userModel.checkUserLog(usernameTextField.getText(), passwordTextField.getText());
        }
        if(user.size() >= 1){
            if(user.get(0).role() != null){
                /*
                 * Leaving like only Role while we define the view for each Role
                 */
                MainWindowController controller = (MainWindowController) controllerFactory.loadFxmlFile(ViewType.MAIN);
                stage.setScene(new Scene(controller.getView()));
                int stageWidth = (int) stage.getWidth();
                int stageHeight = (int) stage.getHeight();
                stage.centerOnScreen();
                stage.close();
                stage.show();
                controller.initialed(stage, stageWidth, stageHeight);
            }
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;

        stage.setHeight(loginGrid.getPrefHeight()+30); // +30 because of the title bar and window border
        stage.setWidth(loginGrid.getPrefWidth()+1);
        stage.setMinWidth(loginGrid.getPrefWidth());
        stage.setMinHeight(loginGrid.getPrefHeight() + 30); // +30 because of the title bar and window border

    }

}
