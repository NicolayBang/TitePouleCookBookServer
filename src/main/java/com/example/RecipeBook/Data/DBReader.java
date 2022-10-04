package com.example.RecipeBook.Data;

import com.example.RecipeBook.JSONHandler.CardJSONAdapter;
import com.example.RecipeBook.Objects.Card;
import com.example.RecipeBook.RestController.RESTControllerImpl;
import com.example.RecipeBook.Views.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class DBReader {
    public static final CardJSONAdapter cardJsonAdapter = new CardJSONAdapter();
    public final Gson gsonGet = new GsonBuilder().registerTypeAdapter(Card.class, cardJsonAdapter).serializeNulls().create();
    final Connection connection = RESTControllerImpl.getInstance().connection;

    public TagsCheckboxMenuView getTagsFromDB() {
        String sql = "SELECT * FROM TagsView";
        TagsCheckboxMenuView tagsCheckboxMenuView = new TagsCheckboxMenuView();
        try {
            PreparedStatement preparedStatement = RESTControllerImpl.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                tagsCheckboxMenuView.getTags().add(resultSet.getString("category_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tagsCheckboxMenuView;
    }

    /**
     * Gets all the data for the InstructionsView and returns it as a InstructionsView object.
     * getRecippeIngredientsViewFromDB has the exact same structure and logic.
     * TODO - Refactor to use a generic method that takes a view name and returns a view object or implement a RecipeViewFactory.
     *
     * @param recipe_id
     * @return
     */
    public InstructionsView getRecipeInstructionsViewFromDB(long recipe_id) {
        String sql = "SELECT * FROM RecipeInstructionsView WHERE recipe_id = " + recipe_id;
        InstructionsView instructionsView = new InstructionsView();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String instruction = resultSet.getString("step_nb") + " " + resultSet.getString("instruction");
                instructionsView.getInstructions().add(instruction);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // this.instructionsView = instructionsView;
        return instructionsView;

    }

    /**
     * * TODO - Refactor to use a generic method that takes a view name and returns a view object.
     *
     * @param recipe_id
     * @return
     */

    public IngredientsView getRecipeIngredientsViewFromDB(long recipe_id) {
        //  Ingredient ingredient = null;
        String sql = "SELECT * FROM RecipeIngredientsView WHERE recipe_id = " + recipe_id + ";";
        IngredientsView recipeIngredientsView = new IngredientsView();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String ingredient = resultSet.getString("amount")
                        + " " + resultSet.getString("unit_of_measure") + " " + resultSet.getString("ingredient");
                recipeIngredientsView.getIngredients().add(ingredient);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeIngredientsView;
    }

    /**
     * If user_id is not 0, gets recipes by user_id, otherwise uses sent query as parameter
     *
     * @param user_id
     * @param query
     * @return
     */
    public ArrayList<RecipeCardView> getRecipes(long user_id, String query) {
        String sql;
        String columnLabel;
        ArrayList<RecipeCardView> recipeCardViews = new ArrayList<>();
        if (user_id != 0) {
            sql = query + user_id + ";";
            columnLabel = "favourite_recipes";
        } else {
            sql = query;
            columnLabel = "recipe_id";
        }

        //    sql = "SELECT * FROM FavouriteRecipesView WHERE user_id = " + user_id + ";";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // recipeCardViews = new RecipeCardView[resultSet.getFetchSize()];
                if (columnLabel.equals("favourite_recipes")) {
                    recipeCardViews.add(getRecipeCardViewFromDB(resultSet.getLong(columnLabel)));
                } else {
                    recipeCardViews.add(getRecipeCardViewFromDB(resultSet.getLong(columnLabel)));
                }

            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return recipeCardViews;
    }
    public RecipeCardView getRecipeCardViewFromDB(long recipe_id) {
        String sql;
        Card card;
        sql = "SELECT * FROM RecipeCardView where recipe_id = " + recipe_id + ";";
        RecipeCardView recipeCardView = new RecipeCardView();

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                card = new Card();
                card.setRecipe_id(recipe_id);
                card.setTitle(resultSet.getString("name"));
                card.setPosted_by(resultSet.getString("posted_by"));
                card.setDescription(resultSet.getString("description"));
                card.setCreation_date(resultSet.getString("creation_date"));
                card.setCook_time(resultSet.getString("cook_time"));
                card.setDifficulty(resultSet.getInt("difficulty"));
                card.setPopularity(resultSet.getInt("popularity"));
                card.setServings(resultSet.getInt("servings"));
                recipeCardView.addRecipeCard(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < recipeCardView.getRecipe().size(); i++)//REMOVE AFTER
        {
            System.out.println(recipeCardView.getRecipe().get(i).getTitle() + " | " + recipeCardView.getRecipe().get(i).getDescription());
        }
        // this.recipeCardView = recipeCardView;
        return recipeCardView;
    }
    public TagsCheckboxMenuView getTagsFromDB(long recipe_id) {
        String sql = "SELECT*FROM Tags WHERE recipe_id = " + recipe_id + ";";
        TagsCheckboxMenuView tagsCheckboxMenuView = new TagsCheckboxMenuView();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tagsCheckboxMenuView.getTags().add(resultSet.getString("category_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tagsCheckboxMenuView;
    }

    public CookingTimesView getCookingTimesFromDB(String query) {
        CookingTimesView cookingTimesView = new CookingTimesView();
   //     HashMap<String, String> result = new HashMap<>();
        HashMap<String, String> result = queryDBAsMap(query, "prep_time_id", "prep_time");
       // result.put(queryDB(query, "prep_time"), queryDB(query, "prep_time_id") );
      //  ArrayList<String> result = queryDB(query,"prep_time");;
        cookingTimesView.setCooking_times(result);
        return cookingTimesView;
    }
    public UnitsView getUnitsFromDB(String query) {
        UnitsView unitsView = new UnitsView();
        ArrayList<String> result = queryDB(query,"unit_name");//TODO - Separate different unit types
        unitsView.setUnits(result);
        return unitsView;
    }
    /**
     * Non-synchronized method that performs query to the database that do no need to be synchronized
     * TODO - Make other get methods use this method, more generic and maintainable
     * @param sql statement
     * @param columnLabel: never null
     * @return
     */
    public ArrayList<String> queryDB(String sql, String columnLabel) {
        ArrayList<String> outputValue = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

            while (resultSet.next()) {
                outputValue.add(resultSet.getString(columnLabel));
               // outputValue = resultSet.getString(columnLabel);
            }
            return outputValue;
        } catch (SQLException e) {
            e.printStackTrace();
            return outputValue;
        }
    }

    /**
     * Version of queryDB that returns a HashMap
     * @param sql
     * @param key
     * @param value
     * */

    public HashMap<String, String> queryDBAsMap(String sql, String key, String value) {
        HashMap<String, String> outputValue = new HashMap<>();
        try {
            ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

            while (resultSet.next()) {
              //  outputValue.add(resultSet.getString(columnLabel));
                outputValue.put( resultSet.getString(value),resultSet.getString(key));
                // outputValue = resultSet.getString(columnLabel);
            }
            return outputValue;
        } catch (SQLException e) {
            e.printStackTrace();
            return outputValue;
        }
    }

}



