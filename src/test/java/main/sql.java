package main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class sql {

        /**
     * Loads properties from config.proprties.
     * @return 
     * @throws IOException
     */
    @BeforeAll
    public static void getPropValues() throws IOException {
        Boolean result = true;
        InputStream inputStream = null;
            try {
                Properties props = new Properties();
                String propFileName = "config.properties";
    
                inputStream = new FileInputStream(propFileName);
    
                if (inputStream != null) {
                    props.load(inputStream);
                } else {
                    result = false;
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
                }    
                //set properties to system so they are accessible
                System.setProperty("dburl",props.getProperty("dburl"));
                System.setProperty("token",props.getProperty("token"));
            } catch (Exception e) {
                System.out.println("Exception: " + e);
                result = false;

            } finally {
                inputStream.close();
            }
            //return result;
    }
    @Test
    void selectRank() throws SQLException {
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(System.getProperty("dburl"));
            ResultSet rs = connection.createStatement().executeQuery("Select * from listentries  where rank>=2 and list_id=0");
            while(rs.next()){
                System.out.println("id: " + rs.getInt("id") + ", rank: " + rs.getInt("rank"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally{
            connection.close();
        }

    }



}
