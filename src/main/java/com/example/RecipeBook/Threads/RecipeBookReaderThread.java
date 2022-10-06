package com.example.RecipeBook.Threads;

import com.example.RecipeBook.Data.DBReader;
import com.example.RecipeBook.RestController.RESTControllerImpl;
import com.example.RecipeBook.Views.*;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.ArrayList;

@Service
public class RecipeBookReaderThread extends Thread {

    final RESTControllerImpl recipeHandler;
    RecipeCardView recipeCardView;
    final Connection connection;
    final DBReader DBReader = new DBReader();
    private final DBReader dbReader = new DBReader();

    public RecipeBookReaderThread() {
        recipeHandler = new RESTControllerImpl();
        this.connection = RESTControllerImpl.connection;
    }

    //@Override

    /**
     * TODO this acts as getRecipe method for now, need to determine how many and what recipe to return.
     *
     * @return
     */
    public String getRecipes(String query) {

        ArrayList<RecipeCardView> recipeCardViews = DBReader.getRecipes(1, query);//TODO change this to be dynamic
        return dbReader.gsonGet.toJson(recipeCardViews);
    }

    public String getRecipes(long user_id, String query) {

        ArrayList<RecipeCardView> recipeCardViews = DBReader.getRecipes(user_id, query);//TODO change this to be dynamic
        return dbReader.gsonGet.toJson(recipeCardViews);
    }

    public String getRecipesByName(long user_id, String query) {

        ArrayList<RecipeCardView> recipeCardViews = DBReader.getRecipes(user_id, query);//TODO change this to be dynamic
        return dbReader.gsonGet.toJson(recipeCardViews);
    }

    // @Override
    public String getRecipe(long id) {
        return dbReader.gsonGet.toJson(DBReader.getRecipeCardViewFromDB(id));
        //return recipeHandler.getDataBaseConnector().gson.toJson(RESTControllerImpl.getInstance().getDataBaseConnector().recipes.get(id));
    }

    public String getTags() {

        TagsCheckboxMenuView tagsCheckboxMenuView = DBReader.getTagsFromDB();//TODO change this to be dynamic
        return dbReader.gsonGet.toJson(tagsCheckboxMenuView);
    }

    public String getIngredients(long recipe_id) {

        IngredientsView ingredientsView = DBReader.getRecipeIngredientsViewFromDB(recipe_id);//TODO change this to be dynamic
        return dbReader.gsonGet.toJson(ingredientsView);
    }

    public String getInstructions(long recipe_id) {

        InstructionsView instructionsView = DBReader.getRecipeInstructionsViewFromDB(recipe_id);//TODO change this to be dynamic
        return dbReader.gsonGet.toJson(instructionsView);
    }

    public String getTags(long recipe_id) {
        TagsCheckboxMenuView tagsCheckboxMenuView = DBReader.getTagsFromDB(recipe_id);//TODO change this to be dynamic
        return dbReader.gsonGet.toJson(tagsCheckboxMenuView);
    }

    public String getCookingTimes(String query) {
           CookingTimesView cookingTimesView = DBReader.getCookingTimesFromDB(query);//TODO change this to be dynamic
        return dbReader.gsonGet.toJson(cookingTimesView);
    }

    public String getUnits(String query) {
        UnitsView unitsView = DBReader.getUnitsFromDB(query);//TODO change this to be dynamic
        return dbReader.gsonGet.toJson(unitsView);
    }
//
//    /**
//     * Creates a new recipe. Accepts JSON format as well as html form data.
//     * TODO: This should worked as it was tested with the previous data structure and should work with the new one.
//     * Confirm with Postman. For now focusing on Get, Post will be implemented later so this is a low priority for now.
//     *
//     * @param gsonPost
//     * @return
//     * @throws IOException
//     */
//    public Response createRecipe(String gsonPost) throws IOException {
//
//        Card recipe;
//
//        if (isJSONValid(gsonPost)) {
//            recipe = recipeHandler.getDBWriter().gson.fromJson(gsonPost, Card.class);
//        } else {
//            recipe = (Card) parseFormDataToRecipe(gsonPost);
//        }
//        recipeHandler.getDBWriter().currId++;
//        recipe.setRecipe_id(recipeHandler.getDBWriter().currId);
//        RESTControllerImpl.getInstance().getDBWriter().recipes.put(recipe.getRecipe_id(), recipe);
//        return Response.ok(recipe).build();
//    }
//
//    private boolean isJSONValid(String gsonPost) {
//        try {
//            recipeHandler.getDataBaseConnector().gson.fromJson(gsonPost, Object.class);
//            return true;
//        } catch (com.google.gson.JsonSyntaxException ex) {
//            return false;
//        }
//    }
//
//    private Recipe parseFormDataToRecipe(String gsonPost) {
//        Card card = new Card();
//        String[] recipeArray = createArrayByValue(gsonPost, "&");
//        String name = createStringByValue(recipeArray[0], "=");
//        String posted_by = createStringByValue(recipeArray[1], "=");
//        String creation_date = createStringByValue(recipeArray[2], "=");
//        int difficulty = Integer.parseInt(createStringByValue(recipeArray[3], "="));
//        //Need to adapt client to handle these new values
//        String cook_time = createStringByValue(recipeArray[4], "=");
//        int popularity = Integer.parseInt(createStringByValue(recipeArray[5], "="));
//        int servings = Integer.parseInt(createStringByValue(recipeArray[6], "="));
//        String description = createStringByValue(recipeArray[7], "=");
//        int recipe_id = Integer.parseInt(createStringByValue(recipeArray[8], "="));
//        boolean hasImage = Boolean.parseBoolean(createStringByValue(recipeArray[9], "="));
//        // String[] recipeArray = gsonPost.split("&");
//
//        card.setName(name);
//        card.setPosted_by(posted_by);
//        card.setCreation_date(creation_date);
//        card.setDifficulty(difficulty);
//        card.setCook_time(cook_time);
//        card.setPopularity(popularity);
//        card.setServings(servings);
//        card.setDescription(description);
//        card.setRecipe_id(recipe_id);
//        card.setHasImage(hasImage);
////
//
//        if (card.hasImage()) {
//            card.setImage_id("img_" + card.getRecipe_id() + ".jpg");
//        }
//        return card;
//    }
//
//    private String[] createArrayByValue(String gsonPost, String regex) {
//        String[] recipeArray = gsonPost.split(regex);
//        return recipeArray;
//    }
//
//    private String createStringByValue(String rawValue, String regex) {
//        String[] recipeArray = rawValue.split(regex);
//        String value = recipeArray[1];
//        return value;
//    }

//    public Response updateRecipe(String gsonPut) {
//        Card recipe = recipeHandler.getDataBaseConnector().gson.fromJson(String.valueOf(gsonPut), Card.class);
//        Card currRecipe = RESTControllerImpl.getInstance().getDataBaseConnector().recipes.get(recipe.getRecipe_id());
//        Response response;
//        if (currRecipe != null) {
//            RESTControllerImpl.getInstance().getDataBaseConnector().recipes.put(recipe.getRecipe_id(), recipe);
//            response = Response.ok().build();
//        } else {
//            response = Response.notModified().build();
//        }
//        return response;
//    }

    // @Override
//    public Response deleteRecipe(Long id) {
//        Recipe recipe = RESTControllerImpl.getInstance().getDataBaseConnector().recipes.get(id);
//
//        Response response;
//        if (recipe != null) {
//            RESTControllerImpl.getInstance().getDataBaseConnector().recipes.remove(id);
//            response = Response.ok().build();
//        } else {
//            response = Response.notModified().build();
//        }
//        return response;
//    }
}
