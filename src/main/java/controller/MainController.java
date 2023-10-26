package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.HelloApplication;
import model.ContentList;
import model.ListEntry;
import model.content.Movie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;

public class MainController {


    @FXML
    ListView<ListEntry> currentList;
    @FXML
    ImageView poster;
    @FXML
    Label titleLabel, directorLabel, yearLabel, characterLabel, actorLabel, overviewLabel;
    @FXML
    ImageView castImage1;
    @FXML
    public void getSelectedItem(){
        Movie selected =  (Movie) currentList.getSelectionModel().getSelectedItem().getEntry();
        titleLabel.setText(selected.getTitle());
        setPoster(selected.getImage());
        directorLabel.setText(selected.getDirector().getName());
        yearLabel.setText(Integer.toString(selected.getReleaseDate().getYear()));
        overviewLabel.setText(selected.getOverview());
        characterLabel.setText(selected.getCast().get(0).getCharacter());
        actorLabel.setText(selected.getCast().get(0).getPerson().getName());
        OkHttpClient client = new OkHttpClient();
        Image image = new Image(selected.getCast().get(0).getPerson().getImage());
        castImage1 = new ImageView();
        castImage1.setImage(image);






    }

    public void setCurrentList(ContentList list){
        for (ListEntry ent : list.getListEntries()){
            currentList.getItems().add(ent);
        }

    }
    public void setPoster(FileInputStream input){
        Image image = new Image(input);
        //poster.setPreserveRatio(true);
        poster.setImage(image);

    }
    public void setTitle(String str){
        titleLabel.setText(str);
    }

    public void setDetailView(ListEntry entry){

    }

   @FXML
   public void openNewItem() throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/new-item.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("TV-Movie Watchlists");
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
    @FXML
    public void editButtonClicked() throws IOException{
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/new-item.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("TV-Movie Watchlists");
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    @FXML
    public void deleteButtonClicked() throws IOException{

    }

}
