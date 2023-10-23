package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.ContentList;
import model.ListEntry;

import java.io.FileInputStream;

public class MainController {


    @FXML
    ListView<ListEntry> currentList;
    @FXML
    ImageView poster;

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
}
