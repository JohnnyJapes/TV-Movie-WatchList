package model;

import model.Person.Person;
import model.connectors.CastMember;
import model.content.Movie;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Tables {

    /**
     * Method creates the person table in the sqlite DB. If it already exists it is dropped and remade
     */
    public static void createPersonTable(){
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

    /**
     * Method to create cast member table
     */
    public static void createCastMemberTable(){
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:local.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists castmembers");

            String[] strColumns = {"character"};
            String[] intColumns = {"content_id, person_id, rank"};
            String query = "create table if not exists castMembers (id integer primary key asc";
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

    /**
     * Create table for content(movies/TV)
     */
    public static void createContentTable(){
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


    /**
     * Method to create list entry table.
     */
    public static void createListEntryTable(){
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:local.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists listentries");

            String[] intColumns = {"list_id, content_id, rank"};
            String query = "create table if not exists listentries (id integer primary key asc";
            for (String str : intColumns) {
                query += ", " + str + " int ";
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

    /**
     * Method will empty image storage and recreate database; Destroying any data inside it. Will also add filler data
     */
    public static void refreshDatabase(){
        deleteDirectory(new File("images"));
        Movie temp = new Movie();
        createContentTable();
        createCastMemberTable();
        createListEntryTable();
        createPersonTable();
        temp.getTMDBdetails(680);
        temp.createRow();
        ListEntry entry = new ListEntry(temp, 1, 0);
        Movie temp2 = new Movie(988);
        entry.createRow();
        temp2.createRow();
        ListEntry entry2 = new ListEntry(temp2, 2, 0);
        entry2.createRow();
    }

    private static boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

}
