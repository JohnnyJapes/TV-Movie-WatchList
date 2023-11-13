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
import model.content.ContentBase;
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
        ContentBase selected =  currentList.getSelectionModel().getSelectedItem().getEntry();
        if (selected.getContentType() == 1)
            directorLabel.setText(selected.getTopCrew().get(0).getName());
        else{
            String creators = "";
            for (int i = 0; i < selected.getTopCrew().size(); i++){
                if ( selected.getTopCrew().size() - i == 1){
                    creators += selected.getTopCrew().get(i);
                }
                else  creators += selected.getTopCrew().get(i) + ", ";
            }
            directorLabel.setText(creators);
        }
        titleLabel.setText(selected.getTitle());
        setPoster(selected.getImage());

        yearLabel.setText(Integer.toString(selected.getReleaseDate().getYear()));
        overviewLabel.setText(selected.getOverview());
        characterLabel.setText(selected.getCast().get(0).getCharacter());
        actorLabel.setText(selected.getCast().get(0).getPerson().getName());
        OkHttpClient client = new OkHttpClient();
        System.out.println("ID PIC: " + selected.getCast().get(0).getPerson().getID());
        Image image = new Image(selected.getCast().get(0).getPerson().getImage());
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
            NewItemController controller = fxmlLoader.getController();
            controller.setCurrentList(0);       //TODO: make application read from GUI which list is currently selected
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("New Item");
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setOnCloseRequest((event) -> {
                readContentTable();

            });
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
    @FXML
    public void editButtonClicked() throws IOException{
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/edit-item.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Item");
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

    public void readContentTable(){
        ContentList list = new ContentList();
        list.readWatchingList();
        setCurrentList(list);
        return;
    }

}
