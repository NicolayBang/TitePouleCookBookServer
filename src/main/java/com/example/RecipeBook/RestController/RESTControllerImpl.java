package com.example.RecipeBook.RestController;

import com.example.RecipeBook.Data.DBReader;
import com.example.RecipeBook.Data.DBWriter;
import com.example.RecipeBook.Threads.RecipeBookReaderThread;
import com.example.RecipeBook.Threads.RecipeBookWriterThread;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Singleton RestController class that handles all the requests from the client.
 */



public class RESTControllerImpl implements RestListener, RestController {

    private static final DBWriter dbWriter = new DBWriter();
    private static final RESTControllerImpl recipeHandler = new RESTControllerImpl();


    public static Connection connection;


    public RESTControllerImpl() {
        connectToDB();
    }

    public static RESTControllerImpl getInstance() {
        return recipeHandler;
    }

    public void connectToDB() {
        try {
            connection = DriverManager.getConnection
                    ("jdbc:mariadb://iu51mf0q32fkhfpl.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/l9wg8442wjqiydnn",
                            "h0t8uqbccnbr14zr", "a8s8zrqzymt4ny5a");
        } catch (SQLException e) {

        }
    }

    public DBReader getDBReader() {
        return new DBReader();
    }

    public DBWriter getDBWriter() {
        return dbWriter;
    }

    /*
     *TODO: Change this method so it calls on FavouriteRecipeView from db to get the recipe_id's of
     * the favourite recipes and then calls on the RecipeCardView to get the recipe cards. Will take
     * user_id as parameter. For now hardcode the user_id to 1.
     */

    @Override
    public String getRecipesByUser() {
        String query = "SELECT * FROM FavouriteRecipesView WHERE user_id = ";
        RecipeBookReaderThread thread = new RecipeBookReaderThread();
        thread.start();
       // return thread.getRecipes(query);
        return "TEST";
    }

    @Override
    public String getRecipesByUser(String tags) {
        String query = buildQueryByTags(tags);
        //     System.out.println(query);
        RecipeBookReaderThread thread = new RecipeBookReaderThread();
        thread.start();
        String response = thread.getRecipes(0, query);
        return response;
    }

    @Override
    public String getRecipesByName(String name) {
        String query = "SELECT * FROM RecipeCardView WHERE name LIKE '%" + name + "%'";
        System.out.println(query);
        RecipeBookReaderThread thread = new RecipeBookReaderThread();
        thread.start();
        String response = thread.getRecipesByName(0, query);
        return response;
    }

    @Override
    public String getCookingTimes() {
        //TODO change this to also return the prep_time_id from the DB. Either change the query or create a new view.

        String query = "SELECT * FROM CookingTimeView";
        RecipeBookReaderThread thread = new RecipeBookReaderThread();
        thread.start();
        return thread.getCookingTimes(query);
    }

    @Override
    public String getUnits() {
        String query = "SELECT * FROM Unit";
        RecipeBookReaderThread thread = new RecipeBookReaderThread();
        thread.start();
        return thread.getUnits(query);
    }

    public String buildQueryByTags(String tags) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("    SELECT * FROM RecipeCardView\n" +
                "\n" +
                " INNER JOIN Tags ON Tags.recipe_id = RecipeCardView.recipe_id\n" +
                " INNER JOIN Category ON Category.category_id = Tags.category_id\n" +
                "   WHERE Tags.category_id = ");
        String[] tagArray = tags.split("&=");
        for (int i = 0; i < tagArray.length; i++) {
            //  String test = tagArray[i].replaceAll(",", "");

            if (i == 0) {
                continue;
            }

            if (i == 1) {//If first tag add the first part of the query
                queryBuilder.append("'" + tagArray[i].replaceAll(",", "") + "'");
                continue;
            }
            if (i != tagArray.length) {
                queryBuilder.append("\n    OR Tags.category_id = " + "'" + tagArray[i].replaceAll(",", "") + "'");
            }
            if (i == tagArray.length - 1) {
                queryBuilder.append("GROUP BY name;");
            }

        }
        return queryBuilder.toString();
    }

    @Override
    public String getTags() {
        RecipeBookReaderThread thread = new RecipeBookReaderThread();
        thread.start();
        String response = thread.getTags();
        //       System.out.println(response);//For debugging
        return response;
    }

    @Override
    public String getTags(long recipe_id) {
        RecipeBookReaderThread thread = new RecipeBookReaderThread();
        thread.start();
        String response = thread.getTags(recipe_id);
        System.out.println(response);//For debugging
        return response;
    }

    @Override
    public String getIngredients(Long recipe_id) {
        RecipeBookReaderThread thread = new RecipeBookReaderThread();
        thread.start();
        String response = thread.getIngredients(recipe_id);
        //       System.out.println(response);//For debugging
        return response;
    }

    @Override
    public String getInstructions(Long recipe_id) {
        RecipeBookReaderThread thread = new RecipeBookReaderThread();
        thread.start();
        String response = thread.getInstructions(recipe_id);
        //    System.out.println(response);//For debugging
        return response;
    }

    @Override
    public String getRecipe(Long id) {
        RecipeBookReaderThread thread = new RecipeBookReaderThread();
        thread.start();
        return thread.getRecipe(id);
    }

    @Override
    public Response createRecipe(String gsonPost) {
        System.out.println("POST: "+gsonPost);
       // DBWriter dbWriter = getDBWriter();
        RecipeBookWriterThread thread = new RecipeBookWriterThread(gsonPost);

        return thread.parseRecipe(gsonPost);
    }


//    @Override
//    public Response updateRecipe(String gsonPut) {
//        DBWriter dbWriter = getDBWriter();
//
//        return dbWriter.updateRecipe(gsonPut);
//    }
//
//    @Override
//    public Response deleteRecipe(Long id) {
//        DBWriter dbWriter = getDBWriter();
//        return dbWriter.deleteRecipe(id);
//    }
}
