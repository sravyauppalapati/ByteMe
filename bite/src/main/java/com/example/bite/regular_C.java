package com.example.bite;

public class regular_C {
    public void WELCOME() {
        System.out.println("\n----Welcome Regular Customer!----");
        customer_page customerpage = new customer_page(false);
        customerpage.displayMainMenu();
    }
}
