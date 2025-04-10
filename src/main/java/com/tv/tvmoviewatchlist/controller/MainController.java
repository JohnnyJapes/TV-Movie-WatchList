package com.tv.tvmoviewatchlist.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.tv.tvmoviewatchlist.main.Main;
import com.tv.tvmoviewatchlist.model.ContentList;
import com.tv.tvmoviewatchlist.model.ListEntry;
import com.tv.tvmoviewatchlist.model.connectors.CastMember;
import com.tv.tvmoviewatchlist.model.content.ContentBase;
import okhttp3.OkHttpClient;

import java.io.*;
import java.util.ArrayList;

public class MainController {


    @FXML
    ListView<ListEntry> currentList;
    @FXML
    HBox castList;
    @FXML
    VBox detailView;
    @FXML
    ImageView poster;
    @FXML
    Label titleLabel, directorLabel, yearLabel, characterLabel, actorLabel, overviewLabel, changingLabel;
    @FXML
    Label characterLabel2, actorLabel2, characterLabel3, actorLabel3;
    @FXML
    ImageView castImage1, castImage2, castImage3;
    @FXML
    Button toWatchButton, watchingButton, completedButton;
    @FXML
    TextField searchBox;

    int listID;

    private ContentList fullList;

    @FXML
    private void initialize(){
        listID = 0;
        toWatchButton.getStyleClass().add("current");
        toWatchButton.setOnAction(event -> {
            completedButton.getStyleClass().remove("current");
            watchingButton.getStyleClass().remove("current");
            toWatchButton.getStyleClass().add("current");
            listID=0;
            readContentTable();
        });
        watchingButton.setOnAction(event -> {
            toWatchButton.getStyleClass().remove("current");
            completedButton.getStyleClass().remove("current");
            watchingButton.getStyleClass().add("current");
            listID=1;
            readContentTable();
        });
        completedButton.setOnAction(event -> {
            watchingButton.getStyleClass().remove("current");
            toWatchButton.getStyleClass().remove("current");
            completedButton.getStyleClass().add("current");
            listID=2;
            readContentTable();
        });
        fullList = new ContentList();
        readContentTable();
    }
    /**
     * Displays item in detail view when selected on list
     */
    @FXML
    public void getSelectedItem(){

        //show detail view
        detailView.setVisible(true);
        //fetch selected item
        ContentBase selected =  currentList.getSelectionModel().getSelectedItem().getEntry();
        //reset cast list
        castList.getChildren().clear();
        //fill out 
        if (selected.getContentType() == 1){
            directorLabel.setText(selected.getTopCrew().get(0).getName());
            changingLabel.setText("Director:");
        }

        else{
            String creators = "";
            changingLabel.setText("Creator:");
            for (int i = 0; i < selected.getTopCrew().size(); i++){
                if ( selected.getTopCrew().size() - i == 1){
                    creators += selected.getTopCrew().get(i).getName();
                }
                else  creators += selected.getTopCrew().get(i).getName() + ", ";
            }
            directorLabel.setText(creators);
        }
        titleLabel.setText(selected.getTitle());
        setPoster(selected.getImage());

        yearLabel.setText(Integer.toString(selected.getReleaseDate().getYear()));
        overviewLabel.setText(selected.getOverview());
        ArrayList<CastMember> cast = selected.getCast();
        //variable to alternate colors for cast members
        int x = 0;
        //fill out cast
        for (CastMember member : cast){
            try {
                VBox castBox = new VBox();
                castBox.setAlignment(Pos.CENTER);
                castBox.setMinWidth(175);
                ImageView castImage = new ImageView(new Image(member.getPerson().getImage()));
                castImage.setFitWidth(125);
                castImage.setFitHeight(187);
                HBox characterBox = new HBox();
                characterBox.setAlignment(Pos.CENTER);
                characterBox.setMinHeight(20);
                Label characterLabel = new Label(member.getCharacter());
                characterLabel.setFont(new Font(18));
                characterLabel.setStyle("-fx-font-weight: bold");
                characterLabel.setAlignment(Pos.CENTER_LEFT);
                characterLabel.setWrapText(true);
                characterBox.getChildren().add(characterLabel);
                HBox actorBox = new HBox();
                actorBox.setAlignment(Pos.CENTER);
                Label actorLabel = new Label(member.getPerson().getName());
                actorLabel.setFont(new Font(14));
                actorLabel.setAlignment(Pos.CENTER_LEFT);
                actorBox.getChildren().add(actorLabel);
                //add to castbox
                castBox.getChildren().addAll(castImage, characterBox, actorBox);
                //highlight if necessary
                if (x%2 == 0){
                    castBox.setStyle("-fx-background-color: lightgray");
                }
                castList.getChildren().add(castBox);
                x++;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Gets which list the user is on (watching, complete, etc.)
     * @param list
     */
    public void setCurrentList(ContentList list){
         currentList.getItems().clear();
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

    /**
     * Opens window to add a new item to the list
     * @throws IOException
     */
   @FXML
   public void openNewItem() throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/new-item.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            NewItemController controller = fxmlLoader.getController();
            controller.setCurrentList(listID);       //TODO: make application read from GUI which list is currently selected
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("New Item");
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
            controller.addItem.setOnAction(event -> {
                controller.takeUserInput();
                stage.close();
                readContentTable();
            });
            stage.setOnCloseRequest((event) -> {
                readContentTable();
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Opens window to edit the selected item
     * @throws IOException
     */
    @FXML
    public void editButtonClicked() throws IOException{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/edit-item.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            EditItemController controller = fxmlLoader.getController();
            ListEntry selected = currentList.getSelectionModel().getSelectedItem();
            controller.setCurrentEntry(selected);
            controller.setCurrentList(listID);
            controller.start();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Item");
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();

            controller.editItem.setOnAction(event -> {
                controller.takeUserInput();
                stage.close();
                readContentTable();
            });
            stage.setOnCloseRequest((event) -> {
                readContentTable();
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * Delete currently selected Item
     * @throws IOException
     */
    @FXML
    public void deleteButtonClicked() throws IOException{
        ListEntry selected = currentList.getSelectionModel().getSelectedItem();
        selected.deleteRow(selected);
        readContentTable();

    }

    public void readContentTable(){
        ContentList list = new ContentList();
        list.readList(listID);
        setCurrentList(list);
        return;
    }

    /**
     * Search for text given in GUI
     */
    @FXML
    public void search(){
        String searchText;
        try{
            searchText = searchBox.getText();
            if (fullList.getListEntries().size() < 1){
                fullList.getListEntries().clear();
                fullList.getListEntries().addAll(currentList.getItems());
            }


            //
            ContentList searchResults = fullList.search(searchText);
            setCurrentList(searchResults);
        }
        catch (Error e){
            e.printStackTrace();

        }
    }

    /**
     * Clear Search results
     */
    @FXML
    public void clearSearch(){
        setCurrentList(fullList);
        searchBox.clear();
    }

}
