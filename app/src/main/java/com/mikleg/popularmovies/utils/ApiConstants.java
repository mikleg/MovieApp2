package com.mikleg.popularmovies.utils;

import com.mikleg.popularmovies.R;

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

/*    public static String getSortOrderKey(String term)
    {
        String[] tab_names = R.array.pref_sort_order_values;
        String tabname1=tab_names[0];//"My Tab 1"
       // if (term.equalsIgnoreCase(@string/popularity_sort_value))
    }*/
}
