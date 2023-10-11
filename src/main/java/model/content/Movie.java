package model.content;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import model.Person.Person;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import okhttp3.OkHttpClient;
import okhttp3.*;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.databind.*;

public class Movie extends ContentBase {

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


/*    public static void searchTMDB(String title){

        OkHttpClient client = new OkHttpClient();
        String query = StringEscapeUtils.escapeHtml4(title);


        Request request = new Request.Builder()
                .url(".url("https://api.themoviedb.org/3/movie/680?language=en-US")"+ query+"&include_adult=false&language=en-US&page=1")
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

    }*/
    public static Movie generateMovie(String tmdbID){


        OkHttpClient client = new OkHttpClient();
        String query = StringEscapeUtils.escapeHtml4(tmdbID);

        Movie tempMovie = new Movie();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + query + "?language=en-US")
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
            while(!"id".equals(parser.getCurrentName()) ) token = parser.nextToken();
            //extra parses are to avoid other fields named id
            token = parser.nextToken();
            token = parser.nextToken();
            while(!"id".equals(parser.getCurrentName()) ) token = parser.nextToken();
            token = parser.nextToken();
            token = parser.nextToken();
            while(!"id".equals(parser.getCurrentName()) ) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "id".equals(parser.getCurrentName())) {
                token = parser.nextToken();
                if (token == JsonToken.VALUE_NUMBER_INT) {
                    System.out.println("ID : " + parser.getIntValue());
                    tempMovie.setTmdbID(parser.getIntValue());
                }
            }
            while(!"original_title".equals(parser.getCurrentName()) ) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "original_title".equals(parser.getCurrentName())) {
                token = parser.nextToken();
                if (token == JsonToken.VALUE_STRING) {
                    System.out.println("original_title : " + parser.getText());
                    tempMovie.setTitle(parser.getText());
                }
            }
            while(!"overview".equals(parser.getCurrentName()) ) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "overview".equals(parser.getCurrentName())) {
                token = parser.nextToken();
                if (token == JsonToken.VALUE_STRING) {
                    System.out.println("overview : " + parser.getText());
                    tempMovie.setSummary(parser.getText());
                }
            }
            while(!"release_date".equals(parser.getCurrentName()) ) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "release_date".equals(parser.getCurrentName())) {
                token = parser.nextToken();
                if (token == JsonToken.VALUE_STRING) {
                    System.out.println("release_date : " + parser.getText());
                    LocalDate releaseDate = LocalDate.parse(parser.getText());
                    tempMovie.setReleaseDate(releaseDate);
                }
            }
            //close the parser
            parser.close();

            request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/" + query + "/credits?language=en-US")
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyODgxODIwZTI3OWFkZGMzN2MzYzNjOTUyYjJlM2VkNCIsInN1YiI6IjY0ZmI2YzY1ZmZjOWRlMGVlM2MzOTA5MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.egadbAWxCd6r9WYP6-0BQiSOoctQdoQ_jx283WyDMIw")
                    .build();
            response = client.newCall(request).execute();
            factory = new JsonFactory();
            parser = factory.createParser(response.body().string());
            token = parser.nextToken();



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
                    tempMovie.getCast().add(tempPerson);
                }
                System.out.println();
            }

//            System.out.println(root.fieldNames().next());
//            temp.setTitle( root.get("original_title").asText());;
//            temp.setTmdbID(root.get("id").asInt());
            parser.close();

// can modify as well: this adds child Object as property 'other', set property 'type'
            //root.with("other").put("type", "student");
            //String json = mapper.writeValueAsString(root);
            return tempMovie;
        }
        catch(Error | IOException e){
            System.out.println(e);
           // e.printStackTrace();

        }

        return tempMovie;

    }
}
