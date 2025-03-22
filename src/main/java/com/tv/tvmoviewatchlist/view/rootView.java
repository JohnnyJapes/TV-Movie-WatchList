package com.tv.tvmoviewatchlist.view;

import com.tv.tvmoviewatchlist.main.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class rootView extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane rootLayout = new BorderPane();

        VBox listPane = new VBox(5);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello World!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
