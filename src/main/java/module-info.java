module com.tv.tvmoviewatchlist {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires org.apache.commons.text;
    requires com.fasterxml.jackson.databind;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens model.content to javafx.fxml;
    exports model.content;
    exports controller;
    opens controller to javafx.fxml;
    exports model;
    opens model to javafx.fxml;
    exports model.Person;
    opens model.Person to javafx.fxml;
    exports main;
    opens main to javafx.fxml;
}