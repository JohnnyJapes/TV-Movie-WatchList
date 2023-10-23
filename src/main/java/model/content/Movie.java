package model.content;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import model.Person.Person;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;

import model.TMDBcompatible;
import okhttp3.OkHttpClient;
import okhttp3.*;
import org.apache.commons.text.StringEscapeUtils;

public class Movie extends ContentBase implements TMDBcompatible {

    private final int contentType = 1; //1 for movies, 2 for TV shows
    private Person director;
    protected int watched;

    public Movie(){
        super();
        director = new Person();
        watched = 0;
    }

    public Movie(Person director) {
        this.director = director;
    }

    public Movie(int ID, Person director) {
        super(ID);
        this.director = director;
    }

    public Movie(String title, String summary, String imageLocation, int tmdbID, int ID, LocalDate releaseDate, float userRating, ArrayList<Person> cast, Person director) {
        super(title, summary, imageLocation, tmdbID, ID, releaseDate, userRating, cast);
        this.director = director;
    }
    public Movie(int tmdbID){
        super();
        getTMDBdetails(tmdbID);
    }

    /**
     * Gets contentType. 1 is a Movie, 2 is a TV show
     *
     * @return java.lang.String, value of contentType
     */
    public int getContentType() {
        return contentType;
    }


    /**
     * Gets director.
     *
     * @return model.Person.Person, value of director
     */
    public Person getDirector() {
        return director;
    }

    /**
     * Method to set director.
     *
     * @param director model.Person.Person - director
     */
    public void setDirector(Person director) {
        this.director = director;
    }


    public Object searchTMDB(String title){

        OkHttpClient client = new OkHttpClient();
        String query = StringEscapeUtils.escapeHtml4(title);
        ArrayList<Movie> movies = new ArrayList<>();


        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/search/movie?query="+ query+"&include_adult=false&language=en-US&page=1")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyODgxODIwZTI3OWFkZGMzN2MzYzNjOTUyYjJlM2VkNCIsInN1YiI6IjY0ZmI2YzY1ZmZjOWRlMGVlM2MzOTA5MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.egadbAWxCd6r9WYP6-0BQiSOoctQdoQ_jx283WyDMIw")
                .build();


