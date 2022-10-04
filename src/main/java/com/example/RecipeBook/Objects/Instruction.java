package com.example.RecipeBook.Objects;

public class Instruction extends Recipe {
    private String instruction;
    private String recipe_name;
    private String step_nb;

    public String getRecipe_name() {
        return recipe_name;
    }

    public Instruction setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
        return this;
    }

    public String getStep_nb() {
        return step_nb;
    }

    public Instruction setStep_nb(String step_nb) {
        this.step_nb = step_nb;
        return this;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

}
