package model.content;

import model.Person.Person;

import java.util.Date;

public class ContentBase {

    private String title, summary;
    private Date releaseDate;
    private float userRating;
    private Person[] cast;

    public ContentBase(String title, String summary, Date releaseDate, float userRating, Person[] cast) {
        this.title = title;
        this.summary = summary;
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
     * Gets cast.
     *
     * @return model.Person.Person[], value of cast
     */
    public Person[] getCast() {
        return cast;
    }

    /**
     * Method to set cast.
     *
     * @param cast model.Person.Person[] - cast
     */
    public void setCast(Person[] cast) {
        this.cast = cast;
    }
}
