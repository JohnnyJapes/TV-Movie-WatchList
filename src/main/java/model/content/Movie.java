package model.content;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import model.Person.Person;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.TMDBcompatible;
import okhttp3.OkHttpClient;
import okhttp3.*;
import org.apache.commons.text.StringEscapeUtils;

public class Movie extends ContentBase implements TMDBcompatible {

    private final String contentType = "Movie";
    private Person director;

    public Movie(){
        super();
        director = new Person();
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
    getTMDBdetails(tmdbID);
    }

    /**
     * Gets contentType.
     *
     * @return java.lang.String, value of contentType
     */
    public String getContentType() {
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


        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/680?language=en-US"+ query+"&include_adult=false&language=en-US&page=1")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyODgxODIwZTI3OWFkZGMzN2MzYzNjOTUyYjJlM2VkNCIsInN1YiI6IjY0ZmI2YzY1ZmZjOWRlMGVlM2MzOTA5MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.egadbAWxCd6r9WYP6-0BQiSOoctQdoQ_jx283WyDMIw")
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());

        }
        catch(Error | IOException e){
            System.out.println(e);
            //e.printStackTrace();
        }
        Movie temp = new Movie();
        return temp;

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
            // De-serialize to an movie object

            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createParser(response.body().string());
            JsonToken token = parser.nextToken();
            // Read JSON object
//            while(!"id".equals(parser.getCurrentName()) ) token = parser.nextToken();
//            //extra parses are to avoid other fields named id
//            token = parser.nextToken();
//            token = parser.nextToken();
//            while(!"id".equals(parser.getCurrentName()) ) token = parser.nextToken();
//            token = parser.nextToken();
//            token = parser.nextToken();
            while(!"homepage".equals(parser.getCurrentName()) ) token = parser.nextToken(); //skip over irrelevant IDs
            while(!"id".equals(parser.getCurrentName()) ) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "id".equals(parser.getCurrentName())) {
                token = parser.nextToken();
                if (token == JsonToken.VALUE_NUMBER_INT) {
                    System.out.println("ID : " + parser.getIntValue());
                    this.setTmdbID(parser.getIntValue());
                }
            }
            while(!"original_title".equals(parser.getCurrentName()) ) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "original_title".equals(parser.getCurrentName())) {
                token = parser.nextToken();
                if (token == JsonToken.VALUE_STRING) {
                    System.out.println("original_title : " + parser.getText());
                    this.setTitle(parser.getText());
                }
            }
            while(!"overview".equals(parser.getCurrentName()) ) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "overview".equals(parser.getCurrentName())) {
                token = parser.nextToken();
                if (token == JsonToken.VALUE_STRING) {
                    System.out.println("overview : " + parser.getText());
                    this.setOverview(parser.getText());
                }
            }
            while(!"release_date".equals(parser.getCurrentName()) ) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "release_date".equals(parser.getCurrentName())) {
                token = parser.nextToken();
                if (token == JsonToken.VALUE_STRING) {
                    System.out.println("release_date : " + parser.getText());
                    LocalDate releaseDate = LocalDate.parse(parser.getText());
                    this.setReleaseDate(releaseDate);
                }
            }
            //close the parser
            parser.close();



        }
        catch(Error | IOException e){
            System.out.println(e);
           // e.printStackTrace();

        }
        addCast();
        addDirector();
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


}
