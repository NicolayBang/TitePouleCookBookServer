package com.example.RecipeBook.Objects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * TODO: Will change this class to be the superclass of all other objects
 */

@XmlRootElement(name="Recipe")
public class Recipe {

    private long recipe_id;
    private String image_id;
    boolean hasImage=false;

    public boolean hasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public long getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(long recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }





}