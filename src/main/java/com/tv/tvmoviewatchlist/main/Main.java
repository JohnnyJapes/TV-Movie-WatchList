package com.tv.tvmoviewatchlist.main;

import com.tv.tvmoviewatchlist.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import com.tv.tvmoviewatchlist.model.ContentList;
import com.tv.tvmoviewatchlist.model.Tables;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
        //check for properties
        if (!getPropValues()){
            Alert alert = new Alert(AlertType.ERROR, "Please create a config.properties following the template");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    System.exit(0);
                }
            });
        }
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

    /**
     * Loads properties from config.proprties. Returns false if properties are not found.
     * @return Boolean
     * @throws IOException
     */
    public Boolean getPropValues() throws IOException {
        Boolean result = true;
        InputStream inputStream = null;
            try {
                Properties props = new Properties();
                String propFileName = "config.properties";
    
                inputStream = new FileInputStream(propFileName);
    
                if (inputStream != null) {
                    props.load(inputStream);
                } else {
                    result = false;
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
                }    
                //set properties to system so they are accessible
                System.setProperty("dburl",props.getProperty("dburl"));
                System.setProperty("token",props.getProperty("token"));
            } catch (Exception e) {
                System.out.println("Exception: " + e);
                result = false;

            } finally {
                inputStream.close();
            }
            return result;
    }
}