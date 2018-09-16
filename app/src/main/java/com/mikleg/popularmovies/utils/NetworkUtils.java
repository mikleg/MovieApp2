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


    public static URL buildUrl2(String... params) {
        String page = "1";
        // the page number
            if (params.length > 0) {
                page = params[0];
            }
        Uri.Builder builtUri = new Uri.Builder();
        builtUri.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("api_key", key)
                .appendQueryParameter("sort_by", mSortDisc)
                .appendQueryParameter("page",page)
                .appendQueryParameter("include_adult", mAdult)
                .appendQueryParameter("primary_release_date.gte", mBeginDate)
                .appendQueryParameter("primary_release_date.lte", mEndDate)
        //debug        .appendQueryParameter("vote_count.gte", "1500")
                .build();
        System.out.println(builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
//https://api.themoviedb.org/3/movie/15/videos?api_key=iojefijfjk&language=en-US
        return url;
    }

    public static URL buildUrl(String... params) {
        //String page = "1";
        // the page number
       /* if (params.length > 0) {
            page = params[0];
        }*/
        URL url = null;
        if (params.length == 0) {
            Uri.Builder builtUri = new Uri.Builder();
            builtUri.scheme("https")
                    .authority("api.themoviedb.org")
                    .appendPath("3")
                    //  .appendPath("discover")
                    .appendPath("movie")
                    .appendPath(mSort)


                    .appendQueryParameter("api_key", key)
                    // .appendQueryParameter("sort_by", mSort)
                    //  .appendQueryParameter("page",page)
                    // .appendQueryParameter("include_adult", mAdult)
                    //  .appendQueryParameter("primary_release_date.gte", mBeginDate)
                    // .appendQueryParameter("primary_release_date.lte", mEndDate)
                    //debug        .appendQueryParameter("vote_count.gte", "1500")
                    .build();
            System.out.println(builtUri.toString());

            try {
                url = new URL(builtUri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

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
