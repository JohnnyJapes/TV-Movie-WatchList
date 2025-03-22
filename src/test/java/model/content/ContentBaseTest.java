package model.content;

import com.tv.tvmoviewatchlist.model.content.ContentBase;
import com.tv.tvmoviewatchlist.model.content.Movie;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ContentBaseTest {

/*    @Test
    void createRow() {
        ContentBase temp;
        Movie movie = new Movie(680);
        temp = movie;
        temp.createRow();
    }*/
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
    void createTable() {
        ContentBase temp = new ContentBase();
        temp.createTable();
    }

    @Test
    void getDetails() {
        ContentBase temp;
        Movie movie = new Movie(680);
        temp = movie;
        System.out.println(temp.getDetails());
    }
}