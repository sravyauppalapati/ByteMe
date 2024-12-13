package com.example.bite;

import java.util.Comparator;

public class Opriority implements Comparator<Order>
{

    public int compare(Order o1, Order o2) {
        boolean iVIP1= o1.getCustomerT()!= null&& o1.getCustomerT().equals("VIP");
        boolean iVIP2 =o2.getCustomerT() != null &&o2.getCustomerT().equals("VIP");
        if (iVIP1!=iVIP2)
        {
            return iVIP1 ? -1 : 1;
        }
        return o1.getOrderId()-o2.getOrderId();
    }
}