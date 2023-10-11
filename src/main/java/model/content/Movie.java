package model.content;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import model.Person.Person;

import java.io.IOException;
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

    public Movie(String title, String summary, String imageLocation, int tmdbID, int ID, Date releaseDate, float userRating, ArrayList<Person> cast, Person director) {
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

        Movie temp = new Movie();
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + query + "?language=en-US")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyODgxODIwZTI3OWFkZGMzN2MzYzNjOTUyYjJlM2VkNCIsInN1YiI6IjY0ZmI2YzY1ZmZjOWRlMGVlM2MzOTA5MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.egadbAWxCd6r9WYP6-0BQiSOoctQdoQ_jx283WyDMIw")
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
            ObjectMapper mapper = new ObjectMapper();

// De-serialize to an object
            //Movie temp = mapper.readValue(response.body().string(), Movie.class);
            //System.out.println(temp.getTitle()); //John

            // can be read as generic JsonNode, if it can be Object or Array; or,
            // if known to be Object, as ObjectNode, if array, ArrayNode etc:
            JsonNode root = mapper.readTree(response.body().string());
            Object jsonTemp = new JsonParser().par;
            System.out.println(root.fieldNames().next());
            temp.setTitle( root.get("original_title").asText());;
            temp.setTmdbID(root.get("id").asInt());

// can modify as well: this adds child Object as property 'other', set property 'type'
            //root.with("other").put("type", "student");
            //String json = mapper.writeValueAsString(root);
            return temp;
        }
        catch(Error | IOException e){
            System.out.println(e);
            //e.printStackTrace();
        }

        return temp;

    }
}
