package com.mikleg.popularmovies.model;

/**
 * Created by mikle on 5/28/2018.
 */

public class Movie {

    private String title;
    private String pop = null;
 //   private String poster;
    private String rating = null;
    private String image = null;
    private int votes = 0;
    private String origLang = null;
    private String genres = null;
    private String origTitle = null;
    private String backDrop = null;
    private boolean adult = false;
    private String description = null;
    private String date = null;

    public Movie(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getOrigLang() {
        return origLang;
    }

    public void setOrigLang(String origLang) {
        this.origLang = origLang;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getOrigTitle() {
        return origTitle;
    }

    public void setOrigTitle(String origTitle) {
        this.origTitle = origTitle;
    }

    public String getBackDrop() {
        return backDrop;
    }

    public void setBackDrop(String backDrop) {
        this.backDrop = backDrop;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
