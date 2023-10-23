package main;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ContentList;
import model.ListEntry;
import model.content.Movie;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Watchlist");
        stage.setResizable(false);
        stage.setScene(scene);
        MainController controller = fxmlLoader.getController();
        ContentList temp = new ContentList();
        Movie pulp = new Movie(680);
        //pulp.readRow(6);
        pulp.setImageURL("/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg");
        pulp.createRow();
        temp.getListEntries().add(new ListEntry(pulp, 1));
        temp.getListEntries().add(new ListEntry(new Movie(1210), 2));
        controller.setCurrentList(temp);
        controller.setPoster(pulp.getImage() );

        System.out.println("Tv-Movie-Watchlist");
        //Movie.searchTMDB("Pulp Fiction");
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}