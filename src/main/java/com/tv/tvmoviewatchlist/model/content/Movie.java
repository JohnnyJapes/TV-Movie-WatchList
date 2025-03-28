package com.tv.tvmoviewatchlist.model.content;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.tv.tvmoviewatchlist.model.Person.Person;

import java.io.*;
import java.sql.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;

import com.tv.tvmoviewatchlist.model.TMDBcompatible;
import com.tv.tvmoviewatchlist.model.connectors.CastMember;
import okhttp3.OkHttpClient;
import okhttp3.*;
import org.apache.commons.text.StringEscapeUtils;

public class Movie extends ContentBase implements TMDBcompatible {


    private Person director;

    public Movie(){
        super();
        director = new Person();
        watchedEpisodes = 0;
        contentType = 1; //1 for movies, 2 for TV shows
    }

    public Movie(Person director) {
        //topCrew.set(0, director);
        this.director = director;
        contentType = 1; //1 for movies, 2 for TV shows
    }

    /**
     * Uses tmdb ID
     * @param ID
     * @param director
     */
    public Movie(int ID, Person director) {
        super(ID);
        topCrew.set(0, director);
        this.director = director;
        contentType = 1; //1 for movies, 2 for TV shows
    }

    public Movie(String title, String summary, String imageLocation, int tmdbID, int ID, LocalDate releaseDate, float userRating, ArrayList<CastMember> cast, Person director) {
        super(title, summary, imageLocation, tmdbID, ID, releaseDate, userRating, cast);
        this.director = director;
        topCrew.set(0, director);
        contentType = 1; //1 for movies, 2 for TV shows
    }
    public Movie(int tmdbID){
        super();
        getTMDBdetails(tmdbID);
        contentType = 1; //1 for movies, 2 for TV shows
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
        if (topCrew.size() == 0) {
            topCrew.add(director);
        }
        else  topCrew.set(0, director);
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
                .addHeader("Authorization", System.getProperty("token"))
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
                    while (!"id".equals(parser.getCurrentName()) && !"total_results".equals(parser.getCurrentName())) token = parser.nextToken();
                    if ("total_results".equals(parser.getCurrentName())) break;
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
        //System.out.println("token: " + System.getProperty("token"));

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + tmdbID + "?language=en-US")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", System.getProperty("token"))
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
            while(!"poster_path".equals(parser.getCurrentName()) ) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "poster_path".equals(parser.getCurrentName())) {
                token = parser.nextToken();
                if (token == JsonToken.VALUE_STRING) {
                    // System.out.println("overview : " + parser.getText());
                    this.setImageURL(parser.getText());
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
    /*public void addCast(){
        OkHttpClient client = new OkHttpClient();
        String query = StringEscapeUtils.escapeHtml4(Integer.toString(this.getTmdbID()));

        try{
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + query + "/credits?language=en-US")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", System.getProperty("token"))
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
                CastMember tempPerson = new CastMember();
                parser.nextToken();
                if (token == JsonToken.END_ARRAY) break;
                while(!"gender".equals(parser.getCurrentName())) token = parser.nextToken();{
                    if (token == JsonToken.FIELD_NAME && "gender".equals(parser.getCurrentName()))
                        parser.nextToken();
                    //  if (token == JsonToken.VALUE_NUMBER_INT) {
                    int gen = parser.getIntValue();
                    tempPerson.getPerson().setGender(gen);
                    //       System.out.println("Gender:"+parser.getIntValue());
                    token = parser.nextToken();
                }
                while(!"id".equals(parser.getCurrentName()) ) token = parser.nextToken();
                if (token == JsonToken.FIELD_NAME && "id".equals(parser.getCurrentName())) {
                    token = parser.nextToken();
                    if (token == JsonToken.VALUE_NUMBER_INT) {
                        // System.out.println("ID : " + parser.getIntValue());
                        tempPerson.getPerson().setTmdbID(parser.getIntValue());
                    }
                }
                //}
                while(!"known_for_department".equals(parser.getCurrentName())) token = parser.nextToken();

                token = parser.nextToken();
                // if (token == JsonToken.VALUE_STRING) {
                //        System.out.println("department: "+parser.getText());
                tempPerson.getPerson().setKnownFor(parser.getText());

                // }

                while(!"name".equals(parser.getCurrentName())) token = parser.nextToken();

                token = parser.nextToken();
                if (token == JsonToken.VALUE_STRING) {
                    //       System.out.println("Name: "+parser.getText());
                    tempPerson.getPerson().setName(parser.getText());
                    token = parser.nextToken();
                }
                while (!"profile_path".equals(parser.getCurrentName())) token = parser.nextToken();
                token = parser.nextToken();
                if (token == JsonToken.VALUE_STRING) {
                    //       System.out.println("Name: "+parser.getText());
                    tempPerson.getPerson().setImageURL(parser.getText());
                    token = parser.nextToken();
                }
                while(!"character".equals(parser.getCurrentName())) token = parser.nextToken();

                token = parser.nextToken();
                if (token == JsonToken.VALUE_STRING) {
                    //       System.out.println("Name: "+parser.getText());
                    tempPerson.setCharacter(parser.getText());
                    token = parser.nextToken();
                }
                while(!"order".equals(parser.getCurrentName())) token = parser.nextToken();

                token = parser.nextToken();
                if (token == JsonToken.VALUE_NUMBER_INT) {
                    //       System.out.println("Name: "+parser.getText());
                    tempPerson.setOrder(parser.getIntValue());
                    token = parser.nextToken();
                }
                //System.out.println(parser.getCurrentName());
                System.out.println();
                tempPerson.setContent(this);
                this.getCast().add(tempPerson);
            }
            System.out.println();
        }



        }
        catch(Error | IOException e){
            System.out.println(e);
            // e.printStackTrace();

        }

    }*/
    public void addDirector(){
        OkHttpClient client = new OkHttpClient();
        String query = StringEscapeUtils.escapeHtml4(Integer.toString(this.getTmdbID()));

        try{
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/" + query + "/credits?language=en-US")
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", System.getProperty("token"))
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

    /**
     * Checks if record is a duplicate, if no, adds to list/database
     */
    public void addFromSearch(){
        int found = searchLocalDB(getTmdbID());
        if (found < 0) {
            getTMDBdetails(getTmdbID());
            createRow();
        }
        else {
            setID(found);
            this.readRow(found);
        }
        return;
    }

    /**
     * Add new entry to content table
     */
    @Override
    public void createRow(){
        {
            Connection connection = null;
            getDirector().createRow();
            try
            {
                // create a database connection
                connection = DriverManager.getConnection(System.getProperty("dburl"));
                PreparedStatement statement = connection.prepareStatement("insert into content(title, overview, tmdb_id, content_type, total_episodes, watched_episodes, image_url, releaseDate, director_id)" +
                        " values(?,?,?,?,?,?,?,?,?)");
                statement.setQueryTimeout(30);  // set timeout to 30 sec.

                statement.setString(1,getTitle());
                statement.setString(2,getOverview());
                statement.setInt(3,getTmdbID());
                statement.setInt(4,getContentType());
                statement.setInt(5,1);
                statement.setInt(6,0);
                statement.setString(7,getImageURL());
                statement.setString(8, getReleaseDate().toString());
                statement.setInt(9, getDirector().getID());



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
                for (int i = 0; i < 5; i++){
                    getCast().get(i).createRow();
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
            connection = DriverManager.getConnection(System.getProperty("dburl"));
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
                setImageURL(rs.getString("image_url"));
                setReleaseDate(rs.getString("releaseDate"));
                Person temp = new Person();
                temp.readRow(rs.getInt("director_id"));
                setDirector(temp);
                watchedEpisodes = rs.getInt("watched_episodes");
                setTotalEpisodes(1);

            }
            //makeImageLocal();
            statement = connection.prepareStatement("select * from castmembers where content_id=?");
            statement.setInt(1,this.getID());
            statement.setQueryTimeout(30);  // set timeout to 30 sec.


            // statement.executeQuery();
            rs = statement.executeQuery();
            while(rs.next())
            {
                CastMember member = new CastMember();
                member.readRow(rs.getInt("id"));
                member.setContent(this);
                getCast().add(member);

            }
            statement = connection.prepareStatement("select * from castmembers where content_id=?");
            statement.setInt(1,this.getID());
            statement.setQueryTimeout(30);  // set timeout to 30 sec.


            // statement.executeQuery();
            rs = statement.executeQuery();

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



    @Override
    public String toString() {
        return super.toString();
    }
}
