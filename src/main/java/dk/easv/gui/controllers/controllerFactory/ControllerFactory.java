package dk.easv.gui.controllers.controllerFactory;

import dk.easv.Main;
import dk.easv.bll.helpers.ViewType;
import dk.easv.gui.controllers.abstractController.RootController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * controller factory class that is responsible only for injecting the google guice injector
 * when instantiated and include one method that binds the injector for
 * loader each time it is required to be loaded
 */
public class ControllerFactory implements IControllerFactory {
    @Override
    public RootController loadFxmlFile(ViewType fxmlFile) throws IOException {
        Objects.requireNonNull(fxmlFile, "fxmlFile must not be null.");

        final URL fxmlFileUrl = Main.class.getResource(fxmlFile.getFXMLView());

        final FXMLLoader loader = new FXMLLoader(fxmlFileUrl);

        final Parent view = loader.load();
        final RootController controller = loader.getController();
        controller.setView(view);

        return controller;
    }
}
