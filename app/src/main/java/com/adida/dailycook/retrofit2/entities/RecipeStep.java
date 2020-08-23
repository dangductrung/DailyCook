package com.adida.dailycook.retrofit2.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeStep {
    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("durationMinute")
    @Expose
    public int durationMinute;

    @SerializedName("order")
    @Expose
    public int order;

    @SerializedName("imageUrl")
    @Expose
    public String imageUrl;

    public RecipeStep(int mStepId,String mDescription,int mDurationMinute,String mImageUrl,int mStepOrder){
        id = mStepId;
        description = mDescription;
        durationMinute = mDurationMinute;
        order = mStepOrder;
        imageUrl = mImageUrl;
    }

    public String toString() {
        return "";
    }
}
