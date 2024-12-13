package com.example.bite;
import java.util.*;
public class Byte
{
    public static void main(String[] args)
    {
        Scanner opt = new Scanner(System.in);
        while(true) {
            System.out.println("\n----Welcome to Byte Me!----");
            System.out.println("Login as: ");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.print("Enter your option: ");
            int choice = opt.nextInt();
            switch(choice)
            {
                case 1:
                    Admin.adminLogin();
                    break;

                case 2:
                    Customer.customerL();
                    break;

                default:
                    System.out.println("try again");
            }
            System.out.println("\ncontinue? (y/n): ");
            char cc = opt.next().charAt(0);
            if(cc != 'y' && cc != 'Y') {
                System.out.println("BYEEE :D");
                break;
            }
        }
        opt.close();
        System.out.println("\nGUI :3");
        HelloApplication.main(args);
    }
}
