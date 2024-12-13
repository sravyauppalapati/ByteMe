package com.example.bite;

import org.junit.jupiter.api.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

public class OrderTest
{
    private Menu menu;
    private ByteArrayOutputStream outputStream;
    @BeforeEach
    public void setUp() {
        menu = new Menu();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }
    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
    }
    @Test
    public void testifwecanaddavailableitem() {

        FoodItem item = menu.findItem("French Fries");
        assertNotNull(item, "French Fries should exist in menu");
        assertTrue(item.isAvailable(), "French Fries should be available");
    }

    @Test
    public void testUnavailableitem()
    {
        FoodItem item = menu.findItem("Pasta Alfredo");
        assertNotNull(item, "Pasta Alfredo should exist in menu");
        assertFalse(item.isAvailable(), "Pasta Alfredo should be unavailable");
    }

    @Test
    public void testAvailabilityinmenu()
    {
        menu.item_display();
        String output = outputStream.toString();
        assertTrue(output.contains("Pasta Alfredo") &&output.contains("Not Available"), "Menu should show Pasta Alfredo as Not Available");
        assertTrue(output.contains("French Fries") && output.contains("Available"), "Menu should show French Fries as Available");
    }
}