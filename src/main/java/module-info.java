module dk.easv.eventticketseasvbar {
    requires javafx.controls;
    requires javafx.fxml;


    opens dk.easv to javafx.fxml;
    exports dk.easv;
    exports dk.easv.gui.controllers;
    opens dk.easv.gui.controllers to javafx.fxml;
}