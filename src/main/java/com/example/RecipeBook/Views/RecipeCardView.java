package com.example.RecipeBook.Views;

import com.example.RecipeBook.Objects.Card;

import java.util.ArrayList;

//@XmlRootElement(name="RecipeView")
public class RecipeCardView {
    private ArrayList<Card> recipe = new ArrayList<>();
    public ArrayList<Card> getRecipe() {
        return recipe;
    }

    public void setRecipe(ArrayList<Card> recipe) {
        this.recipe = recipe;
    }

   public void addRecipeCard(Card recipeCard){
       recipe.add(recipeCard);
   }
}
