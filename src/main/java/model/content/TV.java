package model.content;

import model.Person.Person;

import java.util.Date;

public class TV extends ContentBase {

    private final String contentType = "TV";
    //total episodes is the total number of episodes in a tv show.
    private int totalEpisodes, watchedEpisodes;
    //series creator
    private Person creator;


    public TV(String title, String summary, Date releaseDate, String imageLocation, float userRating, Person[] cast, int totalEpisodes, int watchedEpisodes, Person creator) {
        super(title, summary, imageLocation, releaseDate, userRating, cast);
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
