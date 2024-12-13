package com.example.bite;

public class Review
{
    private String item_name;
    private String reviewww;
    private String room_no;

    public Review(String item_name, String reviewww, String room_no) {
        this.item_name = item_name;
        this.reviewww = reviewww;
        this.room_no = room_no;
    }
    public String getItemName() {
        return item_name; }

    public String toString() {
        return String.format("Room %s: %s", room_no, reviewww);
    }
}