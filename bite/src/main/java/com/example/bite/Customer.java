package com.example.bite;
import java.util.*;
public class Customer
{
    public static void customerL()
    {
        Scanner opt = new Scanner(System.in);

        System.out.println("\nLogin as: ");
        System.out.println("1. VIP");
        System.out.println("2. Regular");
        System.out.print("Enter your option: ");
        int choice = opt.nextInt();
        switch(choice) {
            case 1:
                vip_C vip = new vip_C();
                vip.WELCOME();
                break;
            case 2:
                regular_C regular = new regular_C();
                regular.WELCOME();
                break;
            default:
                System.out.println("try again");
                customerL();
        }
    }
}