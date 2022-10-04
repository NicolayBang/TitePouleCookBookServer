package com.example.RecipeBook.Views;

import com.example.RecipeBook.Objects.Instruction;

import java.util.ArrayList;

public class InstructionsView {
    private ArrayList<String> instructions = new ArrayList<>();

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public void setIngredients(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

}
