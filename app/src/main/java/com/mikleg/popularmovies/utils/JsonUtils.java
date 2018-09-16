package com.mikleg.popularmovies.utils;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.mikleg.popularmovies.model.Movie;
import com.mikleg.popularmovies.model.Video;

import org.json.JSONArray;
//import org.json.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

/**
 * Created by mikle on 5/17/2018.
 */

public class JsonUtils {
    static String  mTotalPages = "1";
    public static String[] getSimpleMoviesFromJson(Context context, String moviesJsonStr)
            throws JSONException {

        final String ARRAY = "results";
        final String TITLE = "title";
        final String POP = "popularity";
        final String ID = "id";
        final String POSTER = "poster_path";
        final String GENRES = "genres_ids";
        final String RATING = "vote_average";
        final String ORIGINAL_TITLE = "original_title";
        final String DESCRIPTION = "overview";
        final String DATE = "release_date";
        final String VOTES = "vote_count";

        final String OWM_MESSAGE_CODE = "cod"; //TODO -- check the same codes from OPENMDB

        List<String> parsedMoviesData = new ArrayList<>();
        //String[] parsedMoviesData = null;

        JSONObject moviesJson = new JSONObject(moviesJsonStr);

        /* Is there an error? */
        if (moviesJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = moviesJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray moviesArray = moviesJson.getJSONArray(ARRAY);
    //    JSONObject max_page = new JSONObject(moviesJsonStr);


        int i = 0;
        while (i < moviesArray.length()) {

            JSONObject movieInfo = moviesArray.getJSONObject(i);


         if ( !movieInfo.getString(POSTER).equals("null")) {

             Movie el = new Movie(movieInfo.getString(TITLE));
             el.setDescription(movieInfo.getString(DESCRIPTION));
             el.setPop(movieInfo.getString(POP));
             el.setImage("http://image.tmdb.org/t/p/w185" + movieInfo.getString(POSTER));
             el.setRating(movieInfo.getString(RATING));
             el.setId(movieInfo.getString(ID));
             el.setTitle(movieInfo.getString(TITLE));
             el.setDate(movieInfo.getString(DATE));
             el.setVotes(movieInfo.getString(VOTES));
             Gson gson = new Gson();
             parsedMoviesData.add(gson.toJson(el));

         }
         i++;

        }

        return parsedMoviesData.toArray(new String[parsedMoviesData.size()]);
    }

    public static String[] getSimpleDataFromJson(Context context, String moviesJsonStr)
            throws JSONException {

        final String NAME ="name";
        final String KEY ="key";
        final String TYPE ="type";
        final String SITE ="site";
        final String ARRAY = "results";
        final String TITLE = "title";
        final String POP = "popularity";
        final String ID = "id";
        final String POSTER = "poster_path";
        final String GENRES = "genres_ids";
        final String RATING = "vote_average";
        final String ORIGINAL_TITLE = "original_title";
        final String DESCRIPTION = "overview";
        final String DATE = "release_date";
        final String VOTES = "vote_count";

        final String OWM_MESSAGE_CODE = "cod"; //TODO -- check the same codes from OPENMDB

        List<String> parsedMoviesData = new ArrayList<>();
        //String[] parsedMoviesData = null;

        JSONObject moviesJson = new JSONObject(moviesJsonStr);

        /* Is there an error? */
        if (moviesJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = moviesJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray moviesArray = moviesJson.getJSONArray(ARRAY);
        //    JSONObject max_page = new JSONObject(moviesJsonStr);


        int i = 0;
        while (i < moviesArray.length()) {

            JSONObject movieInfo = moviesArray.getJSONObject(i);




                //Movie el = new Movie(movieInfo.getString(TITLE));
                Video el = new Video();
                el.setName(movieInfo.getString(NAME));
                el.setKey(movieInfo.getString(KEY));
                el.setSite(movieInfo.getString(SITE));
                el.setType(movieInfo.getString(TYPE));
                Gson gson = new Gson();
                parsedMoviesData.add(gson.toJson(el));


            i++;

        }

        return parsedMoviesData.toArray(new String[parsedMoviesData.size()]);
    }




    public static int getTotalPages(Context context, String moviesJsonStr)  throws JSONException{
        final String PAGES = "total_pages";
        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        mTotalPages = moviesJson.getString(PAGES);
        System.out.println("debug 9 " + mTotalPages);
        return Integer.parseInt(mTotalPages);

    }
}
