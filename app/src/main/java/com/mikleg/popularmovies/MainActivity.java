package com.mikleg.popularmovies;

import android.content.Intent;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.mikleg.popularmovies.utils.JsonUtils;
import com.mikleg.popularmovies.utils.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MyMoviesAdapter.ItemClickListener
    , LoaderCallbacks<String[]>{

    private MyMoviesAdapter mAdapter;
    private static final int LOADER_ID = 10;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int loaderId = LOADER_ID;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //screen width
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float width = dm.widthPixels/dm.xdpi;
        Log.d("debug","Screen width inches : " + dm.widthPixels/dm.xdpi + " Screen height inches : " + dm.heightPixels/dm.ydpi);
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
        LoaderCallbacks<String[]> callback = MainActivity.this;
        Bundle bundleForLoader = null;
        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback);
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
        int debug = 1;
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


}



