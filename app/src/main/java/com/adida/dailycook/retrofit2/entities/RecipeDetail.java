package com.adida.dailycook.retrofit2.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeDetail {
    @SerializedName("recipe_id")
    @Expose
    private Integer id;
    @SerializedName("recipe_name")
    @Expose
    private String name;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("time")
    @Expose
    private Integer time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer recipeId) {
        this.id = recipeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String recipeName) {
        this.name = recipeName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

}
