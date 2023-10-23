package model.content;

import model.Person.Person;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ContentBase {

    protected String title, overview, imageURL; //imageLocation is URL, currently undecided how images will be handled, local image location is constructed with id
    //ID should refer to a local database ID
    private int tmdbID, ID;    //ID should refer to a local database ID
    private LocalDate releaseDate;
    private float userRating;
    private ArrayList<Person> cast;

    public ContentBase() {
        cast = new ArrayList<Person>();
    }

    public ContentBase(int ID){
        //call function to fetch info from database

    }

    public ContentBase(String title, String overview, String imageLocation, int tmdbID, int ID, LocalDate releaseDate, float userRating, ArrayList<Person> cast) {
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
    public ArrayList<Person> getCast() {
        return cast;
    }

    /**
     * Method to set cast.
     *
     * @param cast java.util.ArrayList<model.Person.Person> - cast
     */
    public void setCast(ArrayList<Person> cast) {
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
            String[] intColumns = {"tmdb_id, content_type", "total_episodes", "watched_episodes"};
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
}
