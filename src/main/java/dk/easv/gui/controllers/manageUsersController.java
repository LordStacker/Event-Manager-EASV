package dk.easv.gui.controllers;

import dk.easv.be.User;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class manageUsersController implements Initializable {
    @FXML
    private MFXLegacyTableView usersTableView;
    @FXML
    private TableColumn<User, String> username, roles, email;

    @FXML
    private MFXButton cancelButton;

    @FXML
    private BorderPane borderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
