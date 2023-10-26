package model.connectors;

import model.Person.Person;
import model.content.ContentBase;

import java.sql.*;

/**
 * This class will hold the character for a piece of content along with the person portraying them.
 */
public class CastMember {

    private Person person;
    private String character;
    private ContentBase content;
    private int id;
    private int order;

    public CastMember(){
        person = new Person();
        character = "";
        content = new ContentBase();
    }

    public CastMember(Person person, String character, ContentBase content) {
        this.person = person;
        this.character = character;
        this.content = content;
    }

    /**
     * Gets person.
     *
     * @return model.Person.Person, value of person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Method to set person.
     *
     * @param person model.Person.Person - person
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Gets character.
     *
     * @return java.lang.String, value of character
     */
    public String getCharacter() {
        return character;
    }

    /**
     * Method to set character.
     *
     * @param character java.lang.String - character
     */
    public void setCharacter(String character) {
        this.character = character;
    }

    /**
     * Gets content.
     *
     * @return model.content.ContentBase, value of content
     */
    public ContentBase getContent() {
        return content;
    }

    /**
     * Method to set content.
     *
     * @param content model.content.ContentBase - content
     */
    public void setContent(ContentBase content) {
        this.content = content;
    }

    /**
     * Gets id.
     *
     * @return int, value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Method to set id.
     *
     * @param id int - id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets rank.
     *
     * @return int, value of rank
     */
    public int getOrder() {
        return order;
    }

    /**
     * Method to set order.
     *
     * @param order int - order
     */
    public void setOrder(int order) {
        this.order = order;
    }

    public void createRow(){
        createRow(content.getID());
    }

    public static void createTable(){
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
    public void createRow(int contentID){
        person.createRow();
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:local.db");
            PreparedStatement statement = connection.prepareStatement("insert into CastMembers(content_id, person_id, character, rank)" +
                    " values(?,?,?,?)");
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.setInt(1,contentID);
            statement.setInt(2,getPerson().getID());
            statement.setString(3,getCharacter());
            statement.setInt(4,getOrder());



            statement.executeUpdate();
            ResultSet rs = connection.createStatement().executeQuery("select * from castmembers order by id desc limit 1");
            while(rs.next())
            {
                // read the result set
                System.out.println("Character: " + rs.getString("character"));
                System.out.println("id = " + rs.getInt("id"));
                setId(rs.getInt("id"));
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
            //PreparedStatement statement = connection.prepareStatement("insert into content(title, overview, tmdb_id, content_type, total_episodes, watched_episodes, image_url)" +
            //        " values(?,?,?,?,?,?,?)");
            PreparedStatement statement = connection.prepareStatement("select * from castmembers where id=?");
            statement.setInt(1,id);
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            // statement.executeQuery();
            ResultSet rs = statement.executeQuery();
            while(rs.next())
            {
                // read the result set
                setId(rs.getInt("id"));
                setCharacter(rs.getString("character"));
                setOrder(rs.getInt("rank"));
                Person tempPerson = new Person();
                tempPerson.setID(rs.getInt("person_id"));
                tempPerson.readRow(tempPerson.getID());
                setPerson(tempPerson);



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

    public void readRow(ResultSet rs){
        try{
            setId(rs.getInt("id"));
            setCharacter(rs.getString("character"));
            setOrder(rs.getInt("rank"));
            Person tempPerson = new Person();
            tempPerson.setID(rs.getInt("person_id"));
            tempPerson.readRow(tempPerson.getID());
            setPerson(tempPerson);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}

