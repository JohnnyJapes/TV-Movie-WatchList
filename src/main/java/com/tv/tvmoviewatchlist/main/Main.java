package com.tv.tvmoviewatchlist.main;

import com.tv.tvmoviewatchlist.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.tv.tvmoviewatchlist.model.ContentList;
import com.tv.tvmoviewatchlist.model.Tables;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Class x = this.getClass();
        System.out.println(x.getCanonicalName());
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/main-view.fxml"));
        //check that database exists before loading the fxml.
        if(checkVersion()){
            Tables.refreshDatabase();
        };
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("TV-Movie Watchlists");
        stage.setResizable(false);
        stage.setScene(scene);
        MainController controller = fxmlLoader.getController();
        ContentList current = new ContentList();

        //ContentList temp = new ContentList();
        //new main.RefreshDatabase();




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
     * checks current version. returns true if not on current version or database was missing
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

        //check local db exists
        path = "local.db";

        ver = new File(path);
        if (ver.createNewFile()) {
            result = true;
            System.out.println("LOCAL CHECK: " + result);
        }

        return result;
    }
}