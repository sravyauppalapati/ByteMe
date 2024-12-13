package com.example.bite;

public class vip_C {
    public void WELCOME()
    {
        System.out.println("\n----Welcome VIP Customer!----");
        customer_page customerpage = new customer_page(true);
        customerpage.displayMainMenu();
    }
}
