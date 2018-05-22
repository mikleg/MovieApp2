package com.mikleg.popularmovies;

import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.mikleg.popularmovies.utils.JsonUtils;
import com.mikleg.popularmovies.utils.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MoviesAdapterOnClickHandler
    , LoaderCallbacks<String[]>

{

    private static final int NUM_LIST_ITEMS = 25;
    private static final int LOADER_ID = 10;


    private MovieAdapter mAdapter;
    private RecyclerView mRecyclerView;
    public String[] aaa;

    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int loaderId = LOADER_ID;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.movies_tv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this ,LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
       // loadMoviesData();
        //TODO test that
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MovieAdapter(this, NUM_LIST_ITEMS, this);
        mRecyclerView.setAdapter(mAdapter);
        LoaderCallbacks<String[]> callback = MainActivity.this;
        Bundle bundleForLoader = null;
        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback);
        int debug =1;

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


    @Override
    public void onListItemClick(int clickedItemIndex) {

        if (mToast != null) {
            mToast.cancel();
        }
        //loadMoviesData();
        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
        mToast.show();
    }

/*    private void loadMoviesData() {

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

                String[] simpleJsonMovieData = JsonUtils.getSimpleMoviesFromJson(MainActivity.this, jsonMovieResponse );
                System.out.println("Response1=" + simpleJsonMovieData[0]);
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
    }*/
}



