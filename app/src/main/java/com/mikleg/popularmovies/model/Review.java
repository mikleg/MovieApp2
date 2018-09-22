package com.mikleg.popularmovies.model;

/**
 * Created by mikle on 9/15/2018.
 */

public class Review {
    private String author ="NA";
    private String content ="NA";


    public Review() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
