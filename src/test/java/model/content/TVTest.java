package model.content;

import model.Person.Person;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TVTest {

    @Test
    void generateTVshow() {
        TV tempTV = TV.generateTVshow("1920");
        assertEquals(1920, tempTV.getTmdbID() );
        tempTV = TV.generateTVshow("1950");
        assertEquals(1950, tempTV.getTmdbID() );
    }

    @Test
    void addCast() {
        TV tempTV = TV.generateTVshow("1920");
        tempTV.setCast(new ArrayList<Person>());
        tempTV = TV.addCast(tempTV);
        assertEquals("Kyle MacLachlan", tempTV.getCast().get(0).getName() );
        tempTV = TV.generateTVshow("1950");
        tempTV.setCast(new ArrayList<Person>());
        tempTV = TV.addCast(tempTV);
        assertEquals("Gregory Smith", tempTV.getCast().get(1).getName() );
    }

    @Test
    void addCreators() {
        TV tempTV = TV.generateTVshow("1920");
        tempTV.setCreators(new ArrayList<Person>());
        tempTV = TV.addCreators(tempTV);
        assertEquals("David Lynch", tempTV.getCreators().get(0).getName() );
        tempTV = TV.generateTVshow("1950");
        tempTV.setCreators(new ArrayList<Person>());
        tempTV = TV.addCreators(tempTV);
        assertEquals("Greg Berlanti", tempTV.getCreators().get(0).getName() );
    }
}