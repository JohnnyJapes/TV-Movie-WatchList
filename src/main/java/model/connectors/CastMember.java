package model.connectors;

import model.Person.Person;
import model.content.ContentBase;

/**
 * This class will hold the character for a piece of content along with the person portraying them.
 */
public class CastMember {

    private Person person;
    String character;
    ContentBase content;

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
}

