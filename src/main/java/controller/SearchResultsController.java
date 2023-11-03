package controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.ListEntry;
import model.content.ContentBase;
import model.content.Movie;

import java.util.ArrayList;

public class SearchResultsController {

    public SearchResultsController(){

    }
    @FXML
    ListView<ContentBase> searchList;
    @FXML
    Button addButton;

    @FXML
    public void onClickSearchList(){

    }
    @FXML
    public void addSelectedItem(){

    }
    public void addList(ArrayList<ContentBase> contentList){
            searchList.getItems().addAll(contentList);

    }

    /**
     * Returns the currently selected item.
     * @return
     */
    public ContentBase getSelected(){
        return searchList.getSelectionModel().getSelectedItem();
    }

    /**
     * Gets addButton.
     *
     * @return javafx.scene.control.Button, value of addButton
     */
    public Button getAddButton() {
        return addButton;
    }
    public void closeWindow(){
        
    }
}
