package com.example.bite;
import java.io.Serializable;

class FoodItem implements Comparable<FoodItem>
{
    private String name;
    private double price;
    private String category;
    private boolean available;
    private static final long serialVersionUID = 1L;

    public FoodItem(String name, double price, String category, boolean available)
    {
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return available;
    }
    public void setPrice(double price) {
        if (price>0) {
            this.price=price;
        }
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int compareTo(FoodItem other) {
        return this.name.compareToIgnoreCase(other.name);
    }

    @Override
    public String toString()
    {
        return String.format("%-20s %-10s $%-7.2f %s", name, category, price, available ? "Available" : "Not Available");
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this==obj) return true;
        if (!(obj instanceof FoodItem)) return false;
        FoodItem other = (FoodItem) obj;
        return this.name.equalsIgnoreCase(other.name);
    }
    public int hashCode()
    {
        return name.toLowerCase().hashCode();
    }
}
