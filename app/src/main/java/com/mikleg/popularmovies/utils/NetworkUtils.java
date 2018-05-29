package com.mikleg.popularmovies.utils;

import android.net.Uri;

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

    final static String API = "api_key";
    final static String key = "";
    final static String sort = "popular";

    public static URL buildUrl(String... params) {
        //    if (params.length == 0) {
        //        return null;
        //    }
        Uri.Builder builtUri = new Uri.Builder();
        builtUri.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(sort)
                .appendQueryParameter("api_key", key)
                .build();

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
        System.out.println("stage1" );
        try {
            InputStream in = urlConnection.getInputStream();
           // System.out.println("stage2" );
            Scanner scanner = new Scanner(in);
           // System.out.println("stage3" );
            scanner.useDelimiter("\\A");
          //  System.out.println("stage4" );
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
}
