package com.mikleg.popularmovies.model;

/**
 * Created by mikle on 9/15/2018.
 */

public class Video {
    private String name ="NA";
    private String key ="NA";
    private String type ="NA";
    private String site ="NA";

    public Video() {
    }

    public Video(String name, String key, String type, String site) {
        this.name = name;
        this.key = key;
        this.type = type;
        this.site = site;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
