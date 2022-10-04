package com.example.RecipeBook.Objects;

public class Ingredient extends Recipe {
    private String name;
    private long amount;
    private String unit;

    public Ingredient(String ingredient, long amount, String unit_of_measure) {
        super();
        this.name = ingredient;
        this.amount = amount;
        this.unit = unit_of_measure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


}