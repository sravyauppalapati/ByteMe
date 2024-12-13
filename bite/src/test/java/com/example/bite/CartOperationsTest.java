package com.example.bite;

import org.junit.jupiter.api.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

public class CartOperationsTest
{
    private Menu menu;
    private cart_opt cart;
    private ByteArrayOutputStream outputStream;
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        menu = new Menu();
        cart = new cart_opt(menu, false);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testaddnverify() {
        FoodItem fries = menu.findItem("French Fries");
        assertNotNull(fries);
        cart.addToCart(fries, 2);
        double eTotal = 2*3.99;
        double aTotal = cart.calculateT();
        assertEquals(eTotal, aTotal, 0.01);
    }

    @Test
    public void testquantityntotal() {
        FoodItem burger = menu.findItem("Burger Combo");
        assertNotNull(burger);
        cart.addToCart(burger, 1);
        double initialTotal = cart.calculateT();
        assertEquals(12.99, initialTotal, 0.01);
        cart.updatee_quantity(burger, 3);
        double newTotal = cart.calculateT();
        assertEquals(38.97, newTotal, 0.01);
    }

    @Test
    public void testNegativequantity() {

        FoodItem fries = menu.findItem("French Fries");
        assertNotNull(fries);
        cart.addToCart(fries, 2);
        cart.updatee_quantity(fries, -1);
        Integer newQuantity = cart.getItems_in_cart().get(fries);
        if (newQuantity != null) {
            assertTrue(newQuantity > 0, "the quantity should be positive !!");
        } else {
            assertFalse(cart.getItems_in_cart().containsKey(fries),
                    "if invalid quantity given item should be removed");
        }
    }

    @Test
    public void testTotalMultipleitems() {
        FoodItem fries = menu.findItem("French Fries");
        FoodItem burger = menu.findItem("Burger Combo");
        FoodItem drink = menu.findItem("Iced Tea");

        cart.addToCart(fries, 2);
        cart.addToCart(burger, 1);
        cart.addToCart(drink, 3);

        double expectedTotal = (2 *3.99) + (1*12.99) +(3*2.49);
        double actualTotal = cart.calculateT();
        assertEquals(expectedTotal, actualTotal, 0.01);
    }
}