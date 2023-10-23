package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.ContentList;
import model.ListEntry;

public class MainController {


    @FXML
    ListView<ListEntry> currentList;

    public void setCurrentList(ContentList list){
        for (ListEntry ent : list.getListEntries()){
            currentList.getItems().add(ent);
        }

    }
}
