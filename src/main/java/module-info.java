module dk.easv.eventticketseasvbar {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;
    requires com.microsoft.sqlserver.jdbc;
    requires MaterialFX;
    requires qrgen;
    requires org.apache.pdfbox;
    requires org.apache.commons.io;


    opens dk.easv to javafx.fxml;
    exports dk.easv;
    exports dk.easv.gui.models;
    exports dk.easv.be;
    exports dk.easv.gui.controllers;
    opens dk.easv.gui.controllers to javafx.fxml;
}