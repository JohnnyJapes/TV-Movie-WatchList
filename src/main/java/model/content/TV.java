package model.content;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import model.Person.Person;
import model.TMDBcompatible;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class TV extends ContentBase implements TMDBcompatible {

    private final int contentType = 2; //1 for movies, 2 for tv shows
    //total episodes is the total number of episodes in a tv show.
    private int totalEpisodes, watchedEpisodes;
    //series creator
    private ArrayList<Person> creators;


    public TV(){
        super();
        creators = new ArrayList<Person>();
        totalEpisodes = 0;
        watchedEpisodes = 0;
    }
    public TV(int tmdbID){
        this();
        getTMDBdetails(tmdbID);
    }

    public TV(int totalEpisodes, int watchedEpisodes, ArrayList<Person> creators) {
        this.totalEpisodes = totalEpisodes;
        this.watchedEpisodes = watchedEpisodes;
        this.creators = creators;
    }

    public TV(int ID, int totalEpisodes, int watchedEpisodes, ArrayList<Person> creators) {
        super(ID);
        this.totalEpisodes = totalEpisodes;
        this.watchedEpisodes = watchedEpisodes;
        this.creators = creators;
    }

    public TV(String title, String summary, String imageLocation, int tmdbID, int ID, LocalDate releaseDate, float userRating, ArrayList<Person> cast, int totalEpisodes, int watchedEpisodes, ArrayList<Person> creators) {
        super(title, summary, imageLocation, tmdbID, ID, releaseDate, userRating, cast);
        this.totalEpisodes = totalEpisodes;
        this.watchedEpisodes = watchedEpisodes;
        this.creators = creators;
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
     * Gets totalEpisodes.
     *
     * @return int, value of totalEpisodes
     */
    public int getTotalEpisodes() {
        return totalEpisodes;
    }

    /**
     * Method to set totalEpisodes.
     *
     * @param totalEpisodes int - totalEpisodes
     */
    public void setTotalEpisodes(int totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }

    /**
     * Gets watchedEpisodes.
     *
     * @return int, value of watchedEpisodes
     */
    public int getWatchedEpisodes() {
        return watchedEpisodes;
    }

    /**
     * Method to set watchedEpisodes.
     *
     * @param watchedEpisodes int - watchedEpisodes
     */
    public void setWatchedEpisodes(int watchedEpisodes) {
        this.watchedEpisodes = watchedEpisodes;
    }

    /**
     * Gets creators.
     *
     * @return java.util.ArrayList<model.Person.Person>, value of creators
     */
    public ArrayList<Person> getCreators() {
        return creators;
    }

    /**
     * Method to set creators.
     *
     * @param creators java.util.ArrayList<model.Person.Person> - creators
     */
    public void setCreators(ArrayList<Person> creators) {
        this.creators = creators;
    }

    public void getTMDBdetails(int tmdbID){


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/tv/" + tmdbID + "?language=en-US")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyODgxODIwZTI3OWFkZGMzN2MzYzNjOTUyYjJlM2VkNCIsInN1YiI6IjY0ZmI2YzY1ZmZjOWRlMGVlM2MzOTA5MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.egadbAWxCd6r9WYP6-0BQiSOoctQdoQ_jx283WyDMIw")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if(response.code() != 200) throw new Error(response.code() + " Request error");
            // De-serialize to an movie object
            System.out.println("start TV Details");
            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createParser(response.body().string());
            JsonToken token = parser.nextToken();
            // Read JSON object
            token = parser.nextToken();
            while(!"first_air_date".equals(parser.getCurrentName()) ) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "first_air_date".equals(parser.getCurrentName())) {
                token = parser.nextToken();
                if (token == JsonToken.VALUE_STRING) {
                    //System.out.println("first_air_date : " + parser.getText());
                    LocalDate releaseDate = LocalDate.parse(parser.getText());
                    this.setReleaseDate(releaseDate);
                }
            }
            while(!"homepage".equals(parser.getCurrentName()) ) token = parser.nextToken(); //skip over irrelevant IDs
            while(!"id".equals(parser.getCurrentName()) ) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "id".equals(parser.getCurrentName())) {
                token = parser.nextToken();
                if (token == JsonToken.VALUE_NUMBER_INT) {
                    //System.out.println("ID : " + parser.getIntValue());
                    this.setTmdbID(parser.getIntValue());
                }
            }
            while(!"number_of_episodes".equals(parser.getCurrentName()) ) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "number_of_episodes".equals(parser.getCurrentName())) {
                token = parser.nextToken();
                if (token == JsonToken.VALUE_NUMBER_INT) {
                    //System.out.println("number_of_episodes : " + parser.getText());
                    this.setTotalEpisodes(parser.getIntValue());
                }
            }
            while(!"original_name".equals(parser.getCurrentName()) ) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "original_name".equals(parser.getCurrentName())) {
                token = parser.nextToken();

                if (token == JsonToken.VALUE_STRING) {
                    //System.out.println("name : " + parser.getText());
                    this.setTitle(parser.getText());
                }
            }
            while(!"overview".equals(parser.getCurrentName()) ) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "overview".equals(parser.getCurrentName())) {
                token = parser.nextToken();
                if (token == JsonToken.VALUE_STRING) {
                    //System.out.println("overview : " + parser.getText());
                    this.setOverview(parser.getText());
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
        addCreators();
        System.out.println("TV Show Title: " + this.getTitle() + "\n");
        System.out.println("-----------------------------"); //solely to make it easier to read testHarness


    }

    @Override
    public Object searchTMDB(String query) {
        OkHttpClient client = new OkHttpClient();
        query = StringEscapeUtils.escapeHtml4(query);
        ArrayList<TV> shows = new ArrayList<>();

        System.out.println("Query: " + query);
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/search/tv?query="+ query +"&include_adult=false&language=en-US&page=1")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyODgxODIwZTI3OWFkZGMzN2MzYzNjOTUyYjJlM2VkNCIsInN1YiI6IjY0ZmI2YzY1ZmZjOWRlMGVlM2MzOTA5MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.egadbAWxCd6r9WYP6-0BQiSOoctQdoQ_jx283WyDMIw")
                .build();


        try {
            Response response = client.newCall(request).execute();
            // De-serialize to an movie object
            System.out.println("TV Show Search Results ----------------------------");
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
                    TV tempTV = new TV();
                    token = parser.nextToken();
                    if (token == JsonToken.END_ARRAY) break;
                    while (!"id".equals(parser.getCurrentName())) token = parser.nextToken();
                    if (token == JsonToken.FIELD_NAME && "id".equals(parser.getCurrentName())) {
                        token = parser.nextToken();
                        if (token == JsonToken.VALUE_NUMBER_INT) {
                            System.out.println("ID : " + parser.getIntValue());
                            tempTV.setTmdbID(parser.getIntValue());
                        }
                    }

                    while (!"original_name".equals(parser.getCurrentName())) token = parser.nextToken();
                    if (token == JsonToken.FIELD_NAME && "original_name".equals(parser.getCurrentName())) {
                        token = parser.nextToken();
                        if (token == JsonToken.VALUE_STRING) {
                            System.out.println("original_name : " + parser.getText());
                            tempTV.setTitle(parser.getText());
                        }
                    }
                    while (!"overview".equals(parser.getCurrentName())) token = parser.nextToken();
                    if (token == JsonToken.FIELD_NAME && "overview".equals(parser.getCurrentName())) {
                        token = parser.nextToken();
                        if (token == JsonToken.VALUE_STRING) {
                            System.out.println("overview : " + parser.getText());
                            tempTV.setOverview(parser.getText());
                        }
                    }
                    while (!"first_air_date".equals(parser.getCurrentName())) token = parser.nextToken();
                    if (token == JsonToken.FIELD_NAME && "release_date".equals(parser.getCurrentName())) {
                        token = parser.nextToken();
                        if (token == JsonToken.VALUE_STRING) {
                            System.out.println("release_date : " + parser.getText());
                            if (parser.getText() != "")
                            {
                                LocalDate releaseDate = LocalDate.parse(parser.getText());
                                tempTV.setReleaseDate(releaseDate);
                            }
                        }
                    }
                    shows.add(tempTV);

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

        return shows;

    }


    public void addCast(){
        OkHttpClient client = new OkHttpClient();
        String query = StringEscapeUtils.escapeHtml4(Integer.toString(this.getTmdbID()));

        try{
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/tv/" + query + "/credits?language=en-US")
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
    public void addCreators(){
        OkHttpClient client = new OkHttpClient();
        String query = StringEscapeUtils.escapeHtml4(Integer.toString(this.getTmdbID()));

        try{
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/tv/" + query + "?language=en-US")
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyODgxODIwZTI3OWFkZGMzN2MzYzNjOTUyYjJlM2VkNCIsInN1YiI6IjY0ZmI2YzY1ZmZjOWRlMGVlM2MzOTA5MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.egadbAWxCd6r9WYP6-0BQiSOoctQdoQ_jx283WyDMIw")
                    .build();
            Response response = client.newCall(request).execute();
            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createParser(response.body().string());
            JsonToken token = parser.nextToken();
            System.out.println("Get Creators");
            //Fetching cast and adding them to person arraylist
            while(!"created_by".equals(parser.getCurrentName())) token = parser.nextToken();
            if (token == JsonToken.FIELD_NAME && "created_by".equals(parser.getCurrentName())) {
                //System.out.println("Cast - \n");
                token = parser.nextToken();
                // // Read left bracket i.e. [
                // Loop to print array elements until right bracket i.e ]
                for (int i = 0; i < 5; i++){
                    token = parser.nextToken();
                    Person tempPerson = new Person();
                    if (token == JsonToken.END_ARRAY) break;

                    //}
                    while(!"id".equals(parser.getCurrentName())) token = parser.nextToken();
                    token = parser.nextToken();
                    // if (token == JsonToken.VALUE_STRING) {
                    //        System.out.println("department: "+parser.getText());
                    System.out.println("Person ID: "+parser.getIntValue());
                    tempPerson.setTmdbID(parser.getIntValue());

                    // }


                    while(!"name".equals(parser.getCurrentName())) token = parser.nextToken();
                    //System.out.println("name");
                    token = parser.nextToken();
                    if (token == JsonToken.VALUE_STRING) {
                        System.out.println("Name: "+parser.getText());
                        tempPerson.setName(parser.getText());
                        token = parser.nextToken();
                    }
                    while(!"gender".equals(parser.getCurrentName())) token = parser.nextToken();

                        if (token == JsonToken.FIELD_NAME && "gender".equals(parser.getCurrentName())){
                            parser.nextToken();
                        //  if (token == JsonToken.VALUE_NUMBER_INT) {

                        switch (parser.getIntValue()) {
                            case 1:tempPerson.setGender("female");
                            case 2:tempPerson.setGender("male");
                        }
                        //       System.out.println("Gender:"+parser.getIntValue());
                        token = parser.nextToken();
                    }
                        //moves parser to the end of the object, so if the end of array is next it will be caught
                    while(token != JsonToken.END_OBJECT) token = parser.nextToken();

                    //System.out.println(parser.getCurrentName());
                    System.out.println();
                    this.getCreators().add(tempPerson);
                }
                System.out.println();
            }


            parser.close();
        }
        catch(Error | IOException e){
            System.out.println(e);
             e.printStackTrace();

        }
    }

    @Override
    public String getDetails(){
        String str = "TV Show Details:\n";
        str += super.getDetails();
        str += "\nCreators: ";
        for (int i = 0; i < creators.size(); i++){
            if (creators.size() - i > 1) str += creators.get(i).getName() + ", ";
            else str += creators.get(i).getName();
        }
        str += "\nTotal Episodes: " + totalEpisodes;
        return str;

    }

}
