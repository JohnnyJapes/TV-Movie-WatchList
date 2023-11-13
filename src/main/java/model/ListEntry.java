package model;

import model.content.ContentBase;
import model.content.Movie;

import java.sql.*;

public class ListEntry {

    private ContentBase entry; //actual piece of content for that entry
    private int listRank; //rank/order in the default list sorting
    public int listID;//0 watching, 1 completed, 2 plan to watch, 3 abandoned


    public ListEntry(){
        listID = -1;
        listRank = -1;
        entry = new ContentBase();
    }



    /**
     * Will create new object with these properites. Will NOT create an entry in the database. Use createRow()
     * @param entry
     * @param listID - 0 = watching, 1 completed, 2 plan to watch, 3 abandoned
     */
    public ListEntry(ContentBase entry,  int listID) {
        this.entry = entry;
        this.listID = listID;
        listRank = -1;
    }


    /**
     * Will create new object with these properites. Will NOT create an entry in the database. Use createRow()
     * @param entry
     * @param listRank
     * @param listID - 0 = watching, 1 completed, 2 plan to watch, 3 abandoned
     */
    public ListEntry(ContentBase entry, int listRank, int listID) {
        this.entry = entry;
        this.listRank = listRank;
        this.listID = listID;
    }

    /**
     * Gets entry.
     *
     * @return model.content.ContentBase, value of entry
     */
    public ContentBase getEntry() {
        return entry;
    }

    /**
     * Method to set entry.
     *
     * @param entry model.content.ContentBase - entry
     */
    public void setEntry(ContentBase entry) {
        this.entry = entry;
    }

    /**
     * Gets listRank.
     *
     * @return int, value of listRank
     */
    public int getListRank() {
        return listRank;
    }

    /**
     * Method to set listRank.
     *
     * @param listRank int - listRank
     */
    public void setListRank(int listRank) {
        this.listRank = listRank;
    }

    /**
     * Gets listID.
     *
     * @return int, value of listID
     */
    public int getListID() {
        return listID;
    }

    /**
     * Method to set listID.
     *
     * @param listID int - listID
     */
    public void setListID(int listID) {
        this.listID = listID;
    }

    @Override
    public String toString() {
        return listRank + ". " + entry.getTitle();

    }

    //CRUD operations
    public void createRow(){

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:local.db");
            PreparedStatement statement = connection.prepareStatement("insert into listentries(list_id, content_id, rank) values(?,?,?)");
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.setInt(1,listID);
            if (getEntry().getID() < 0){
                getEntry().createRow();
            }

            statement.setInt(2,getEntry().getID());
            if(getListRank() < 0){
                ResultSet rsLast = connection.createStatement().executeQuery("select rank from listentries order by rank desc limit 1");
                int lastRank = rsLast.getInt("rank");
                statement.setInt(3, lastRank+1);
            }
            else statement.setInt(3,getListRank());




            statement.executeUpdate();
            ResultSet rs = connection.createStatement().executeQuery("select * from listentries");
            while(rs.next())
            {
                Movie temp = new Movie();
                // read the result set
                System.out.println("list_id = " + rs.getInt("list_id"));
                System.out.println("content_id = " + rs.getInt("content_id"));
                temp.readRow(rs.getInt("content_id"));
                System.out.println("Entry Title = " + temp.getTitle());
                System.out.println("Rank = " + rs.getInt("rank"));

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

    public void readRow(int id){
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:local.db");
            PreparedStatement statement = connection.prepareStatement("select * from listentries where content_id=?");
            statement.setInt(1,getEntry().getID());

            ResultSet rs = statement.executeQuery();
            while(rs.next())
            {
                ContentBase temp = new ContentBase();
                // read the result set
                System.out.println("list_id = " + rs.getInt("list_id"));
                setListID(rs.getInt("list_id"));
                System.out.println("content_id = " + rs.getInt("content_id"));
                temp.readRow(rs.getInt("content_id"));
                System.out.println("Entry Title = " + temp.getTitle());
                System.out.println("Rank = " + rs.getInt("rank"));
                setEntry(temp);
                setListRank(rs.getInt("rank"));

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


    public void readData(ResultSet rs){
        ContentBase temp = new ContentBase();

        try {
            // read the result set
            System.out.println("list_id = " + rs.getInt("list_id"));
            setListID(rs.getInt("list_id"));
            System.out.println("content_id = " + rs.getInt("content_id"));
            temp.readRow(rs.getInt("content_id"));
            System.out.println("Entry Title = " + temp.getTitle());
            System.out.println("Rank = " + rs.getInt("rank"));
            setEntry(temp);
            setListRank(rs.getInt("rank"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateRow(ListEntry entry){

    }

    public void deleteRow(ListEntry entry){

    }



}
