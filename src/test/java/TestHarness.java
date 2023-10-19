
import model.Person.Person;
import model.TMDBcompatible;
import model.content.Movie;

import java.util.ArrayList;

public class TestHarness {
public static void main(String args[]){
    new TestHarness();
}
    public TestHarness(){
        //testing the Person.searchPersonTMDB method
        try {
            ArrayList<Person> results = Person.searchPersonTMDB("Jack Black");
            if (results.get(0).getName().equals("Jack Black")) System.out.println("SearchPersonTMDB1 : Success");
            else System.out.println("SearchPersonTMDB1 : Failed");
        }
        catch (Error e){
            e.printStackTrace();
        }



        //testing the person retrievePersonDetails method
        Person test = new Person();
        test.retrievePersonDetails(70851);
        if (test.getName().equals("Jack Black") && test.getGender().equals("male")) System.out.println("retrievePersonDetails1 : Success");
        else System.out.println("retrievePersonDetails1 : Failed");
        test.retrievePersonDetails(70853);
        if (test.getName().equals("Jean Danet") && test.getGender().equals("male")) System.out.println("retrievePersonDetails2 : Success");
        else System.out.println("retrievePersonDetails2 : Failed");

        //testing toString functionality
        try {
            String testString = test.toString();
            String answerString =  "Name: " + "Jean Danet" + "\n"
                    + "Biography: " +"Jean Danet (14 January 1924 – 15 October 2001) was a French actor. He appeared in 27 films between 1942 and 1983.\n\nDanet was born in Auray, Brittany, France. Following World War II, he began work in films. He founded Tréteaux de France in 1959. Danet died in Paris.\n\nSource: Article \"Jean Danet\" from Wikipedia in English, licensed under CC-BY-SA 3.0." + "\n"
                    + "gender: " + "male"  + "\n"
                    + "knownFor: " + "Acting"  + "\n"
                    + "Birthday: " + "1924-01-14"  + "\n"
                    + "TMDB ID: " + "0"  + "\n";
            if (testString.equals(answerString))System.out.println("toString 1 : Success");
            else System.out.println("toString 1 : Failed");
            answerString =  "Name: " + "Martin Todsharow" + "\n"
                    + "Biography: " + "" + "\n"
                    + "gender: " + "male"  + "\n"
                    + "knownFor: " + "Sound"  + "\n"
                    + "Birthday: " + "1967-09-06"  + "\n"
                    + "TMDB ID: " + "0"  + "\n";
            test.retrievePersonDetails(708);

            testString = test.toString();
            if (testString.equals(answerString))System.out.println("toString 2 : Success");
            else System.out.println("toString 2 : Failed");
            System.out.println(answerString);


        }
        catch (Error e){
            e.printStackTrace();

        }



    }
    public void testInterface(){
        ArrayList<TMDBcompatible> interfaceTester = new ArrayList<TMDBcompatible>();
        interfaceTester.add(new Movie(680));






    }





}