        try {
            Response response = client.newCall(request).execute();
            // De-serialize to an movie object
            System.out.println("Movie Search Results ----------------------------");
            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createParser(response.body().string());
            JsonToken token = parser.nextToken();
            // Read JSON object
            while(!"results".equals(parser.getCurrentName())) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "results".equals(parser.getCurrentName())) {
                //System.out.println("Cast - \n");
                token = parser.nextToken(); // Read left bracket i.e. [
                // Loop to print array elements until right bracket i.e ]
                for (int i = 0; i < 7; i++) {
                    if (token == JsonToken.END_ARRAY) break;
                    Movie tempMovie = new Movie();
                    token = parser.nextToken();
                    if (token == JsonToken.END_ARRAY) break;
                    while (!"id".equals(parser.getCurrentName())) token = parser.nextToken();
                    if (token == JsonToken.FIELD_NAME && "id".equals(parser.getCurrentName())) {
                        token = parser.nextToken();
                        if (token == JsonToken.VALUE_NUMBER_INT) {
                            System.out.println("ID : " + parser.getIntValue());
                            tempMovie.setTmdbID(parser.getIntValue());
                        }
                    }
                    while (!"original_title".equals(parser.getCurrentName())) token = parser.nextToken();
                    if (token == JsonToken.FIELD_NAME && "original_title".equals(parser.getCurrentName())) {
                        token = parser.nextToken();
                        if (token == JsonToken.VALUE_STRING) {
                            System.out.println("original_title : " + parser.getText());
                            tempMovie.setTitle(parser.getText());
                        }
                    }
                    while (!"overview".equals(parser.getCurrentName())) token = parser.nextToken();
                    if (token == JsonToken.FIELD_NAME && "overview".equals(parser.getCurrentName())) {
                        token = parser.nextToken();
                        if (token == JsonToken.VALUE_STRING) {
                            System.out.println("overview : " + parser.getText());
                            tempMovie.setOverview(parser.getText());
                        }
                    }
                    while (!"release_date".equals(parser.getCurrentName())) token = parser.nextToken();
                    if (token == JsonToken.FIELD_NAME && "release_date".equals(parser.getCurrentName())) {
                        token = parser.nextToken();
                        if (token == JsonToken.VALUE_STRING) {
                            System.out.println("release_date : " + parser.getText());
                            if (parser.getText() != "")
                            {
                            LocalDate releaseDate = LocalDate.parse(parser.getText());
                            tempMovie.setReleaseDate(releaseDate);
                            }
                        }
                    }
                    movies.add(tempMovie);

                }
            }
            //close the parser
            parser.close();
            System.out.println("Results End -----------------------------");


        }
        catch(Error | IOException e){
            System.out.println(e);
            // e.printStackTrace();

        }

        return movies;

    }
    public void getTMDBdetails(int tmdbID){


        OkHttpClient client = new OkHttpClient();
       // String query = StringEscapeUtils.escapeHtml4(tmdbID);

        Movie tempMovie = new Movie();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + tmdbID + "?language=en-US")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyODgxODIwZTI3OWFkZGMzN2MzYzNjOTUyYjJlM2VkNCIsInN1YiI6IjY0ZmI2YzY1ZmZjOWRlMGVlM2MzOTA5MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.egadbAWxCd6r9WYP6-0BQiSOoctQdoQ_jx283WyDMIw")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if(response.code() != 200) throw new Error(response.code() + " Request error");
            // De-serialize to an movie object

            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createParser(response.body().string());
            JsonToken token = parser.nextToken();
            // Read JSON object
            while(!"homepage".equals(parser.getCurrentName()) ) token = parser.nextToken(); //skip over irrelevant IDs
            while(!"id".equals(parser.getCurrentName()) ) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "id".equals(parser.getCurrentName())) {
                token = parser.nextToken();
                if (token == JsonToken.VALUE_NUMBER_INT) {
                   // System.out.println("ID : " + parser.getIntValue());
                    this.setTmdbID(parser.getIntValue());
                }
            }
            while(!"original_title".equals(parser.getCurrentName()) ) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "original_title".equals(parser.getCurrentName())) {
                token = parser.nextToken();
                if (token == JsonToken.VALUE_STRING) {
                 //   System.out.println("original_title : " + parser.getText());
                    this.setTitle(parser.getText());
                }
            }
            while(!"overview".equals(parser.getCurrentName()) ) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "overview".equals(parser.getCurrentName())) {
                token = parser.nextToken();
                if (token == JsonToken.VALUE_STRING) {
                   // System.out.println("overview : " + parser.getText());
                    this.setOverview(parser.getText());
                }
            }
            while(!"release_date".equals(parser.getCurrentName()) ) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "release_date".equals(parser.getCurrentName())) {
                token = parser.nextToken();
                if (token == JsonToken.VALUE_STRING) {
                 //   System.out.println("release_date : " + parser.getText());
                    LocalDate releaseDate = LocalDate.parse(parser.getText());
                    this.setReleaseDate(releaseDate);
                }
            }
            //close the parser
            parser.close();



        }
        catch(Error | IOException e){
            System.out.println(e);
            return;
           // e.printStackTrace();

        }
        addCast();
        addDirector();
        System.out.println("Movie Title: " + this.getTitle() + "\n");
    }
    public void addCast(){
        OkHttpClient client = new OkHttpClient();
        String query = StringEscapeUtils.escapeHtml4(Integer.toString(this.getTmdbID()));

        try{
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + query + "/credits?language=en-US")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyODgxODIwZTI3OWFkZGMzN2MzYzNjOTUyYjJlM2VkNCIsInN1YiI6IjY0ZmI2YzY1ZmZjOWRlMGVlM2MzOTA5MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.egadbAWxCd6r9WYP6-0BQiSOoctQdoQ_jx283WyDMIw")
                .build();
        Response response = client.newCall(request).execute();
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(response.body().string());
        JsonToken token = parser.nextToken();

        //Fetching cast and adding them to person arraylist
        while(!"cast".equals(parser.getCurrentName())) token = parser.nextToken();
        if (token == JsonToken.FIELD_NAME && "cast".equals(parser.getCurrentName())) {
            //System.out.println("Cast - \n");
            token = parser.nextToken();
            token = parser.nextToken();
            token = parser.nextToken();// // Read left bracket i.e. [
            // Loop to print array elements until right bracket i.e ]
            for (int i = 0; i < 7; i++){
                Person tempPerson = new Person();
                parser.nextToken();
                if (token == JsonToken.END_ARRAY) break;
                while(!"gender".equals(parser.getCurrentName())) token = parser.nextToken();{
                    if (token == JsonToken.FIELD_NAME && "gender".equals(parser.getCurrentName()))
                        parser.nextToken();
                    //  if (token == JsonToken.VALUE_NUMBER_INT) {
                    switch (parser.getIntValue()) {
                        case 1:tempPerson.setGender("female");
                        case 2:tempPerson.setGender("male");
                    }
                    //       System.out.println("Gender:"+parser.getIntValue());
                    token = parser.nextToken();
                }
                //}
                while(!"known_for_department".equals(parser.getCurrentName())) token = parser.nextToken();

                token = parser.nextToken();
                // if (token == JsonToken.VALUE_STRING) {
                //        System.out.println("department: "+parser.getText());
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
                System.out.println();
                this.getCast().add(tempPerson);
            }
            System.out.println();
        }



        }
        catch(Error | IOException e){
            System.out.println(e);
            // e.printStackTrace();

        }

    }
    public void addDirector(){
        OkHttpClient client = new OkHttpClient();
        String query = StringEscapeUtils.escapeHtml4(Integer.toString(this.getTmdbID()));

        try{
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/" + query + "/credits?language=en-US")
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyODgxODIwZTI3OWFkZGMzN2MzYzNjOTUyYjJlM2VkNCIsInN1YiI6IjY0ZmI2YzY1ZmZjOWRlMGVlM2MzOTA5MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.egadbAWxCd6r9WYP6-0BQiSOoctQdoQ_jx283WyDMIw")
                    .build();
            Response response = client.newCall(request).execute();
            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createParser(response.body().string());
            JsonToken token = parser.nextToken();

        while(!"crew".equals(parser.getCurrentName())) token = parser.nextToken();
        if (token == JsonToken.FIELD_NAME && "crew".equals(parser.getCurrentName())) {

            //System.out.println("Cast - \n");
            token = parser.nextToken();
            token = parser.nextToken();
            token = parser.nextToken();// // Read left bracket i.e. [
            // Loop to print array elements until right bracket i.e ]
            while (token != JsonToken.END_ARRAY){
                while (!"id".equals(parser.getCurrentName())) token = parser.nextToken();
                token = parser.nextToken();
                int id = parser.getIntValue();
                //will eventually replace this code with a person method filling out their information
                while (!"name".equals(parser.getCurrentName())) token = parser.nextToken();
                token = parser.nextToken();
                Person director = new Person();
                String directorName = parser.getText();
                while (!"job".equals(parser.getCurrentName())) token = parser.nextToken();
                token = parser.nextToken();
                if (parser.getText().equals("Director")) {
                    director.setName(directorName);
                    this.setDirector(director);
                    break;
                }
            }
        }


        parser.close();
        }
        catch(Error | IOException e){
            System.out.println(e);
            // e.printStackTrace();

        }
    }
    @Override
    public void createRow(){
        {
            Connection connection = null;
            try
            {

                // create a database connection
                connection = DriverManager.getConnection("jdbc:sqlite:local.db");
                PreparedStatement statement = connection.prepareStatement("insert into content(title, overview, tmdb_id, content_type, total_episodes, watched_episodes, image_url)" +
                        " values(?,?,?,?,?,?,?)");
                statement.setQueryTimeout(30);  // set timeout to 30 sec.

                statement.setString(1,getTitle());
                statement.setString(2,getOverview());
                statement.setInt(3,getTmdbID());
                statement.setInt(4,getContentType());
                statement.setInt(5,1);
                statement.setInt(6,0);
                statement.setString(7,getImageURL());



                statement.executeUpdate();
                ResultSet rs = connection.createStatement().executeQuery("select * from content order by id desc limit 1");
                while(rs.next())
                {
                    // read the result set
                    System.out.println("title = " + rs.getString("title"));
                    System.out.println("id = " + rs.getInt("id"));
                    setID(rs.getInt("id"));
                    System.out.println("overview = " + rs.getString("overview"));
                    System.out.println("tmdb_ID = " + rs.getFloat("tmdb_id"));
                }
                //makeImageLocal();
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
    }

    /**
     * Reads from local database and applies to local object
     * @param id
     */
    public void readRow(int id){
        Connection connection = null;
        try
        {

            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:local.db");
            //PreparedStatement statement = connection.prepareStatement("insert into content(title, overview, tmdb_id, content_type, total_episodes, watched_episodes, image_url)" +
            //        " values(?,?,?,?,?,?,?)");
            PreparedStatement statement = connection.prepareStatement("select * from content where id=?");
            statement.setInt(1,id);
            statement.setQueryTimeout(30);  // set timeout to 30 sec.




           // statement.executeQuery();
            ResultSet rs = statement.executeQuery();
            while(rs.next())
            {
                // read the result set
                System.out.println("title = " + rs.getString("title"));
                setTitle(rs.getString("title"));
                System.out.println("id = " + rs.getInt("id"));
                setID(rs.getInt("id"));
                System.out.println("overview = " + rs.getString("overview"));
                setOverview(rs.getString("overview"));
                System.out.println("tmdb_ID = " + rs.getFloat("tmdb_id"));
                setTmdbID((int) rs.getFloat("tmdb_id"));

            }
            //makeImageLocal();
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

    @Override
    public String getDetails(){
        String str = "Movie Details:\n";
        str += super.getDetails();
        str += "\nDirector: "+ director.getName();
        return str;

    }

    public void makeImageLocal(){
        //URL url = new URL("https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg");
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url("https://image.tmdb.org/t/p/w500/"+ getImageURL())
                .get()
                .build();

        try{
            Response response = client.newCall(request).execute();
            OutputStream out = new FileOutputStream("./images/"+getID()+"/poster.jpg");
            out.write(response.body().bytes());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        } {

        }

    }




}
