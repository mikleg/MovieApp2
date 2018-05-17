package com.mikleg.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mikleg.popularmovies.utils.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.ListItemClickListener {

    private static final int NUM_LIST_ITEMS = 25;


    private MovieAdapter mAdapter;
    private RecyclerView mNumbersList;


    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNumbersList = (RecyclerView) findViewById(R.id.movies_tv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumbersList.setLayoutManager(layoutManager);
        mNumbersList.setHasFixedSize(true);
        mAdapter = new MovieAdapter(NUM_LIST_ITEMS, this);
        mNumbersList.setAdapter(mAdapter);

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        if (mToast != null) {
            mToast.cancel();
        }
        loadMoviesData();
        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
        mToast.show();
    }

    private void loadMoviesData() {

        new FetchMoviesTask().execute("");
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, String[]> {

        @Override

        protected String[] doInBackground(String... params) {


            //    if (params.length == 0) {
            //        return null;
            //    }


            URL moviesRequestUrl = NetworkUtils.buildUrl(params);

            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(moviesRequestUrl);

        //        String[] simpleJsonWeatherData = OpenWeatherJsonUtils
         //               .getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);
                String[] simpleJsonMovieData = {"",""};
                simpleJsonMovieData[0] = jsonMovieResponse;
                return simpleJsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] moviesData) {
            if (moviesData != null) {

                System.out.println("Response=" + moviesData[0]);
            }
        }
    }
}



