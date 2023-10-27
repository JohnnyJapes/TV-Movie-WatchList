package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.HelloApplication;
import model.content.ContentBase;
import model.content.Movie;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class EditItemController {


    @FXML
    ImageView tmdbImage;
    @FXML
    TextField title, director;
    @FXML
    DatePicker date;
    @FXML
    TextArea overview;
    @FXML
    RadioButton movieRadio, tvRadio;

    final ToggleGroup group = new ToggleGroup();

/*    public NewItemController(){
        movieRadio.setToggleGroup(group);
        movieRadio.setSelected(true);
        tvRadio.setToggleGroup(group);
    }*/
    /**
     * Method sets up TMDB image to comply with attribution
     *
     */
    public void setTmdbImage() {
        this.tmdbImage =new ImageView();
        try {
            FileInputStream input = new FileInputStream("src/main/resources/attribution/shortTMDB.svg");
            Image image = new Image(input);
            tmdbImage.setImage(image);


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void clickTMDBsearch(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/search-results.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            SearchResultsController controller = fxmlLoader.getController();
            controller.addList((ArrayList<ContentBase>)new Movie().searchTMDB(title.getText()));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Search Results");
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
