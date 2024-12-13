package com.example.bite;
import java.util.*;


public class MReview {
    private static Map<String, List<Review>> reviewsI = new HashMap<>();
    public static void addReview(Review review) {
        String item_name = review.getItemName();
        reviewsI.computeIfAbsent(item_name, k -> new ArrayList<>()).add(review);
    }
    public static List<Review> getReviewI(String item_name) {
        return reviewsI.getOrDefault(item_name, new ArrayList<>());
    }
}