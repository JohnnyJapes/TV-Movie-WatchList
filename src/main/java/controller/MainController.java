package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import model.ContentList;
import model.ListEntry;
import model.content.ContentBase;
import okhttp3.OkHttpClient;

import java.io.*;

public class MainController {


    @FXML
    ListView<ListEntry> currentList;
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
    @FXML
    public void getSelectedItem(){

        ContentBase selected =  currentList.getSelectionModel().getSelectedItem().getEntry();
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

        OkHttpClient client = new OkHttpClient();
        characterLabel.setText(selected.getCast().get(0).getCharacter());
        actorLabel.setText(selected.getCast().get(0).getPerson().getName());
        System.out.println("ID PIC: " + selected.getCast().get(0).getPerson().getID());
        Image image = new Image(selected.getCast().get(0).getPerson().getImage());
        castImage1.setImage(image);

        //2nd cast member
        try{
            characterLabel2.setText(selected.getCast().get(1).getCharacter());
            actorLabel2.setText(selected.getCast().get(1).getPerson().getName());
            System.out.println("ID PIC: " + selected.getCast().get(1).getPerson().getID());
            image = new Image(selected.getCast().get(1).getPerson().getImage());
            castImage2.setImage(image);
        }
        catch (Error e){
            e.printStackTrace();
            characterLabel2.setVisible(false);
            actorLabel2.setVisible(false);
            castImage2.setVisible(false);
        }
        //3rd cast member
        try{
            characterLabel3.setText(selected.getCast().get(2).getCharacter());
            actorLabel3.setText(selected.getCast().get(2).getPerson().getName());
            System.out.println("ID PIC: " + selected.getCast().get(2).getPerson().getID());
            image = new Image(selected.getCast().get(2).getPerson().getImage());
            castImage3.setImage(image);
        }
        catch (Error e){
            e.printStackTrace();
            characterLabel2.setVisible(false);
            actorLabel2.setVisible(false);
            castImage2.setVisible(false);
        }
    }

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
