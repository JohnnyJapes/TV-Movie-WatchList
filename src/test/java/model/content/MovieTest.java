package model.content;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class MovieTest {

    @Test
    void generateMovie() {
        //Movie test = new Movie();
        Movie test = Movie.generateMovie("680");
        System.out.println(test.getTmdbID());
        assertEquals(680, Movie.generateMovie("680").getTmdbID());
    }
}