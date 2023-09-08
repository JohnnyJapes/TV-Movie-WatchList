module com.tv.tvmoviewatchlist {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens model.tv to javafx.fxml;
    exports model.tv;
    exports controller;
    opens controller to javafx.fxml;
}