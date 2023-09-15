module com.tv.tvmoviewatchlist {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens model.content to javafx.fxml;
    exports model.content;
    exports controller;
    opens controller to javafx.fxml;
    exports model;
    opens model to javafx.fxml;
    exports model.Person;
    opens model.Person to javafx.fxml;
}