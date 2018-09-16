package com.mikleg.popularmovies.model;

import java.util.ArrayList;

/**
 * Created by mikle on 9/15/2018.
 */

public class Videos {
    private ArrayList<Video> videos = new ArrayList<Video>();
    public void addVideo(Video video){
        videos.add(video);
    }
    public Video getVideo(int indx){
        return videos.get(indx);
    }

}
