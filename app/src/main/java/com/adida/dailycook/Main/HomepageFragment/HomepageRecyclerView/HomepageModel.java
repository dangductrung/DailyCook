package com.adida.dailycook.Main.HomepageFragment.HomepageRecyclerView;

public class HomepageModel {
    private String recipename;
    private String username;

    public HomepageModel(String recipename, String username) {
        this.recipename = recipename;
        this.username = username;
    }

    public String getRecipeName() {
        return recipename;
    }

    public void setRecipeName(String recipename) {
        this.recipename = recipename;
    }

    public String getUsername() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }
}
