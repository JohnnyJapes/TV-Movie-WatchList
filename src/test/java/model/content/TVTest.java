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
        tempTV = new TV(1950);
        assertEquals(1950, tempTV.getTmdbID() );
    }

    @Test
    void addCast() {
        TV tempTV = new TV(1920);
        tempTV.setCast(new ArrayList<Person>());
        tempTV.addCast();
        assertEquals("Kyle MacLachlan", tempTV.getCast().get(0).getName() );
        tempTV = new TV(1950);
        tempTV.setCast(new ArrayList<Person>());
        tempTV.addCast();
        assertEquals("Gregory Smith", tempTV.getCast().get(1).getName() );
    }

    @Test
    void addCreators() {
        TV tempTV = new TV(1920);
        tempTV.setCreators(new ArrayList<Person>());
        tempTV.addCreators();
        assertEquals("David Lynch", tempTV.getCreators().get(0).getName() );
        tempTV = new TV(1950);
        tempTV.setCreators(new ArrayList<Person>());
        tempTV.addCreators();
        assertEquals("Greg Berlanti", tempTV.getCreators().get(0).getName() );
    }
}