package dk.easv;


import dk.easv.bll.helpers.ViewType;
import dk.easv.gui.controllers.LoginController;
import dk.easv.gui.controllers.controllerFactory.ControllerFactory;
import dk.easv.gui.controllers.controllerFactory.IControllerFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        final IControllerFactory controllerFactory = new ControllerFactory();
        // checks if config exists
        Path path = Path.of("src/main/resources/dk/easv/config.cfg");
        if (!Files.exists(path)) {
            System.err.println("""
                    Config file not found
                    Please create a config file in the following path:
                    src/main/resources/dk/easv/config.cfg
                    """);
            System.exit(1);
        }

        LoginController loginController = (LoginController) controllerFactory.loadFxmlFile(ViewType.LOGIN);
        loginController.setStage(stage);
        Scene scene = new Scene(loginController.getView());
        stage.setTitle("EASV Event Manager");
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/calendar.png"))));
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop(){
        System.out.println("Closing application");
        Path file = Path.of("src/main/resources/dk/easv/tmp");
        if (Files.exists(file)) {
            try {
                Files.delete(file);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}