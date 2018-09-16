package com.mikleg.popularmovies.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by mikle on 5/16/2018.
 */

public class NetworkUtils {


    private static String key = ApiUtils.getApiKey();
  //  private static String sort = ApiConstants.getPopularity();
    private static String mSortDisc = "popularity.desc";
    private static String mAdult = "false";
    private static String mBeginDate = "1900";
    private static String mEndDate = "2018";
    private static String mSort = "popular";

//https://api.themoviedb.org/3/movie/15/videos?api_key=iojefijfjk&language=en-US
//https://api.themoviedb.org/3/movie/15/reviews?api_key=eba056d2c85537bb0f952351ce33b7a8&language=en-US
// http://api.themoviedb.org/3/movie/157336/videos?api_key=###
// https://api.themoviedb.org/3/movie/popular?api_key=eba056d2c85537bb0f952351ce33b7a8
// https://api.themoviedb.org/3/movie/top_rated?api_key=eba056d2c85537bb0f952351ce33b7a8
// https://api.themoviedb.org/3/movie/top_rated/?api_key=eba056d2c85537bb0f952351ce33b7a8

    public static URL buildUrl(String... params) {
        URL url = null;
        String lId = "";
        String lPath ="";
        if (params.length == 0){
            lPath = mSort;
        }
        else {
            lId = params[1];
            if (params[0] == "video")
                lPath = "videos";
            else
                lPath = "reviews";
        }
        Uri.Builder builtUri = new Uri.Builder();
        builtUri.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(lId)
                .appendPath(lPath)
                .appendQueryParameter("api_key", key)
                .build();
        System.out.println("debug built URL=" + builtUri.toString());

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

         return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static void setSortDiscover(String sort) {
        NetworkUtils.mSortDisc = sort;
    }
    public static void setAdult(String adult) {
        NetworkUtils.mAdult = adult;
    }
    public static void setDates(String beginDate, String endDate) {
        NetworkUtils.mBeginDate = beginDate;
        NetworkUtils.mEndDate = endDate;
    }
    public static void setSort(String sort) {
        Log.d(TAG, "setsort1");
        Log.d(TAG, "sort" + sort);
        NetworkUtils.mSort = sort;
    }



}
