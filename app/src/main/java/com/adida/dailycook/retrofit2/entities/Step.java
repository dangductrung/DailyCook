package com.adida.dailycook.retrofit2.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step {
    @SerializedName("recipe_id")
    @Expose
    private int rid;
    @SerializedName("step_id")
    @Expose
    private int sid;
    @SerializedName("step_description")
    @Expose
    private String description;
    @SerializedName("duration_minute")
    @Expose
    private int duration;
    @SerializedName("step_image_url")
    @Expose
    private String url;
    @SerializedName("step_order")
    @Expose
    private int order;

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
