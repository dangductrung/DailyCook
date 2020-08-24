package com.adida.dailycook.retrofit2.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chef {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("age")
    @Expose
    private int age;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("avatar")
    @Expose
    private String avatar;

    public void setId(int id) {this.id = id;};
    public int getId() {return this.id;};

    public void setName(String name) {this.name = name;};
    public String getName() {return this.name;};

    public void setAge(int age) {this.age = age;};
    public int getAge() {return this.age;};

    public void setEmail(String email) {this.email = email;};
    public String getEmail() {return this.email;};

    public void setAvatar(String avatar) {this.name = avatar;};
    public String getAvatar() {return this.avatar;};
}
