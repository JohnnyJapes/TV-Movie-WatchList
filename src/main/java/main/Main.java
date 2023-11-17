package main;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ContentList;
import model.Tables;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("TV-Movie Watchlists");
        stage.setResizable(false);
        stage.setScene(scene);
        MainController controller = fxmlLoader.getController();
        ContentList current = new ContentList();

        //ContentList temp = new ContentList();
        //new RefreshDatabase();

        if(!checkVersion()){
            Tables.refreshDatabase();
        };


        //default list when opening is watching
        current.readList(0);

        controller.setCurrentList(current);
        //controller.setPoster(pulp.getImage() );
        //controller.setTitle(pulp.getTitle());

        System.out.println("Tv-Movie-Watchlist");
        //Movie.searchTMDB("Pulp Fiction");
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * checks current version. returns true if on current version
     * @return
     * @throws IOException
     */
    private boolean checkVersion() throws IOException {
        String path = "version/";

        File ver = new File(path);
        ver.mkdirs();
        path += "3.txt";
        ver = new File(path);

        Boolean result = ver.createNewFile();

        return !result;
    }
}