package com.example.bite;
import java.io.Serializable;
public class OrderItem
{
    private FoodItem item;
    private int quantity;
    private double priceAtOrder;
    private static final long serialVersionUID = 1L;
    public OrderItem(FoodItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
        this.priceAtOrder = item.getPrice();
    }

    public FoodItem getItem()
    {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
    public double getPriceAtOrder() {
        return priceAtOrder;
    }
}