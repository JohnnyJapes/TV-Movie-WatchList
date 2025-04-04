package com.tv.tvmoviewatchlist.model;

import com.tv.tvmoviewatchlist.model.Person.Person;
import com.tv.tvmoviewatchlist.model.content.ContentBase;
import com.tv.tvmoviewatchlist.model.connectors.CastMember;

import java.sql.*;
import java.util.ArrayList;

public class ContentList {

    private ArrayList<ListEntry> listEntries; // holds list entries which link to contentBase
    private String listName;
    private int id;

    public ContentList(){
        listName = "Watching";
        listEntries = new ArrayList<ListEntry>();
    }
    public ContentList(ArrayList<ListEntry> listEntries, String listName) {
        this.listEntries = listEntries;
        this.listName = listName;
    }

    /**
     * Gets listEntries.
     *
     * @return java.util.ArrayList<model.ListEntry>, value of listEntries
     */
    public ArrayList<ListEntry> getListEntries() {
        return listEntries;
    }

    /**
     * Method to set listEntries.
     *
     * @param listEntries java.util.ArrayList<model.ListEntry> - listEntries
     */
    public void setListEntries(ArrayList<ListEntry> listEntries) {
        this.listEntries = listEntries;
    }

    /**
     * Gets listName.
     *
     * @return java.lang.String, value of listName
     */
    public String getListName() {
        return listName;
    }

    /**
     * Method to set listName.
     *
     * @param listName java.lang.String - listName
     */
    public void setListName(String listName) {
        this.listName = listName;
    }

    public void readContentTable(){
        {
            Connection connection = null;
            try
            {

                // create a database connection
                connection = DriverManager.getConnection(System.getProperty("dburl"));
                //PreparedStatement statement = connection.prepareStatement("insert into content(title, overview, tmdb_id, content_type, total_episodes, watched_episodes, image_url)" +
                //        " values(?,?,?,?,?,?,?)");
                PreparedStatement statement = connection.prepareStatement("select * from content JOIN  ");
                statement.setInt(1,id);
                statement.setQueryTimeout(30);  // set timeout to 30 sec.


                // statement.executeQuery();
                ResultSet rs = statement.executeQuery();
                while(rs.next())
                {
                    ContentBase content = new ContentBase();
                    // read the result set
                    System.out.println("title = " + rs.getString("title"));
                    content.setTitle(rs.getString("title"));
                    System.out.println("id = " + rs.getInt("id"));
                    content.setID(rs.getInt("id"));
                    System.out.println("overview = " + rs.getString("overview"));
                    content.setOverview(rs.getString("overview"));
                    System.out.println("tmdb_ID = " + rs.getFloat("tmdb_id"));
                    content.setTmdbID((int) rs.getFloat("tmdb_id"));
                    content.setImageURL(rs.getString("image_url"));
                    content.setReleaseDate(rs.getString("releaseDate"));
                    Person temp = new Person();
                    temp.readRow(rs.getInt("director_id"));
                    content.setDirector(temp);
                    content.setWatched(rs.getInt("watched_episodes"));
                    content.contentType = rs.getInt("content_type");

                    //makeImageLocal();
                    PreparedStatement castStatement = connection.prepareStatement("select * from castmembers where content_id=?");
                    castStatement.setInt(1,content.getID());
                    castStatement.setQueryTimeout(30);  // set timeout to 30 sec.



                    // statement.executeQuery();
                    ResultSet castRs = castStatement.executeQuery();
                    while(castRs.next())
                    {
                        CastMember member = new CastMember();
                        member.readRow(castRs.getInt("id"));
                        member.setContent(content);
                        content.getCast().add(member);

                    }
                    ListEntry entry = new ListEntry(content, 3, 0);

                    listEntries.add(entry);

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

    /**
     * Read from contentList table specified by listID
     * @param listID - 0 watching, 1 completed, 2 plan to watch, 3 abandoned
     */
    public void readList(int listID){

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(System.getProperty("dburl"));
            PreparedStatement statement = connection.prepareStatement("select * from listentries where list_id=? Order by rank asc");
            statement.setInt(1, listID);

            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                ListEntry entry = new ListEntry();
                entry.readData(rs);
                listEntries.add(entry);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Search current content List in all fields for search term
     * @param searchFilter
     * @return
     */
    public ContentList search(String searchFilter){
        ContentList searchResults = new ContentList();

        for (ListEntry entry : listEntries){
            Boolean found = false;
            ContentBase content = entry.getEntry();
            Person topCrew = content.getTopCrew().get(0);
            ArrayList<CastMember> cast = content.getCast();

            //search for search term, single loop just so break works
            //all to lower case
            for (int j = 0; j < 1; j++) {
                if (content.getTitle().toLowerCase().contains(searchFilter.toLowerCase())) {
                    found = true;
                    break;
                }
                if (topCrew.getName().toLowerCase().contains(searchFilter.toLowerCase())) {
                    found = true;
                    break;
                }
                for (int i = 0; i < cast.size(); i++) {
                    if (cast.get(i).getPerson().getName().toLowerCase().contains(searchFilter.toLowerCase())) {
                        found = true;
                        break;
                    }
                    if (cast.get(i).getCharacter().toLowerCase().contains(searchFilter.toLowerCase())) {
                        found = true;
                        break;
                    }
                }
            }
            if (found){
                searchResults.listEntries.add(entry);
            }
        }



        return searchResults;
    }
}
