package com.adida.dailycook.retrofit2.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chef {
    @SerializedName("username_name")
    @Expose
    private String name;
    @SerializedName("username_age")
    @Expose
    private int age;
    @SerializedName("username_email")
    @Expose
    private String email;
    @SerializedName("username_avatar_url")
    @Expose
    private String avatar;
    @SerializedName("user_gender")
    @Expose
    private String gender;

    public void setName(String name) {this.name = name;};
    public String getName() {return this.name;};

    public void setAge(int age) {this.age = age;};
    public int getAge() {return this.age;};

    public void setEmail(String email) {this.email = email;};
    public String getEmail() {return this.email;};

    public void setAvatar(String avatar) {this.name = avatar;};
    public String getAvatar() {return this.avatar;};

    public String getGender() {return gender;}

    public void setGender(String gender) {this.gender = gender;}
}
