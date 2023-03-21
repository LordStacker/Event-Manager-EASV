package dk.easv;

import dk.easv.gui.controllers.AddEventViewController;
import dk.easv.gui.controllers.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/add-event-view.fxml"));
        Parent root = fxmlLoader.load();
        AddEventViewController mainWindowController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        stage.setTitle("EASV Event Manager");
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/calendar.png"))));
        stage.setScene(scene);
        stage.show();
        mainWindowController.initialed();
    }

    public static void main(String[] args) {
        launch();
    }
}