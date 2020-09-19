package com.adida.dailycook.retrofit2.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RecipeStep implements Serializable {
    @SerializedName("recipe_id")
    Integer recipe_id;

    @SerializedName("step_id")
    Integer step_id;

    @SerializedName("step_description")
    String step_description;

    @SerializedName("duration_minute")
    Integer duration_minute;

    @SerializedName("step_image_url")
    String step_image_url;

    @SerializedName("step_order")
    Integer step_order;

    public Integer getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(Integer recipe_id) {
        this.recipe_id = recipe_id;
    }

    public Integer getStep_id() {
        return step_id;
    }

    public void setStep_id(Integer step_id) {
        this.step_id = step_id;
    }

    public String getStep_description() {
        return step_description;
    }

    public void setStep_description(String step_description) {
        this.step_description = step_description;
    }

    public Integer getDuration_minute() {
        return duration_minute;
    }

    public void setDuration_minute(Integer duration_minute) {
        this.duration_minute = duration_minute;
    }

    public String getStep_image_url() {
        return step_image_url;
    }

    public void setStep_image_url(String step_image_url) {
        this.step_image_url = step_image_url;
    }

    public Integer getStep_Order() {
        return step_order;
    }

    public void setStep_Order(Integer step_order) {
        this.step_order = step_order;
    }
}
