package com.mobility.rakan.androidproject.models;

public class News {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String title, description, Link;

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }
}
