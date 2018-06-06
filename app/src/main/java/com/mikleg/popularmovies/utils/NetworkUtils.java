package com.mikleg.popularmovies.utils;

import android.content.Context;
import android.net.Uri;

import com.mikleg.popularmovies.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by mikle on 5/16/2018.
 */

public class NetworkUtils {


    private static String key = ApiUtils.getApiKey();
  //  private static String sort = ApiConstants.getPopularity();
    private static String dSort = ApiConstants.getdPopularity();
    private static String adult = "false";
    public static URL buildUrl(String... params) {
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
                .appendQueryParameter("sort_by", dSort)
                .appendQueryParameter("page",page)
                .appendQueryParameter("include_adult",adult)
             //   .appendQueryParameter("primary_release_date.gte","2017")
               // .appendQueryParameter("primary_release_date.lte","2018")
                .build();
        System.out.println(builtUri.toString());
        URL url = null;
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

    public static void setSort(String sort) {
        NetworkUtils.dSort = sort;
    }
    public static void setAdult(String adult) {
        NetworkUtils.adult = adult;
    }
}
