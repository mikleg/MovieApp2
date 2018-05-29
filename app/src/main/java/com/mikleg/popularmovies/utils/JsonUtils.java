package com.mikleg.popularmovies.utils;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.mikleg.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by mikle on 5/17/2018.
 */

public class JsonUtils {
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

        /* String array to hold each day's weather String */
        String[] parsedMoviesData = null;

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
        JSONArray deb = moviesJson.names();
        JSONArray moviesArray = moviesJson.getJSONArray(ARRAY);

        parsedMoviesData = new String[moviesArray.length()];


      //  long localDate = System.currentTimeMillis();
     //   long utcDate = SunshineDateUtils.getUTCDateFromLocal(localDate);
     //   long startDay = SunshineDateUtils.normalizeDate(utcDate);

        for (int i = 0; i < moviesArray.length(); i++) {

            /* Get the JSON object representing the day */
            JSONObject movieInfo = moviesArray.getJSONObject(i);


      /*    String title  = movieInfo.getString(TITLE);
          String description  = movieInfo.getString(DESCRIPTION);
          String pop = movieInfo.getString(POP);
          String poster = movieInfo.getString(POSTER);
          String rating = movieInfo.getString(RATING);
          String id = movieInfo.getString(ID);
          String image = movieInfo.getString(POSTER);*/


          Movie el = new Movie(movieInfo.getString(TITLE));
          el.setDescription(movieInfo.getString(DESCRIPTION));
          el.setPop(movieInfo.getString(POP));
          el.setImage("http://image.tmdb.org/t/p/w185" + movieInfo.getString(POSTER));
          el.setRating(movieInfo.getString(RATING));
          el.setRating(movieInfo.getString(ID));



           // parsedMoviesData[i] = title +" "+pop+" "+rating;
         // parsedMoviesData[i] = "http://image.tmdb.org/t/p/w185" + image;
            Gson gson = new Gson();
            parsedMoviesData[i] = gson.toJson(el);
        }

        return parsedMoviesData;
    }


}
