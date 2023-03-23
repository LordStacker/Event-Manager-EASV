package dk.easv.util;

import dk.easv.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;

import java.util.Objects;

public class AlertHelper {
    private static Alert alert;

    public static void showDefaultAlert(String content, Alert.AlertType type) {
        alert = new Alert(type);
        alert.setAlertType(type);
        alert.setContentText(content);
        alert.getDialogPane().getChildren()
                .stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> ((Label) node)
                        .setMinHeight(Region.USE_PREF_SIZE));
        alert.setResizable(false);
        alert.getDialogPane().setMaxWidth(350);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(Main.class.getResource("css/Alert.css")).toExternalForm());
        alert.getDialogPane().getStyleClass().add("dialog-alert-style");
        alert.show();
    }
}
