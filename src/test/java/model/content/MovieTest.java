package model.content;

import com.tv.tvmoviewatchlist.model.Person.Person;
import com.tv.tvmoviewatchlist.model.content.Movie;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
class MovieTest {


    /**
     * Loads properties from config.proprties.
     * @return 
     * @throws IOException
     */
    @BeforeAll
    public static void getPropValues() throws IOException {
        Boolean result = true;
        InputStream inputStream = null;
            try {
                Properties props = new Properties();
                String propFileName = "config.properties";
    
                inputStream = new FileInputStream(propFileName);
    
                if (inputStream != null) {
                    props.load(inputStream);
                } else {
                    result = false;
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
                }    
                //set properties to system so they are accessible
                System.setProperty("dburl",props.getProperty("dburl"));
                System.setProperty("token",props.getProperty("token"));
            } catch (Exception e) {
                System.out.println("Exception: " + e);
                result = false;

            } finally {
                inputStream.close();
            }
            //return result;
    }
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