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
import model.ListEntry;
import model.content.ContentBase;
import model.content.Movie;
import model.content.TV;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class NewItemController {


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


    int currentList;

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
            ToggleGroup contentGroup = movieRadio.getToggleGroup();
            RadioButton selectedRadio = (RadioButton) contentGroup.getSelectedToggle();
            if(selectedRadio.getText() == "Movie")
                controller.addList((ArrayList<ContentBase>)new Movie().searchTMDB(title.getText()));
            else{
                controller.addList((ArrayList<ContentBase>)new TV().searchTMDB(title.getText()));
            }
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Search Results");
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
            controller.getAddButton().setOnAction(event -> {
                ContentBase selected = controller.getSelected();
                if (selected.getContentType()  == 1) {
                    Movie movie = (Movie) selected;
                    stage.close();
                    movie.addFromSearch();
                    ListEntry entry = new ListEntry(movie, currentList);
                    entry.createRow();
                }
                else if (selected.getContentType() == 2) {
                    TV tv = (TV) selected;
                    stage.close();
                    tv.addFromSearch();
                    ListEntry entry = new ListEntry(tv, currentList);
                    tv.createRow();
                }

            });
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Gets currentList.
     *
     * @return int, value of currentList
     */
    public int getCurrentList() {
        return currentList;
    }

    /**
     * Method to set currentList.
     *
     * @param currentList int - currentList
     */
    public void setCurrentList(int currentList) {
        this.currentList = currentList;
    }
}
