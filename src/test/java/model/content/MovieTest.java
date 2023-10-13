package model.content;

import model.Person.Person;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class MovieTest {

    @Test
    void generateMovie() {
        Movie tempMovie = Movie.generateMovie("680");
        assertEquals(680, tempMovie.getTmdbID() );

    }

    @Test
    void addCast(){
        Movie tempMovie = Movie.generateMovie("680");
        tempMovie.setCast(new ArrayList<Person>());
        tempMovie = Movie.addCast(tempMovie);
        assertEquals("Uma Thurman", tempMovie.getCast().get(2).getName());
        assertEquals("John Travolta", tempMovie.getCast().get(0).getName());

    }

    @Test
    void addDirector(){
        Movie tempMovie = Movie.generateMovie("680");
        tempMovie.setDirector(new Person());
        tempMovie = Movie.addDirector(tempMovie);
        assertEquals("Quentin Tarantino", tempMovie.getDirector().getName());


    }
}