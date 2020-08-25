package com.adida.dailycook.retrofit2.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe {
    @SerializedName("recipe")
    @Expose
    private RecipeDetail recipe;
    @SerializedName("chef")
    @Expose
    private Chef chef;

    public RecipeDetail getRecipeDetail() {
        return recipe;
    }

    public void setRecipeDetail(RecipeDetail recipe) {
        this.recipe = recipe;
    }

    public Chef getChef() {
        return chef;
    }

    public void setChef(Chef chef) {
        this.chef = chef;
    }
}
