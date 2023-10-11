package model.content;

import model.Person.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class TV extends ContentBase {

    private final String contentType = "TV";
    //total episodes is the total number of episodes in a tv show.
    private int totalEpisodes, watchedEpisodes;
    //series creator
    private Person creator;


    public TV(){
        super();
        creator = new Person();
        totalEpisodes = 0;
        watchedEpisodes = 0;
    }

    public TV(int totalEpisodes, int watchedEpisodes, Person creator) {
        this.totalEpisodes = totalEpisodes;
        this.watchedEpisodes = watchedEpisodes;
        this.creator = creator;
    }

    public TV(int ID, int totalEpisodes, int watchedEpisodes, Person creator) {
        super(ID);
        this.totalEpisodes = totalEpisodes;
        this.watchedEpisodes = watchedEpisodes;
        this.creator = creator;
    }

    public TV(String title, String summary, String imageLocation, int tmdbID, int ID, LocalDate releaseDate, float userRating, ArrayList<Person> cast, int totalEpisodes, int watchedEpisodes, Person creator) {
        super(title, summary, imageLocation, tmdbID, ID, releaseDate, userRating, cast);
        this.totalEpisodes = totalEpisodes;
        this.watchedEpisodes = watchedEpisodes;
        this.creator = creator;
    }

    /**
     * Gets contentType.
     *
     * @return java.lang.String, value of contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Gets totalEpisodes.
     *
     * @return int, value of totalEpisodes
     */
    public int getTotalEpisodes() {
        return totalEpisodes;
    }

    /**
     * Method to set totalEpisodes.
     *
     * @param totalEpisodes int - totalEpisodes
     */
    public void setTotalEpisodes(int totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }

    /**
     * Gets watchedEpisodes.
     *
     * @return int, value of watchedEpisodes
     */
    public int getWatchedEpisodes() {
        return watchedEpisodes;
    }

    /**
     * Method to set watchedEpisodes.
     *
     * @param watchedEpisodes int - watchedEpisodes
     */
    public void setWatchedEpisodes(int watchedEpisodes) {
        this.watchedEpisodes = watchedEpisodes;
    }
}
