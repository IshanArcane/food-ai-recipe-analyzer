package com.example.foodai.dto;

import java.util.List;

public class RecipeResponse {
    private String dishName;
    private List<String> ingredients;
    private List<String> instructions;

    public RecipeResponse() {
    }

    public RecipeResponse(String dishName, List<String> ingredients, List<String> instructions) {
        this.dishName = dishName;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }
}
