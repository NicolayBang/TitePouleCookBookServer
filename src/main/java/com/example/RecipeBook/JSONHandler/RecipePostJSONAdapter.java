package com.example.RecipeBook.JSONHandler;

import com.example.RecipeBook.Objects.RecipePost;
import com.google.gson.*;

import java.lang.reflect.Type;

public class RecipePostJSONAdapter implements JsonDeserializer<RecipePost> {

    /**
     * Deserializes the JSON object into a RecipePost object
     * TODO: add support for prep_time_id by sending the Prep_time id to the front end
     * TODO: so it can be sent back to the backend
     * @param jsonElement
     * @param type
     * @param jsonDeserializationContext
     * @return
     * @throws JsonParseException
     */
    @Override
    public RecipePost deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        RecipePost recipePost = new RecipePost();

        recipePost.setTitle(jsonObject.get("title").getAsString());
        try{
            recipePost.setUser_id(jsonObject.get("user_id").getAsLong());
        }catch (Exception e){recipePost.setUser_id(1);}//TODO remove this when user_id is implemented

        recipePost.setDifficulty_id(jsonObject.get("difficulty_id").getAsInt());
        recipePost.setPrep_time_id(Long.parseLong(jsonObject.get("prep_time_id").getAsString()));
        recipePost.setNb_of_servings(jsonObject.get("nb_of_servings").getAsLong());

        recipePost.setDescription(jsonObject.get("description").getAsString());
        recipePost.setIngredients(jsonObject.get("ingredients").getAsJsonArray());
        recipePost.setInstructions(jsonObject.get("instructions").getAsJsonArray());
        //if(jsonObject.get("tags").getAsJsonArray()!=null){
        try {
            recipePost.setTags(jsonObject.get("tags").getAsJsonArray());
        }catch (Exception e){recipePost.setTags(null);}

       // }
       // else {
          //  recipePost.setTags(null);
       // }
        // TODO implement this on the front end (hard coded for now)
    //    recipePost.setImage_id(jsonObject.get("recipe_image").getAsLong());
        return recipePost;
    }

}
