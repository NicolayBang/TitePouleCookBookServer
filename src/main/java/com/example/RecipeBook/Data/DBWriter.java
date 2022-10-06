package com.example.RecipeBook.Data;

import com.example.RecipeBook.JSONHandler.RecipePostJSONAdapter;
import com.example.RecipeBook.Objects.RecipePost;
import com.example.RecipeBook.RestController.RESTControllerImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.mariadb.jdbc.Connection;

import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBWriter {
    public static final RecipePostJSONAdapter recipePostJSONAdapter = new RecipePostJSONAdapter();
    public final Gson gsonPost = new GsonBuilder().registerTypeAdapter(RecipePost.class, recipePostJSONAdapter).serializeNulls().create();
    public DBReader dbReader = new DBReader();
    protected Connection connection = (Connection) RESTControllerImpl.getInstance().connection;
    RESTControllerImpl recipeHandler;
    public DBWriter() {
        init();
    }
    void init() {
        recipeHandler = RESTControllerImpl.getInstance();
    //    connection = (Connection) RESTControllerImpl.connection;
    }
//    public void connectToDB() {
//        try {
//            connection = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/recipe_db_v2", "root", "Roslyn06");
//        } catch (SQLException ignored) {
//
//        }
//    }
    /**
     * Synchronized method that performs the write operation
     * @param sql statement
     * @param columnLabel: if set to null, means no return value is expected (-1)
     * @return
     */
    public synchronized long toDB(String sql, String columnLabel) {
        try {
            ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
            if (columnLabel == null) {
                return -1;
            }
            long outputValue = 0;
            while (resultSet.next()) {
                outputValue = resultSet.getLong(columnLabel);
            }
            return outputValue;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

//    public long queryDB(String sql, String columnLabel) {
//        try {
//            ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
//
//            long outputValue = 0;
//            while (resultSet.next()) {
//                outputValue = resultSet.getLong(columnLabel);
//            }
//            return outputValue;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return -1;
//        }
//    }

    /**
     * This version of send recipe doesn't use RETURNING statements in the sql syntax, meant to work with MariaDB prior to 10.5.0 (Heroku Mariadb dyno uses 10.2.0)
     * @param recipe
     */
    public void sendRecipeToDB(RecipePost recipe) {
        System.out.println("Sending recipe to DB");
        String insertTitle = "INSERT INTO Title (title) VALUE ('" + recipe.getTitle() + "')";
        toDB(insertTitle, "title_id");
        long title_id = toDB("SELECT LAST_INSERT_ID() as id", "id");
        System.out.println("title_id: " + title_id);
        //INSERT INTO Recipe (title_id,description,nb_of_servings, difficulty_id, user_id, prep_time_id)
        String insertDescription = "INSERT INTO Recipe (title_id,description,nb_of_servings, difficulty_id, user_id, prep_time_id) VALUE " +
                "(" + title_id + ",'" + recipe.getDescription() + "'," + recipe.getNb_of_servings() + "," + recipe.getDifficulty_id() + ","
                + recipe.getUser_id() + "," + recipe.getPrep_time_id() + ")";//RETURNING recipe_id
        toDB(insertDescription, "recipe_id");
        long recipe_id = toDB("SELECT LAST_INSERT_ID() as id", "id");
        addToFavourites(recipe_id, recipe.getUser_id());//TODO: remove this line, it is temporary, adds newly created recipe to favourites of user 1

        for (int i = 0; i < recipe.getTags().size(); i++) {
            String insertTag = "INSERT INTO Tags (recipe_id, category_id) VALUE (" + recipe_id + ",'" + recipe.getTags().get(i).getAsString() + "')";
            toDB(insertTag, null);//RETURNING tags_id
        }
        toDB("INSERT INTO Ingredients (recipe_id) VALUE (" + recipe_id + ")",//RETURNING ingredients_id
                "ingredients_id") ;
        long ingredients_id =toDB("SELECT LAST_INSERT_ID() as id", "id");  //

       for (int i=0; i<recipe.getIngredients().size();i++){
           String unitName = recipe.getIngredients().get(i).getUnit();
           String unit_id=null;
           if(unitName.contains("null")){
               unit_id = "1";
           }else {
                String getUnitIdByName = "SELECT unit_id FROM Unit WHERE unit_name = '" + unitName + "'";
                //This query will always return only one result so we can use the first element of the list
                 unit_id = dbReader.queryDB(getUnitIdByName, "unit_id").get(0); //(String) dbReader.queryDB(getUnitIdByName, "unit_id");
           }
           String insertIngredients = "INSERT INTO Ingredient (ingredients_id, ingredient, unit_id, quantity) VALUE (" + ingredients_id + ",'"
                   + recipe.getIngredients().get(i).getName()
                   + "'," + unit_id +"," +recipe.getIngredients().get(i).getAmount() +")";
           toDB(insertIngredients, null);//RETURNING tags_id
       }
        toDB("INSERT INTO Instructions (recipe_id) VALUE (" + recipe_id + ")",//RETURNING instructions_id
                "instructions_id") ;
        long instructions_id = toDB("SELECT LAST_INSERT_ID() as id", "id");  //
            for (int i=0; i<recipe.getInstructions().size();i++){
                String insertInstructions = "INSERT INTO Instruction (instructions_id, instruction, step_id) VALUE (" + instructions_id + ",'"
                        + recipe.getInstructions().get(i).getInstruction()
                        + "'," + i+1 +")";

                toDB(insertInstructions, null);
            }

    }

    public Response parseRecipe(String gsonInput) {
        DBWriter dbWriter = recipeHandler.getDBWriter();
        RecipePost recipe = new RecipePost();
        try {
            recipe = dbWriter.gsonPost.fromJson(gsonInput, RecipePost.class);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
        System.out.println("@@@sendRecipeToDB: " + recipe.getTitle() + " " + recipe.getDescription());
        //TODO Parse gson here
        sendRecipeToDB(recipe);
        return Response.ok("Recipe added").build();
    }

    public void addToFavourites(long recipe_id, long user_id){
        String insertFavourites = "INSERT INTO Favourites (recipe_id, user_id) VALUE (" + recipe_id + "," + user_id + ")";
        toDB(insertFavourites, null);
    }

//    private void sendTagsToDB(JsonArray tags) {
//
//        String sql = "INSERT INTO Title (title) VALUE ('" + tag + "')";
//        writeToDB(sql);
//    }

//    public Response createRecipe(String gsonPost) {
//
//        Card recipe;
//
//        if (isJSONValid(gsonPost)) {
//            recipe = recipeHandler.getDBReader().gson.fromJson(gsonPost, Card.class);
//        } else {
//            recipe = (Card) parseFormDataToRecipe(gsonPost);
//        }
//        currId++;
//       // recipe.setRecipe_id(recipeHandler.getDBReader().currId);
//        RESTControllerImpl.getInstance().getDBReader().recipes.put(recipe.getRecipe_id(), recipe);
//        return Response.ok(recipe).build();
//    }

//    private boolean isJSONValid(String gsonPost) {
//        try {
//            gson.fromJson(gsonPost, Object.class);
//            return true;
//        } catch (com.google.gson.JsonSyntaxException ex) {
//            return false;
//        }
//    }

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
//        card.setTitle(name);
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
//
//    public Response updateRecipe(String gsonPut) {
//        Card recipe = gson.fromJson(String.valueOf(gsonPut), Card.class);
//        Card currRecipe = recipes.get(recipe.getRecipe_id());
//        Response response;
//        if (currRecipe != null) {
//            recipes.put(recipe.getRecipe_id(), recipe);
//            response = Response.ok().build();
//        } else {
//            response = Response.notModified().build();
//        }
//        return response;
//    }
//
//    // @Override
//    public Response deleteRecipe(Long id) {
//        Recipe recipe = recipes.get(id);
//
//        Response response;
//        if (recipe != null) {
//            recipes.remove(id);
//            response = Response.ok().build();
//        } else {
//            response = Response.notModified().build();
//        }
//        return response;
//    }

}
