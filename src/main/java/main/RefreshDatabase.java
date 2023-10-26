package main;

import model.Person.Person;
import model.connectors.CastMember;
import model.content.Movie;

import java.io.File;

public class RefreshDatabase {
    public static void main(String args[]){
        new RefreshDatabase();
    }

    public RefreshDatabase(){
        deleteDirectory(new File("images"));
        Movie temp = new Movie();
        Person.createTable();
        CastMember.createTable();
        temp.createTable();
        temp.getTMDBdetails(680);
        temp.createRow();
        Movie temp2 = new Movie(988);
        temp2.createRow();
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
}
