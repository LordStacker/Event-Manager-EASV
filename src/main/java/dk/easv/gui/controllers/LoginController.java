package dk.easv.gui.controllers;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.Main;
import dk.easv.be.Roles;
import dk.easv.dal.dao.UserDAO;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import dk.easv.be.User;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private MFXTextField usernameTextField;
    @FXML
    private MFXPasswordField passwordTextField;
    @FXML
    private GridPane loginGrid;
    private Stage stage;

    private List<User> user;

    private UserDAO userDAO = new UserDAO();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void loginButton(ActionEvent actionEvent) throws SQLServerException, IOException {
        if (usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
            System.out.println("Alert here");
        }
        if (usernameTextField.getText() != null && passwordTextField.getText() !=null)
        {
            user = UserDAO.checkUserLog(usernameTextField.getText(), passwordTextField.getText());
            System.out.println(user); //Checking logic if works for exist or not user in DB
        }
        if(user.size() >= 1){
            if(user.get(0).role() != null){
                /**
                 * Leaving like only Role while we define the view for each Role
                 */
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("views/main-view.fxml")));
                Parent newScene = loader.load();
                MainWindowController controller = loader.getController();
                //controller.setStage(stage);
                stage.setScene(new Scene(newScene));
                stage.centerOnScreen();
                controller.initialed();
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
