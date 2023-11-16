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
import model.Person.Person;
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
    TextField title, director, tmdbID, episodes;
    @FXML
    DatePicker date;
    @FXML
    TextArea overview;
    @FXML
    RadioButton movieRadio, tvRadio;
    @FXML
    Button addItem;


    int currentList;

    protected ContentBase content = new ContentBase();

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
    public void movieRadioSelected(){
        episodes.setDisable(true);
    }
    @FXML
    public void tvRadioSelected(){
        episodes.setDisable(false);
    }
    @FXML
    public void clickTMDBsearch(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/search-results.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            SearchResultsController controller = fxmlLoader.getController();
            if(movieRadio.isSelected())
                controller.addList((ArrayList<ContentBase>)new Movie().searchTMDB(title.getText()));
            else if (tvRadio.isSelected()){
                controller.addList((ArrayList<ContentBase>)new TV().searchTMDB(title.getText()));
            }
            else{
                throw new Exception("No Type Selected");
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
                    movie.setTotalEpisodes(1);
                   // ListEntry entry = new ListEntry(movie, currentList);
                   // entry.createRow();
                }
                else if (selected.getContentType() == 2) {
                    TV tv = (TV) selected;
                    stage.close();
                    tv.addFromSearch();
                  //  ListEntry entry = new ListEntry(tv, currentList);
                  //  tv.createRow();
                }
                movieRadio.setDisable(true);
                tvRadio.setDisable(true);
                setTextFields(selected);
                content = selected;

            });
        }
        catch (Exception e){
            e.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage());
            error.show();
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

    /**
     * Sets textfields and datepicker according to the given contentBase
     * @param result
     */
    public void setTextFields(ContentBase result){
        director.setText(result.getTopCrew().get(0).getName());
        tmdbID.setText(Integer.toString(result.getTmdbID()));
        episodes.setText(Integer.toString(result.getTotalEpisodes()));
        overview.setText(result.getOverview());
        title.setText(result.getTitle());
        date.setValue(result.getReleaseDate());
    }

    /**
     * Takes user input from input fields
     */
    public void takeUserInput(){
        content.setWatched(0);
        try {

            content.setTitle(title.getText());
            if (tmdbID.getText() == "") tmdbID.setText("-1");
            content.setTmdbID(Integer.parseInt(tmdbID.getText()));
            Person holdDirector = new Person();
            holdDirector.setName(director.getText());
            int found = holdDirector.searchNameLocalDB();
            if (found > -1) holdDirector.readRow(found);
            else {
                ArrayList<Person> personList = Person.searchPersonTMDB(director.getText());
                if (personList.size() > 0){
                    holdDirector = personList.get(0);
                }
                holdDirector.createRow();
            }
            content.setDirector(holdDirector);
            content.setOverview(overview.getText());
            if(movieRadio.isSelected()){
                content.setContentType(1);  //movie
                content.setTotalEpisodes(1);
            }

            else if(tvRadio.isSelected()){
                content.setTotalEpisodes(Integer.parseInt(episodes.getText()));
                content.setContentType(2); //tv
            }
            content.setReleaseDate(date.getValue());
            ListEntry entry = new ListEntry(content, currentList);
            entry.createRow();
        }
        catch(Exception e){
            e.printStackTrace();

        }
    }


}
