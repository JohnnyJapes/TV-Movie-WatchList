package com.tv.tvmoviewatchlist.main;

import com.tv.tvmoviewatchlist.model.Person.Person;
import com.tv.tvmoviewatchlist.model.Tables;
import com.tv.tvmoviewatchlist.model.connectors.CastMember;
import com.tv.tvmoviewatchlist.model.content.Movie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RefreshDatabase {
    public static void main(String args[]){
        try {
            getPropValues();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        Tables.refreshDatabase();
    }

    public RefreshDatabase(){
        deleteDirectory(new File("images"));
        Movie.createTable();
        Person.createTable();
        CastMember.createTable();

    }

    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
        /**
     * Loads properties from config.proprties. Returns false if properties are not found.
     * @return Boolean
     * @throws IOException
     */
    static void getPropValues() throws IOException {
        Boolean result = true;
        InputStream inputStream = null;
            try {
                Properties props = new Properties();
                String propFileName = "config.properties";
    
                inputStream = new FileInputStream(propFileName);
                if (inputStream != null) {
                    props.load(inputStream);
                }
                //set properties to system so they are accessible
                System.setProperty("dburl",props.getProperty("dburl"));
                System.setProperty("token",props.getProperty("token"));
                System.out.println("dburl: " + System.getProperty("dburl"));
            } catch (Exception e) {
                System.out.println("Exception: " + e);
                result = false;

            } finally {
                inputStream.close();
            }
            //return result;
    }
    
}
