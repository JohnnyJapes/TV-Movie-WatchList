package model.content;

import org.junit.jupiter.api.Test;

class ContentBaseTest {

    @Test
    void createRow() {
        ContentBase temp;
        Movie movie = new Movie(680);
        temp = movie;
        temp.createRow();
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