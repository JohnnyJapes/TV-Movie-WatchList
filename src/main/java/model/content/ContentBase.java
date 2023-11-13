package model.content;

import model.Person.Person;
import model.connectors.CastMember;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ContentBase {

    protected String title, overview, imageURL; //imageLocation is URL, currently undecided how images will be handled, local image location is constructed with id
    //ID should refer to a local database ID
    private int tmdbID, ID;    //ID should refer to a local database ID
    private LocalDate releaseDate;
    private float userRating;
    private ArrayList<CastMember> cast;
    protected int watched;

    protected ArrayList<Person> topCrew;          //For movies this is the director, for tv this is the creator(s)

    public int contentType;         //1 for movies, 2 for TV

    public ContentBase() {
        cast = new ArrayList<CastMember>();
        topCrew = new ArrayList<Person>();
        ID = -1;
    }

    public ContentBase(int ID){
        //call function to fetch info from database
        cast = new ArrayList<CastMember>();
        topCrew = new ArrayList<Person>();
        this.tmdbID = ID;
    }

    public ContentBase(String title, String overview, String imageLocation, int tmdbID, int ID, LocalDate releaseDate, float userRating, ArrayList<CastMember> cast) {
        this.title = title;
        this.overview = overview;
        this.imageURL = imageLocation;
        this.tmdbID = tmdbID;
        this.ID = ID;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
        this.cast = cast;
    }

    /**
     * Gets title.
     *
     * @return java.lang.String, value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method to set title.
     *
     * @param title java.lang.String - title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets contentType.
     *
     * @return int, value of contentType
     */
    public int getContentType() {
        return contentType;
    }

    /**
     * Gets summary.
     *
     * @return java.lang.String, value of summary
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Method to set summary.
     *
     * @param overview java.lang.String - summary
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * Gets releaseDate.
     *
     * @return java.time.LocalDate, value of releaseDate
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * Method to set releaseDate.
     *
     * @param releaseDate java.time.LocalDate - releaseDate
     */
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    /**
     * Method to set releaseDate.  Parses a string to a LocalDate object
     *
     * @param releaseDate String - releaseDate
     */
    public void setReleaseDate(String releaseDate) {

        LocalDate tempDate = LocalDate.parse(releaseDate);
        this.releaseDate = tempDate;
    }


    /**
     * Gets userRating.
     *
     * @return float, value of userRating
     */
    public float getUserRating() {
        return userRating;
    }

    /**
     * Method to set userRating.
     *
     * @param userRating float - userRating
     */
    public void setUserRating(float userRating) {
        this.userRating = userRating;
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
     * Gets cast.
     *
     * @return java.util.ArrayList<model.Person.Person>, value of cast
     */
    public ArrayList<CastMember> getCast() {
        return cast;
    }

    /**
     * Method to set cast.
     *
     * @param cast java.util.ArrayList<model.Person.Person> - cast
     */
    public void setCast(ArrayList<CastMember> cast) {
        this.cast = cast;
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

    /**
     * Gets topCrew.
     *
     * @return java.util.ArrayList<model.Person.Person>, value of topCrew
     */
    public ArrayList<Person> getTopCrew() {
        return topCrew;
    }

    /**
     * Method to set contentType.
     *
     * @param contentType int - contentType
     */
    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    /**
     * Method to set topCrew.
     *
     * @param topCrew java.util.ArrayList<model.Person.Person> - topCrew
     */
    public void setTopCrew(ArrayList<Person> topCrew) {
        this.topCrew = topCrew;
    }

    //CRUD

    public void createTable(){
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:local.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists content");

            String[] strColumns = {"title","overview","image_url", "releaseDate"};
            String[] intColumns = {"tmdb_id, content_type", "total_episodes", "watched_episodes, director_id"};
            String[] decimalColumns = {"tmdbRating"};
            String query = "create table if not exists content (id integer primary key asc";
            for (String str : strColumns){
                query += ", "+str + " text";
            }
            for (String str : intColumns){
                query += ", "+str + " int ";
            }
            for (String str : decimalColumns){
                query += ", "+str + " real";
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
        {
            Connection connection = null;
            try
            {
                // create a database connection
                connection = DriverManager.getConnection("jdbc:sqlite:local.db");
                PreparedStatement statement = connection.prepareStatement("insert into content(title, overview, tmdbID) values(?,?,?)");
                statement.setQueryTimeout(30);  // set timeout to 30 sec.

                statement.setString(1,this.title);
                statement.setString(2,this.overview);
                statement.setInt(3,tmdbID);



                statement.executeUpdate();
                ResultSet rs = connection.createStatement().executeQuery("select * from content");
                while(rs.next())
                {
                    // read the result set
                    System.out.println("title = " + rs.getString("title"));
                    System.out.println("id = " + rs.getInt("id"));
                    System.out.println("overview = " + rs.getString("overview"));
                    System.out.println("tmdbID = " + rs.getFloat("tmdbid"));
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
    }
    public String getDetails(){
        String str = String.format("Title: %s\nOverview: %s\nRelease Date: %s", title, overview, releaseDate);


        return str;


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
            String path = "images/"+getID()+"/";

            File img = new File(path);
            img.mkdirs();
            path += "poster.jpg";

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
        System.out.println("Getting image, ID: " + getID());
        try {
            return new FileInputStream("images/"+getID()+"/poster.jpg");
        } catch (FileNotFoundException e) {
            System.out.println(e);
            makeImageLocal();
            //try again after making a local image
            return getImage(1);
        }
    }

    /**
     * Method returns a file input stream for use to display the local image, if failing, throws exception
     * @param count - indicates method has failed once
     * @return
     */
    private FileInputStream getImage(int count){


        try {
            return new FileInputStream("images/"+getID()+"/poster.jpg");
        } catch (FileNotFoundException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        String str= title;
        if (releaseDate != null)str += " (" + releaseDate.getYear()+") \n ";
        if (overview != null && overview.length() > 40) str += overview.substring(0, 40) + "...";
        return str;
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
                setImageURL(rs.getString("image_url"));
                setReleaseDate(rs.getString("releaseDate"));
                Person temp = new Person();
                temp.readRow(rs.getInt("director_id"));
                setDirector(temp);
                watched = rs.getInt("watched_episodes");
                contentType = rs.getInt("content_type");

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
    }

    /**
     * Gets watched.
     *
     * @return int, value of watched
     */
    public int getWatched() {
        return watched;
    }

    /**
     * Method to set watched.
     *
     * @param watched int - watched
     */
    public void setWatched(int watched) {
        this.watched = watched;
    }

}
