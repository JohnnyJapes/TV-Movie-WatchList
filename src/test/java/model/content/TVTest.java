package model.content;

import model.Person.Person;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TVTest {

    @Test
    void getTMDBdetails() {
        TV tempTV = new TV();
        tempTV.getTMDBdetails(1920);
        assertEquals(1920, tempTV.getTmdbID() );
        System.out.println(tempTV.getDetails());
        tempTV = new TV(1950);
        assertEquals(1950, tempTV.getTmdbID() );
    }

    @Test
    void addCast() {
        TV tempTV = new TV(1920);
        tempTV.setCast(new ArrayList<>());
        tempTV.addCast();
        assertEquals("Kyle MacLachlan", tempTV.getCast().get(0).getPerson().getName() );
        tempTV = new TV(1950);
        tempTV.setCast(new ArrayList<>());
        tempTV.addCast();
        assertEquals("Gregory Smith", tempTV.getCast().get(1).getPerson().getName() );
    }

    @Test
    void addCreators() {
        TV tempTV = new TV(1920);
        tempTV.setCreators(new ArrayList<Person>());
        tempTV.addCreators();
        assertEquals("Mark Frost", tempTV.getCreators().get(0).getName() );
        tempTV = new TV(1950);
        tempTV.setCreators(new ArrayList<Person>());
        tempTV.addCreators();
        assertEquals("Greg Berlanti", tempTV.getCreators().get(0).getName() );
    }
    @Test
    void getDetails(){
        TV tempTV = new TV();
        tempTV.getTMDBdetails(1920);
        System.out.println(tempTV.getDetails());
        assertEquals("TV Show Details:\n" +
                "Title: Twin Peaks\n" +
                "Overview: The body of Laura Palmer is washed up on a beach near the small Washington state town of Twin Peaks. FBI Special Agent Dale Cooper is called in to investigate her strange demise only to uncover a web of mystery that ultimately leads him deep into the heart of the surrounding woodland and his very own soul.\n" +
                "Release Date: 1990-04-08\n" +
                "Creators: Mark Frost, David Lynch\n" +
                "Total Episodes: 48", tempTV.getDetails());
    }

    @Test
    void searchTMDB(){
        TV tempTV = new TV();
        ArrayList<TV> results = (ArrayList<TV>)         tempTV.searchTMDB("asdf");
    }
}