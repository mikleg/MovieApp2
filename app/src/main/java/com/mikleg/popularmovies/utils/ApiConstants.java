package com.mikleg.popularmovies.utils;

/**
 * Created by mikle on 5/30/2018.
 * This class provides all API constants
 */

public class ApiConstants {
    private final static String popularity = "popular";
    private final static String dPopularity = "popularity.desc"; // discover mode
    private final static String dRating = "vote_average.desc"; // discover mode

    public ApiConstants() {
    }

    public static String getdPopularity() {return dPopularity;}
    public static String getPopularity() {return popularity;}
    public static String getdRating() { return dRating; }
}
