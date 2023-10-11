package model.content;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class MovieTest {

    @Test
    void generateMovie() {
        Movie tempMovie = Movie.generateMovie("680");
        assertEquals(680, tempMovie.getTmdbID() );
        assertEquals("Uma Thurman", tempMovie.getCast().get(2).getName());
        assertEquals("John Travolta", tempMovie.getCast().get(0).getName());
    }
}