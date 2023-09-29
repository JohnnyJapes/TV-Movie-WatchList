package model.content;

import model.Person.Person;

import java.util.ArrayList;
import java.util.Date;

public class ContentBase {

    protected String title, summary, imageLocation; //imageLocation might be a URL or a filepath, currently undecided how images will be handled
    //ID should refer to a local database ID
    private int tmdbID, ID;    //ID should refer to a local database ID
    private Date releaseDate;
    private float userRating;
    private ArrayList<Person> cast;

    public ContentBase() {
        cast = new ArrayList<Person>();
    }

    public ContentBase(int ID){
        //call function to fetch info from database

    }

    public ContentBase(String title, String summary, String imageLocation, int tmdbID, int ID, Date releaseDate, float userRating, ArrayList<Person> cast) {
        this.title = title;
        this.summary = summary;
        this.imageLocation = imageLocation;
        this.tmdbID = tmdbID;
        this.ID = ID;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
        this.cast = cast;
    }

    /**
     * Gets title.
     *
     * @return java.lang.String, value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method to set title.
     *
     * @param title java.lang.String - title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets summary.
     *
     * @return java.lang.String, value of summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Method to set summary.
     *
     * @param summary java.lang.String - summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Gets releaseDate.
     *
     * @return java.util.Date, value of releaseDate
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * Method to set releaseDate.
     *
     * @param releaseDate java.util.Date - releaseDate
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Gets userRating.
     *
     * @return float, value of userRating
     */
    public float getUserRating() {
        return userRating;
    }

    /**
     * Method to set userRating.
     *
     * @param userRating float - userRating
     */
    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    /**
     * Gets tmdbID.
     *
     * @return int, value of tmdbID
     */
    public int getTmdbID() {
        return tmdbID;
    }

    /**
     * Method to set tmdbID.
     *
     * @param tmdbID int - tmdbID
     */
    public void setTmdbID(int tmdbID) {
        this.tmdbID = tmdbID;
    }

    /**
     * Gets ID.
     *
     * @return int, value of ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Method to set ID.
     *
     * @param ID int - ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Gets cast.
     *
     * @return java.util.ArrayList<model.Person.Person>, value of cast
     */
    public ArrayList<Person> getCast() {
        return cast;
    }

    /**
     * Method to set cast.
     *
     * @param cast java.util.ArrayList<model.Person.Person> - cast
     */
    public void setCast(ArrayList<Person> cast) {
        this.cast = cast;
    }

    /**
     * Gets imageLocation.
     *
     * @return java.lang.String, value of imageLocation
     */
    public String getImageLocation() {
        return imageLocation;
    }

    /**
     * Method to set imageLocation.
     *
     * @param imageLocation java.lang.String - imageLocation
     */
    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }
}
