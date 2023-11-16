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
        Movie failMovie = new Movie();
        failMovie.getTMDBdetails(1220);
        assertEquals((Integer) 0, failMovie.getTmdbID());

    }

    @Test
    void addCast(){
        Movie tempMovie = new Movie();
        tempMovie.getTMDBdetails(680);
        tempMovie.setCast(new ArrayList<>());
        tempMovie.addCast();
        assertEquals("Uma Thurman", tempMovie.getCast().get(2).getPerson().getName());
        assertEquals("John Travolta", tempMovie.getCast().get(0).getPerson().getName());

    }

    @Test
    void addDirector(){
        Movie tempMovie = new Movie();
        tempMovie.getTMDBdetails(680);
        tempMovie.setDirector(new Person());
        tempMovie.addDirector();
        assertEquals("Quentin Tarantino", tempMovie.getDirector().getName());


    }

    @Test
    void searchTMDB() {
        Movie tempMovie = new Movie();
        ArrayList<Movie> results = (ArrayList<Movie>) tempMovie.searchTMDB("Scream 4");
        assertEquals("Scream 4", results.get(0).getTitle());
        results = (ArrayList<Movie>) tempMovie.searchTMDB("asdf");
    }

    @Test
    void getDetails(){
        Movie tempMovie = new Movie();
        tempMovie.getTMDBdetails(680);

        System.out.println(tempMovie.getDetails());
    }
    @Test
    void createRow(){
        Movie tempMovie = new Movie();
        tempMovie.getTMDBdetails(680);
        tempMovie.setImageURL("/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg");
        tempMovie.createRow();
    }
    @Test
    void makeImageLocal(){
        Movie tempMovie = new Movie();
        //tempMovie.getTMDBdetails(680);
        //tempMovie.setImageURL("/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg");
        tempMovie.readRow(6);
        tempMovie.makeImageLocal();

    }
}