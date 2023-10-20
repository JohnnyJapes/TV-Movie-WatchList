
import model.Person.Person;
import model.TMDBcompatible;
import model.content.Movie;
import model.content.TV;

import java.util.ArrayList;

public class TestHarness {
public static void main(String args[]){
    new TestHarness();
}
    public TestHarness(){

    try {
        testInterface();
    }
    catch (Exception e){
        e.printStackTrace();
    }


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
        System.out.println("TESTING INTERFACE ---------------------------------------");
        ArrayList<TMDBcompatible> interfaceTester = new ArrayList<TMDBcompatible>();
        interfaceTester.add(new Movie());
        interfaceTester.add(new TV());
        System.out.println("TESTING getTMDBdetails --------------------------------------");
        for (TMDBcompatible obj : interfaceTester){
            obj.getTMDBdetails(1942);
        }
        System.out.println("Testing searchTMDB----------------------------------------");
        for (TMDBcompatible obj : interfaceTester){
            obj.searchTMDB("Scream");
        }
/*
{
  "adult": false,
  "backdrop_path": "/moV8S6PFcFcUQBSxv2LS0wF7oN0.jpg",
  "belongs_to_collection": null,
  "budget": 0,
  "genres": [
    {
      "id": 9648,
      "name": "Mystery"
    },
    {
      "id": 53,
      "name": "Thriller"
    }
  ],
  "homepage": "",
  "id": 1942,
  "imdb_id": "tt0058997",
  "original_language": "en",
  "original_title": "Bunny Lake Is Missing",
  "overview": "A woman reports that her young daughter is missing, but there seems to be no evidence that she ever existed.",
  "popularity": 9.734,
  "poster_path": "/zsZ2PAMMyBhkn5OSC8Qnror2KCA.jpg",
  "production_companies": [
 */
        /*
        {
  "adult": false,
  "backdrop_path": null,
  "created_by": [],
  "episode_run_time": [
    30
  ],
  "first_air_date": "1981-10-26",
  "genres": [
    {
      "id": 35,
      "name": "Comedy"
    }
  ],
  "homepage": "",
  "id": 1942,
  "in_production": false,
  "languages": [
    "en"
  ],
  "last_air_date": "1983-08-23",
  "last_episode_to_air": {
    "id": 131025,
    "name": "0",
    "overview": "",
    "vote_average": 0,
    "vote_count": 0,
    "air_date": "1983-08-23",
    "episode_number": 6,
    "episode_type": "finale",
    "production_code": "",
    "runtime": 30,
    "season_number": 2,
    "show_id": 1942,
    "still_path": null
  },
  "name": "Astronauts",
  "next_episode_to_air": null,
  "networks": [
    {
      "id": 9,
      "logo_path": "/oz9pjTHnBUmlju8OkaTymhKbh6C.png",
      "name": "ITV1",
      "origin_country": "GB"
    }
  ],
  "number_of_episodes": 13,
  "number_of_seasons": 2,
  "origin_country": [
    "GB"
  ],
  "original_language": "en",
  "original_name": "Astronauts",
  "overview": "Astronauts was a British sitcom that aired on ITV in 1981. It was written by Graeme Garden and Bill Oddie, two of The Goodies. Dick Clement and Ian La Frenais, who wrote Porridge, were script editors. It was made for the ITV network by ATV, which became Central midway through the production run.",
  "popularity": 1.409,
  "poster_path": "/5rXO3fgo6aY7ZB2lrMwm6qDgaK1.jpg",
  "production_companies": [],
  "production_countries": [],
         */






    }





}
