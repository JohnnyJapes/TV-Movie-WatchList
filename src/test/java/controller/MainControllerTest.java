package controller;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {

    @Test
    void openNewItem() throws IOException {
        MainController controller = new MainController();
        controller.openNewItem();
    }
}