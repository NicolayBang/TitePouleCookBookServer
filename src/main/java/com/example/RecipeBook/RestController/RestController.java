package com.example.RecipeBook.RestController;

//import HTTPResponse.HTTPResponse;
//import org.springframework.web.bind.annotation.PathVariable;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//@CrossOrigin
@Path("/recipe_handler")
public interface RestController {

    @Path("/recipes")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    String getRecipesByTags();

    @Path("/recipes/{tags}")
    @GET
    String getRecipesByTags(@PathParam("tags")String tags);

    @Path("/recipes_by_name/{name}")
    @GET
    String getRecipesByName(@PathParam("name")String name);

    /**
     * TODO:Either have this method take a prep_time value as parameter and query the database to get the prep_time_id
     * TODO: OR change the query to return the prep_time_id as well as the prep_time so it can be sent back to the backend by the user.
     * TODO: OR create a new view that returns the prep_time_id and the prep_time,
     * TODO: OR implement caching and have the common values for prep_time_id and prep_time stored in a map; this can also be done for all the other tables that are very rarely written to (and never by the server).
     * @return
     */
    @Path("/cooking_times")
    @GET
    String getCookingTimes();

    @Path("/units")
    @GET
    String getUnits();

    @Path("/tags")
    @GET
    String getTags();

    @Path("/tags/{recipe_id}")
    @GET
    String getTags(@PathParam("recipe_id")long recipe_id);

    @Path("/ingredients/{recipe_id}")
    @GET
    String getIngredients(@PathParam("recipe_id") Long recipe_id);

    @Path("/instructions/{recipe_id}")
    @GET
    String getInstructions(@PathParam("recipe_id") Long recipe_id);

    @Path("/recipe/{recipe_id}")
    @GET
    String getRecipe(@PathParam("recipe_id") Long recipe_id);

    @Path("/recipes")
    @POST
    Response createRecipe(String gsonPost);

//    @Path("/recipes")
//    @PUT
//    Response updateRecipe(String gsonPut);
//
//    @Path("/recipe/{recipe_id}")
//    @DELETE
//    Response deleteRecipe(@PathParam("recipe_id") Long id);

    /*
   GET recipes: localhost:8080/recipe_book/services/recipe_handler/recipes
    GET tags: localhost:8080/recipe_book/services/recipe_handler/tags
   GET recipe: localhost:8080/recipe_book/services/recipe_handler/recipe/1
   GET recipes (with tags): http://localhost:8080/recipe_book/services/recipe_handler/recipes/Gastro&=American
    localhost:8080/recipe_book/services/recipe_handler/ingredients/1
    localhost:8080/recipe_book/services/recipe_handler/instructions/1
   POST --> localhost:8080/recipe_book/services/recipe_handler/recipes

GET books: localhost:8080/a2/services/bookhandler/books
GET book: localhost:8080/a2/services/bookhandler/book/123
POST --> localhost:8080/a2/services/bookhandler/books
--> <Book>
		<title>Lord of The Rings</title>
	</Book>
PUT:localhost:8080/a2/services/bookhandler/books
    <Book>
        <id>124</id>
        <title>Lord of The Rings: The Two Towers</title>
    </Book>
*/


}
