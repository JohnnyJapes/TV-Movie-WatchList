package model.Person;

import model.content.ContentBase;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;

public class Person {
    //variables
    //known for should store the role of the person: director, actor, etc.
    private String name, biography, gender, knownFor, imageLocation; // imagelocation will either be a file path or URL
    //tmdb is the TV Movie Database
    //ID should refer to a local database ID
    private int tmdbID, ID;
    private Date birthday, deathDay;
    //credits should contain entries of movies/tv shows they have been a part of
    private ContentBase[] credits; //variable type might change

    public Person(String name, String biography, String gender, String knownFor, int tmdbID, int ID, Date birthday, Date deathDay, ContentBase[] credits) {
        this.name = name;
        this.biography = biography;
        this.gender = gender;
        this.knownFor = knownFor;
        this.tmdbID = tmdbID;
        this.ID = ID;
        this.birthday = birthday;
        this.deathDay = deathDay;
        this.credits = credits;
    }

    //example of detail response from TMDB
    /*
              "adult": false,
  "also_known_as": [
    "Thomas Jacob Black",
    "Τόμας Τζέικομπ \"Τζακ\" Μπλακ",
    "Τόμας Τζέικομπ Μπλακ",
    "Τζακ Μπλακ",
    "잭 블랙",
    "Джек Блек",
    "ג'ק בלאק"
  ],
  "biography": "Thomas Jacob \"Jack\" Black (born August 28, 1969) is an American actor, comedian, musician, and songwriter. Black is known for his roles in the films High Fidelity (2000), Shallow Hal (2001), Orange County (2002), School of Rock (2003), Envy (2004), Gulliver's Travels (2010), Bernie (2011) and The House with a Clock in Its Walls (2018), in addition to his role in the Jumanji franchise. He also voices the giant panda named Po from DreamWorks Animation's Kung Fu Panda films. He gained Golden Globe nominations for his work in School of Rock and Bernie, and he was given a star on Hollywood's Walk of Fame in 2018.\n\nBlack is also the lead vocalist of the Grammy Award–winning comedy rock duo Tenacious D, which he formed in 1994 with long time friend, Kyle Gass. They have released multiple studio albums including their self titled debut Tenacious D, The Pick of Destiny, Rize of the Fenix, and Post-Apocalypto, in addition to their television series Tenacious D (1997–2000) and film Tenacious D in The Pick of Destiny (2006). Since 2018, Black has maintained a YouTube channel called Jablinski Games.",
  "birthday": "1969-08-28",
  "deathday": null,
  "gender": 2,
  "homepage": null,
  "id": 70851,
  "imdb_id": "nm0085312",
  "known_for_department": "Acting",
  "name": "Jack Black",
  "place_of_birth": "Santa Monica, California, USA",
  "popularity": 36.681,
  "profile_path": "/rtCx0fiYxJVhzXXdwZE2XRTfIKE.jpg"


             */

    /**
     * Gets name.
     *
     * @return java.lang.String, value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to set name.
     *
     * @param name java.lang.String - name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets biography.
     *
     * @return java.lang.String, value of biography
     */
    public String getBiography() {
        return biography;
    }

    /**
     * Method to set biography.
     *
     * @param biography java.lang.String - biography
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     * Gets gender.
     *
     * @return java.lang.String, value of gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Method to set gender.
     *
     * @param gender java.lang.String - gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets knownFor.
     *
     * @return java.lang.String, value of knownFor
     */
    public String getKnownFor() {
        return knownFor;
    }

    /**
     * Method to set knownFor.
     *
     * @param knownFor java.lang.String - knownFor
     */
    public void setKnownFor(String knownFor) {
        this.knownFor = knownFor;
    }

    /**
     * Gets tmdbID.
     *
     * @return int, value of tmdbID
     */
    public int getTmdbID() {
        return tmdbID;
    }

    /**
     * Method to set tmdbID.
     *
     * @param tmdbID int - tmdbID
     */
    public void setTmdbID(int tmdbID) {
        this.tmdbID = tmdbID;
    }

    /**
     * Gets ID.
     *
     * @return int, value of ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Method to set ID.
     *
     * @param ID int - ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Gets birthday.
     *
     * @return java.util.Date, value of birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Method to set birthday.
     *
     * @param birthday java.util.Date - birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Gets deathDay.
     *
     * @return java.util.Date, value of deathDay
     */
    public Date getDeathDay() {
        return deathDay;
    }

    /**
     * Method to set deathDay.
     *
     * @param deathDay java.util.Date - deathDay
     */
    public void setDeathDay(Date deathDay) {
        this.deathDay = deathDay;
    }

    /**
     * Gets credits.
     *
     * @return model.content.ContentBase[], value of credits
     */
    public ContentBase[] getCredits() {
        return credits;
    }

    /**
     * Method to set credits.
     *
     * @param credits model.content.ContentBase[] - credits
     */
    public void setCredits(ContentBase[] credits) {
        this.credits = credits;
    }

    /**
     * Gets imageLocation.
     *
     * @return java.lang.String, value of imageLocation
     */
    public String getImageLocation() {
        return imageLocation;
    }

    /**
     * Method to set imageLocation.
     *
     * @param imageLocation java.lang.String - imageLocation
     */
    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

//Methods

    /**
     * Method that should Search TMDB for a person and return the first 10 results
     * @param query - String
     */
    public Person[] searchPersonTMDB(String query){
        Person[] results = {};

        return results;

    }

    /**
     * Method to retrieve the details of a person from their TMDB page and apply them to the current Person Object
     * @param tmdbID - int
     */
    private void retrievePersonDetails(int tmdbID){

    }


}
