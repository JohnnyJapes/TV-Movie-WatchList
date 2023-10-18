package model.content;

import model.Person.Person;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class MovieTest {

    @Test
    void generateTMBDdetails() {
        Movie tempMovie = new Movie();
        tempMovie.getTMDBdetails(680);
        assertEquals(680, tempMovie.getTmdbID() );

    }

    @Test
    void addCast(){
        Movie tempMovie = new Movie();
        tempMovie.getTMDBdetails(680);
        tempMovie.setCast(new ArrayList<Person>());
        tempMovie.addCast();
        assertEquals("Uma Thurman", tempMovie.getCast().get(2).getName());
        assertEquals("John Travolta", tempMovie.getCast().get(0).getName());

    }

    @Test
    void addDirector(){
        Movie tempMovie = new Movie();
        tempMovie.getTMDBdetails(680);
        tempMovie.setDirector(new Person());
        tempMovie.addDirector();
        assertEquals("Quentin Tarantino", tempMovie.getDirector().getName());


    }
}