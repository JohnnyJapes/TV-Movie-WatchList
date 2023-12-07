package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.ListEntry;
import model.Person.Person;
import model.content.ContentBase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class EditItemController  {


    @FXML
    ImageView tmdbImage;
    @FXML
    TextField title, director, tmdbID, totalEpisodes, rank, watchedEpisodes;
    @FXML
    DatePicker date;
    @FXML
    TextArea overview;
    @FXML
    RadioButton movieRadio, tvRadio;
    @FXML
    Button editItem;
    @FXML
    ToggleGroup contentGroup;
    @FXML
    ChoiceBox<String> listChoice;


    protected int currentList;

    protected ListEntry currentEntry;
    protected ContentBase content;

    @FXML
    public void start() {
        disableRadios();
        initializeChoiceBox();
        setTextFields();
        if (content.getContentType() == 1) totalEpisodes.setDisable(true);

    }
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
    public void disableRadios(){
        if (content.getContentType() == 1) {
            movieRadio.setSelected(true);
            tvRadio.setSelected(false);
        }
        else {
            tvRadio.setSelected(true);
            movieRadio.setSelected(false);
        }
        movieRadio.setDisable(true);
        tvRadio.setDisable(true);
    }

    @FXML
    public void movieRadioSelected(){
        totalEpisodes.setDisable(true);
    }
    @FXML
    public void tvRadioSelected(){
        totalEpisodes.setDisable(false);
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
     * Gets content.
     *
     * @return model.content.ContentBase, value of content
     */
    public ContentBase getContent() {
        return content;
    }

    /**
     * Method to set content.
     *
     * @param content model.content.ContentBase - content
     */
    public void setContent(ContentBase content) {
        this.content = content;
    }

    /**
     * Gets currentEntry.
     *
     * @return model.ListEntry, value of currentEntry
     */
    public ListEntry getCurrentEntry() {
        return currentEntry;
    }

    /**
     * Method to set currentEntry.
     *
     * @param currentEntry model.ListEntry - currentEntry
     */
    public void setCurrentEntry(ListEntry currentEntry) {
        this.currentEntry = currentEntry;
        setContent(currentEntry.getEntry());
    }

    /**
     * Sets textfields and datepicker according to currentEntry
     */
    @FXML
    public void setTextFields(){
        ContentBase result = currentEntry.getEntry();
        rank.setText(Integer.toString(currentEntry.getListRank()));
        director.setText(result.getTopCrew().get(0).getName());
        watchedEpisodes.setText(Integer.toString(result.getWatchedEpisodes()));
        tmdbID.setText(Integer.toString(result.getTmdbID()));
        totalEpisodes.setText(Integer.toString(result.getTotalEpisodes()));
        overview.setText(result.getOverview());
        title.setText(result.getTitle());
        date.setValue(result.getReleaseDate());

        switch(currentEntry.getListID()){
            case 0:
                listChoice.setValue("To Watch");
                break;
            case 1:
                listChoice.setValue("Watching");
                break;
            case 2:
                listChoice.setValue("Completed");
        }
    }

    /**
     * Takes user input from input fields and updates DB
     */
    public void takeUserInput(){
        try {
            ListEntry entry = new ListEntry();

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
                content.setTotalEpisodes(Integer.parseInt(totalEpisodes.getText()));
                content.setContentType(2); //tv
            }
            content.setReleaseDate(date.getValue());
            content.setWatchedEpisodes(Integer.parseInt(watchedEpisodes.getText()));

            switch(listChoice.getValue()){
                case "To Watch":
                    entry.setListID(0);
                    break;
                case "Watching":
                    entry.setListID(1);
                    break;
                case "Completed":
                    entry.setListID(2);
            }
            //fix numbering if entry is changing lists
            if (currentEntry.getListID() != entry.getListID()){
                currentEntry.shrinkAllRanks(currentEntry.getListRank(), currentEntry.getListID());

            }
            entry.setListRank(Integer.parseInt(rank.getText()));
            entry.setEntry(content);
            currentEntry.updateRow(entry);
        }
        catch(Exception e){
            e.printStackTrace();

        }
    }
    public void initializeChoiceBox(){
        ObservableList<String> choices = listChoice.getItems();
        choices.add("To Watch");
        choices.add("Watching");
        choices.add("Completed");
    }


}
