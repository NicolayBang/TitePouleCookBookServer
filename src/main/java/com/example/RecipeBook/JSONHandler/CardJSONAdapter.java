package com.example.RecipeBook.JSONHandler;

import com.example.RecipeBook.Objects.Card;
import com.example.RecipeBook.Objects.Recipe;
import com.google.gson.*;

import java.lang.reflect.Type;
/*

 *  */

public class CardJSONAdapter implements JsonDeserializer<Recipe> {

    @Override
    public Recipe deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Card card = new Card();
       /* if (jsonObject.get("recipe_id") != null) {
            recipe.setRecipe_id(jsonObject.get("recipe_id").getAsLong());
        }*/
        card.setTitle(jsonObject.get("name").getAsString());
        card.setDescription(jsonObject.get("description").getAsString());
        card.setPosted_by(jsonObject.get("posted_by").getAsString());
        card.setCreation_date(jsonObject.get("creation_date").getAsString());
        card.setDifficulty(jsonObject.get("difficulty").getAsInt());
        card.setCook_time(jsonObject.get("cook_time").getAsString());
        card.setServings(jsonObject.get("servings").getAsInt());
        card.setPopularity(jsonObject.get("popularity").getAsInt());


        //If there is an image associated with the recipe, generate a custom image id
        if (card.hasImage()) {
            card.setImage_id("img_" + card.getRecipe_id() + ".jpg");
        }
        return card;
    }
}

