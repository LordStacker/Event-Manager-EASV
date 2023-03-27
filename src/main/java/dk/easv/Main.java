package dk.easv;


import dk.easv.gui.controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/Login.fxml"));
        Parent root = fxmlLoader.load();
        LoginController loginController = fxmlLoader.getController();
        loginController.setStage(stage);
        Scene scene = new Scene(root);
        stage.setTitle("EASV Event Manager");
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/calendar.png"))));
        stage.setScene(scene);
        stage.show();

        Path path = Path.of("src/main/resources/dk/easv/tmp");
        if (!Files.exists(path)){
            Files.createDirectory(path);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}