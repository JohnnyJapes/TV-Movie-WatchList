package model.content;

import model.Person.Person;

import java.util.Date;

public class Movie extends ContentBase {

    private final String contentType = "Movie";
    private Person director;


    public Movie(String title, String summary, Date releaseDate, float userRating, Person[] cast, Person director) {
        super(title, summary, releaseDate, userRating, cast);
        this.director = director;
    }

    /**
     * Gets contentType.
     *
     * @return java.lang.String, value of contentType
     */
    public String getContentType() {
        return contentType;
    }
}
