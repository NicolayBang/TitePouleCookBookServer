package com.example.RecipeBook.Views;

import java.util.ArrayList;

public class TagsCheckboxMenuView {

    private ArrayList<String> tags = new ArrayList<>();//Category id is always going to be the same as the index therefore it is not needed. (Hash map not needed)


    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

}
