package com.example.RecipeBook.Objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class RecipePost {
    private String title; //= "Empty";
    private long difficulty_id;//=0;



     private long prep_time_id;
     private long nb_of_servings;
 //   private long image_id = 0;//Not yet implemented TODO change this
     private String description;
     private long user_id;//For now hard coded at 1 TODO change this
     private JsonArray tags;



    private final ArrayList<Ingredient> ingredients = new ArrayList<>();//=null;
    private final ArrayList<Instruction> instructions = new ArrayList<>();//=null;

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(JsonArray instructions) {
        instructions.forEach(instruction -> {
            JsonObject instructionObject = instruction.getAsJsonObject();
            Instruction instruction1 = new Instruction();
            instruction1.setInstruction(instructionObject.getAsJsonObject().get("instruction").getAsJsonObject()
                    .getAsJsonObject().get("name").getAsString());
           // instruction1.setRecipe_name(instructionObject.get("recipe_name").getAsString());
            //instruction1.setStep_nb(String.valueOf(instructionObject.get("step_nb").getAsInt()));
            this.instructions.add(instruction1);
        });
        //this.instructions = instructions;
    }


    public ArrayList<Ingredient> getIngredients() {
    return ingredients;
    }
    public void setIngredients(JsonArray ingredients) {
        ingredients.forEach(ingredient -> {
            this.ingredients.add(new Ingredient(
                    ingredient
                    .getAsJsonObject().get("ingredient").getAsJsonObject()
                    .getAsJsonObject().get("name").getAsString(),
                    ingredient
                            .getAsJsonObject().get("ingredient").getAsJsonObject()
                            .getAsJsonObject().get("amount").getAsLong(),
                    ingredient
                    .getAsJsonObject().get("ingredient").getAsJsonObject()
                    .getAsJsonObject().get("unit").getAsString()));
        });

    }

    public long getPrep_time_id() {
        return prep_time_id;
    }

    public void setPrep_time_id(long prep_time_id) {
        this.prep_time_id = prep_time_id;
    }
    public long getNb_of_servings() {
        return nb_of_servings;
    }

    public void setNb_of_servings(long nb_of_servings) {
        this.nb_of_servings = nb_of_servings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDifficulty_id() {
        return difficulty_id;
    }

    public void setDifficulty_id(long difficulty_id) {
        this.difficulty_id = difficulty_id;
    }

    public JsonArray getTags() {
    return tags;
}

    public void setTags(JsonArray tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

}
