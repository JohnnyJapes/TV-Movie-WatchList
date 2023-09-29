package model.content;

import model.Person.Person;

import java.util.ArrayList;
import java.util.Date;

public class Movie extends ContentBase {

    private final String contentType = "Movie";
    private Person director;

    public Movie(){
        super();
        director = new Person();
    }

    public Movie(Person director) {
        this.director = director;
    }

    public Movie(int ID, Person director) {
        super(ID);
        this.director = director;
    }

    public Movie(String title, String summary, String imageLocation, int tmdbID, int ID, Date releaseDate, float userRating, ArrayList<Person> cast, Person director) {
        super(title, summary, imageLocation, tmdbID, ID, releaseDate, userRating, cast);
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

    /**
     * Gets director.
     *
     * @return model.Person.Person, value of director
     */
    public Person getDirector() {
        return director;
    }

    /**
     * Method to set director.
     *
     * @param director model.Person.Person - director
     */
    public void setDirector(Person director) {
        this.director = director;
    }
}
