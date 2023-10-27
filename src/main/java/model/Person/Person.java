package model.Person;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import model.content.ContentBase;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.text.StringEscapeUtils;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Person {
    //variables
    //known for should store the primary role of the person: director, actor, etc.
    private String name, biography, gender, knownFor, imageURL; // imagelocation will either be a file path or URL
    //tmdb is the TV Movie Database
    //ID should refer to a local database ID
    private int tmdbID, ID;
    private LocalDate birthday, deathDay;
    //credits should contain entries of movies/tv shows they have been a part of
    private ArrayList<ContentBase> credits; //variable type might change

    public Person() {
        name = "";
        biography= "";
        gender = "";
        knownFor = "";
        imageURL = "";
        credits = new ArrayList<ContentBase>();

    }

    /**
     * Constructor without list of credits
     * @param name
     * @param biography
     * @param gender
     * @param knownFor
     * @param tmdbID
     * @param ID
     * @param birthday
     * @param deathDay
     */
    public Person(String name, String biography, String gender, String knownFor, int tmdbID, int ID, LocalDate birthday, LocalDate deathDay) {
        this.name = name;
        this.biography = biography;
        this.gender = gender;
        this.knownFor = knownFor;
        this.tmdbID = tmdbID;
        this.ID = ID;
        this.birthday = birthday;
        this.deathDay = deathDay;
    }
    public Person(String name, String biography, String gender, String knownFor, int tmdbID, int ID, LocalDate birthday, LocalDate deathDay, ArrayList<ContentBase> credits) {
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
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Method to set birthday.
     *
     * @param birthday java.util.Date - birthday
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     * Gets deathDay.
     *
     * @return java.util.Date, value of deathDay
     */
    public LocalDate getDeathDay() {
        return deathDay;
    }

    /**
     * Method to set deathDay.
     *
     * @param deathDay java.util.Date - deathDay
     */
    public void setDeathDay(LocalDate deathDay) {
        this.deathDay = deathDay;
    }

    /**
     * Gets credits.
     *
     * @return java.util.ArrayList<model.content.ContentBase>, value of credits
     */
    public ArrayList<ContentBase> getCredits() {
        return credits;
    }

    /**
     * Method to set credits.
     *
     * @param credits java.util.ArrayList<model.content.ContentBase> - credits
     */
    public void setCredits(ArrayList<ContentBase> credits) {
        this.credits = credits;
    }

    /**
     * Gets imageLocation.
     *
     * @return java.lang.String, value of imageLocation
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * Method to set imageLocation.
     *
     * @param imageURL java.lang.String - imageLocation
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

//Methods

    /**
     * Method that should Search TMDB for a name and return the first 5 results
     * @param query - String
     */
    public static ArrayList<Person> searchPersonTMDB(String query){
        ArrayList<Person> results = new ArrayList<Person>();
        OkHttpClient client = new OkHttpClient();
        query = StringEscapeUtils.escapeHtml4(query);

        try{
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/search/person?query=" + query + "&include_adult=false&language=en-US&page=1")
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyODgxODIwZTI3OWFkZGMzN2MzYzNjOTUyYjJlM2VkNCIsInN1YiI6IjY0ZmI2YzY1ZmZjOWRlMGVlM2MzOTA5MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.egadbAWxCd6r9WYP6-0BQiSOoctQdoQ_jx283WyDMIw")
                    .build();
            Response response = client.newCall(request).execute();
            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createParser(response.body().string());
            JsonToken token = parser.nextToken();

            //Fetching results and adding them to person arraylist
            while(!"results".equals(parser.getCurrentName())) token = parser.nextToken();
                //System.out.println("Cast - \n");
                token = parser.nextToken();
                token = parser.nextToken();
                for (int i = 0; i < 5; i++){
                    Person tempPerson = new Person();
                    parser.nextToken();
                    if (token == JsonToken.END_ARRAY) break;
                    while(!"gender".equals(parser.getCurrentName())) token = parser.nextToken();{
                        if (token == JsonToken.FIELD_NAME && "gender".equals(parser.getCurrentName()))
                            parser.nextToken();

                        switch (parser.getIntValue()) {
                            case 1:tempPerson.setGender("female");
                            case 2:tempPerson.setGender("male");
                        }
                        token = parser.nextToken();
                    }
                    //}
                    while(!"known_for_department".equals(parser.getCurrentName())) token = parser.nextToken();

                    token = parser.nextToken();

                    tempPerson.setKnownFor(parser.getText());

                    // }
                    while(!"name".equals(parser.getCurrentName())) token = parser.nextToken();

                    token = parser.nextToken();
                    if (token == JsonToken.VALUE_STRING) {
                        //       System.out.println("Name: "+parser.getText());
                        tempPerson.setName(parser.getText());
                        token = parser.nextToken();
                    }
                    //System.out.println(parser.getCurrentName());
                    System.out.println("TST");
                    results.add(tempPerson);
                }
                System.out.println();



        }
        catch(Error | IOException e){
            System.out.println(e);
            // e.printStackTrace();

        }


        return results;

    }

    /**
     * Method to retrieve the details of a person from their TMDB page and apply them to the current Person Object
     * @param tmdbID - int
     */
    public void retrievePersonDetails(int tmdbID) {
        OkHttpClient client = new OkHttpClient();

        try {
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/person/" + tmdbID + "?language=en-US")
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyODgxODIwZTI3OWFkZGMzN2MzYzNjOTUyYjJlM2VkNCIsInN1YiI6IjY0ZmI2YzY1ZmZjOWRlMGVlM2MzOTA5MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.egadbAWxCd6r9WYP6-0BQiSOoctQdoQ_jx283WyDMIw")
                    .build();
            Response response = client.newCall(request).execute();
            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createParser(response.body().string());
            JsonToken token = parser.nextToken();


            token = parser.nextToken();

            while (!"biography".equals(parser.getCurrentName())) token = parser.nextToken();

            token = parser.nextToken();

            this.setBiography(parser.getText());


            while (!"birthday".equals(parser.getCurrentName())) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "birthday".equals(parser.getCurrentName())) {
                token = parser.nextToken();
                if (token == JsonToken.VALUE_STRING) {
                    LocalDate birthDate = LocalDate.parse(parser.getText());
                    this.setBirthday(birthDate);
                }
            }

            while (!"gender".equals(parser.getCurrentName())) token = parser.nextToken();

            if (token == JsonToken.FIELD_NAME && "gender".equals(parser.getCurrentName()))
                parser.nextToken();
            //  if (token == JsonToken.VALUE_NUMBER_INT) {
            switch (parser.getIntValue()) {
                case 1:
                    this.setGender("female");
                case 2:
                    this.setGender("male");
            }
            token = parser.nextToken();

            //}
            while (!"known_for_department".equals(parser.getCurrentName())) token = parser.nextToken();

            token = parser.nextToken();

            this.setKnownFor(parser.getText());

            // }
            while (!"name".equals(parser.getCurrentName())) token = parser.nextToken();
            token = parser.nextToken();
            if (token == JsonToken.VALUE_STRING) {
                //       System.out.println("Name: "+parser.getText());
                this.setName(parser.getText());
                token = parser.nextToken();
            }
            while (!"profile_path".equals(parser.getCurrentName())) token = parser.nextToken();
            token = parser.nextToken();
            if (token == JsonToken.VALUE_STRING) {
                //       System.out.println("Name: "+parser.getText());
                this.setImageURL(parser.getText());
                token = parser.nextToken();
            }
            //System.out.println(parser.getCurrentName());
            System.out.println();

        }

        catch(Error | IOException e){
                    System.out.println(e);
                    // e.printStackTrace();

                }

    }

    public static void createTable(){
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:local.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists person");

            String[] strColumns = {"name","biography","image_url", "birthday", "knownFor", "gender"};
            String[] intColumns = {"tmdb_id"};
            String query = "create table if not exists person (id integer primary key asc";
            for (String str : strColumns){
                query += ", "+str + " text";
            }
            for (String str : intColumns){
                query += ", "+str + " int ";
            }
            query += ")";


            statement.executeUpdate(query);

        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
    public void createRow(){
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:local.db");
            PreparedStatement statement = connection.prepareStatement("insert into person(name,biography,image_url, birthday, knownFor, gender, tmdb_id)" +
                    " values(?,?,?,?,?,?,?)");
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.setString(1,getName());
            statement.setString(2,getBiography());
            statement.setString(3,getImageURL());
            if(getBirthday() != null) {
                statement.setString(4, getBirthday().toString());
            }
            else statement.setString(4, "");
            statement.setString(5,getKnownFor());
            statement.setString(6,getGender());
            statement.setInt(7,tmdbID);





            statement.executeUpdate();
            ResultSet rs = connection.createStatement().executeQuery("select * from person order by id desc limit 1");
            while(rs.next())
            {
                // read the result set
                System.out.println("name= " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
                setID(rs.getInt("id"));
                System.out.println("tmdb_ID = " + rs.getFloat("tmdb_id"));
            }
            makeImageLocal();
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

    }

    public void readRow(int id){
        Connection connection = null;
        try
        {

            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:local.db");
            //PreparedStatement statement = connection.prepareStatement("insert into content(title, overview, tmdb_id, content_type, total_episodes, watched_episodes, image_url)" +
            //        " values(?,?,?,?,?,?,?)");
            PreparedStatement statement = connection.prepareStatement("select * from person where id=?");
            statement.setInt(1,id);
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            // statement.executeQuery();
            ResultSet rs = statement.executeQuery();
            while(rs.next())
            {
                // read the result set
                setID(rs.getInt("id"));
                setName(rs.getString("name"));
                setImageURL(rs.getString("image_url"));


            }
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

    }
    public void makeImageLocal(){
        //URL url = new URL("https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg");
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url("https://image.tmdb.org/t/p/w500"+ getImageURL())
                .get()
                .build();

        try{
            Response response = client.newCall(request).execute();
            String path = "images/person/"+getID()+"/";

            File img = new File(path);
            img.mkdirs();
            path += "profile.jpg";

            img.createNewFile();
            OutputStream out = new FileOutputStream(path);
            out.write(response.body().bytes());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * Method returns a file input stream for use to display the local image
     * @return
     */
    public FileInputStream getImage(){
        System.out.println("Getting Person image, ID: " + getID());
        try {
            return new FileInputStream("images/person/"+getID()+"/profile.jpg");
        } catch (FileNotFoundException e) {
            System.out.println(e);
            throw new RuntimeException(e);
            //try again after making a local image

        }
    }
    @Override
    public String toString(){
        String str = "";

        str += "Name: " + this.getName() + "\n"
                + "Biography: " +this.getBiography() + "\n"
                + "gender: " + this.getGender()  + "\n"
                + "knownFor: " + this.getKnownFor()  + "\n"
                + "Birthday: " + this.getBirthday().toString()  + "\n"
                + "TMDB ID: " + this.getTmdbID()  + "\n";

        System.out.println(str);
        return str;

    }
}
