package com.example.RecipeBook.Views;

import java.util.ArrayList;
import java.util.HashMap;

public class CookingTimesView {
//TODO: add Prep_Time_ID as a field to be sent to the front end
//    private ArrayList<String> cooking_times = new ArrayList<>();
//    private ArrayList<String> cooking_times_id = new ArrayList<>();

    private HashMap<String, String> cooking_times = new HashMap<>();

    public  HashMap<String, String> getCooking_times() {
        return cooking_times;
    }

    public void setCooking_times( HashMap<String, String> cooking_times) {
        this.cooking_times = cooking_times;
    }
}
