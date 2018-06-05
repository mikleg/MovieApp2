package com.mikleg.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.mikleg.popularmovies.model.Movie;
import com.mikleg.popularmovies.utils.ApiConstants;
import com.mikleg.popularmovies.utils.JsonUtils;
import com.mikleg.popularmovies.utils.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MyMoviesAdapter.ItemClickListener
    , LoaderCallbacks<String[]>, SharedPreferences.OnSharedPreferenceChangeListener{
//TODO check content for completeness
    private MyMoviesAdapter mAdapter;
    private static final int LOADER_ID = 10;
    private RecyclerView mRecyclerView;
    private static boolean PREFERENCES_HAVE_BEEN_UPDATED = false;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //screen width
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float width = dm.widthPixels/dm.xdpi;
        Log.d(TAG,"Screen width inches : " + dm.widthPixels/dm.xdpi + " Screen height inches : " + dm.heightPixels/dm.ydpi);
        int numberOfColumns = 2;
        if (width > 3 && width <= 4.5) numberOfColumns = 3;
        if (width > 4.5 && width <= 6) numberOfColumns = 4;
        if (width > 6) numberOfColumns = 5;
        // data to populate the RecyclerView with

        // set up the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.rvNumbers);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        mAdapter = new MyMoviesAdapter(this);
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        setupSharedPreferences();
        LoaderCallbacks<String[]> callback = MainActivity.this;
        final Bundle bundleForLoader = null;
        getSupportLoaderManager().initLoader(LOADER_ID, bundleForLoader, callback);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + mAdapter.getItem(position) + ", which is at cell position " + position);
        launchDetailActivity(position);
    }

    @Override
    public Loader<String[]> onCreateLoader(int id, final Bundle loaderArgs) {
        return new AsyncTaskLoader<String[]>(this){
            String[] mMoviesData = null;
            @Override
            protected void onStartLoading() {
                if (mMoviesData != null) {
                    deliverResult(mMoviesData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public String[] loadInBackground() {

                URL moviesRequestUrl = NetworkUtils.buildUrl("");

                try {
                    String jsonMovieResponse = NetworkUtils
                            .getResponseFromHttpUrl(moviesRequestUrl);

                    String[] simpleJsonMovieData = JsonUtils.getSimpleMoviesFromJson(MainActivity.this, jsonMovieResponse );
                    System.out.println("Response1=" + simpleJsonMovieData[0]);
                    return simpleJsonMovieData;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(String[] data) {
                mMoviesData = data;
                super.deliverResult(data);
            }



        };

    }








    @Override
    public void onLoadFinished(Loader<String[]> loader, String[] data) {
     //   int debug = 1;
        mAdapter.setMoviesData(data);
        if (null != data) {
            //TODo remove that
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<String[]> loader) {

    }

    private void launchDetailActivity(int position) {

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("jsonText", mAdapter.getItem(position));
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        startActivity(intent);
    }

    /**
     * Methods for setting up the menu
     **/
      @Override
    public boolean onCreateOptionsMenu(Menu menu) {
          MenuInflater inflater = getMenuInflater();
          inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupSharedPreferences() {
        // Get all of the values from shared preferences to set it up

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("sort_order", true)) NetworkUtils.setSort(ApiConstants.getdRating());
            else NetworkUtils.setSort(ApiConstants.getdPopularity());
        this.setOrder(sharedPreferences);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals("sort_order")){
            if (sharedPreferences.getBoolean(key,false)) NetworkUtils.setSort(ApiConstants.getdRating());
            else NetworkUtils.setSort(ApiConstants.getdPopularity());

            if (key.equals(getString(R.string.pref_sort_key))){
 //               NetworkUtils.setSort(sharedPreferences.getString(getString(R.string.pref_sort_key),
 //                       getString(R.string.popularity_sort_value)));
                this.setOrder(sharedPreferences);

            }



            PREFERENCES_HAVE_BEEN_UPDATED = true;
            //////
         //   LoaderCallbacks<String[]> callback = MainActivity.this;
        //    final Bundle bundleForLoader = null;
          //  getSupportLoaderManager().initLoader(LOADER_ID, bundleForLoader, callback);
           // this.onCreate(new Bundle());

        }
    }
    private void setOrder(SharedPreferences sharedPreferences){
        NetworkUtils.setSort(sharedPreferences.getString(getString(R.string.pref_sort_key),
                getString(R.string.popularity_sort_value)));


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        /*
         * If the preferences  have changed since the user was last in
         * MainActivity, perform another query and set the flag to false.

         */
        if (PREFERENCES_HAVE_BEEN_UPDATED) {
            Log.d(TAG, "onStart: preferences were updated");
            getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
            PREFERENCES_HAVE_BEEN_UPDATED = false;
        }
    }
}



