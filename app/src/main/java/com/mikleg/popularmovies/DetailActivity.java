package com.mikleg.popularmovies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mikleg.popularmovies.model.Movie;
import com.mikleg.popularmovies.utils.JsonUtils;
import com.mikleg.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;


public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String[]> {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mDescriptionTextView;
    private TextView mDateTextView;
    private TextView mRatingTextView;
    private TextView mTitleTextView;
    private TextView mVotesTextView;
    private ImageView imageView;
    private String[] mData ;
    private static final int LOADER_ID = 11;
    private int mRequests = 1;

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";

    private String mSzString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

       // ImageView imageView = findViewById(R.id.image_iv);
      //  mDescriptionTextView = (TextView) findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

/*        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            mSzString = intent.getStringExtra(Intent.EXTRA_TEXT);
            mDescriptionTextView.setText(mSzString);
        }*/
        Gson gson = new Gson();
        Movie movieData = gson.fromJson(getIntent().getStringExtra("jsonText"), Movie.class);

        populateUI(movieData);
/*        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);*/

       // setTitle(sandwich.getMainName());
        LoaderManager.LoaderCallbacks<String[]> callback = DetailActivity.this;

        final Bundle bundleForLoader = null;

        getSupportLoaderManager().initLoader(LOADER_ID, bundleForLoader, callback);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Movie s) {
        imageView = (ImageView) findViewById(R.id.image_iv);
        Picasso.with(this).load(s.getImage()).into(imageView);
        mDescriptionTextView = (TextView) findViewById(R.id.description_tv);
        mDateTextView = (TextView) findViewById(R.id.release_date_tv);
        mRatingTextView = (TextView) findViewById(R.id.rating_tv);
        mVotesTextView = (TextView) findViewById(R.id.votes_tv);
        mTitleTextView = (TextView) findViewById(R.id.title_tv);
        mDescriptionTextView.append(s.getDescription());
        mDateTextView.append(s.getDate());
        mRatingTextView.append(s.getRating());
        mVotesTextView.append(s.getVotes());
        mTitleTextView.append(s.getTitle());
    }

    @SuppressLint("StaticFieldLeak")
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

                ArrayList<String> result = new ArrayList<>();
                ArrayList<String> result2 = new ArrayList<>();
                int i = 1;

                while (i<= mRequests){
                    //String page = Integer.toString(i);
                    URL debugRequestUrlVedeo = NetworkUtils.buildUrl("video", "15");
                    URL moviesRequestUrlReview = NetworkUtils.buildUrl("review", "15");
                    System.out.println("moviesRequestUrl= " + debugRequestUrlVedeo);
                    System.out.println("moviesRequestUrl= " + moviesRequestUrlReview                                                                                                                                       );
                   // System.out.println("moviesRequestUrl= " + moviesRequestUrl);
                    try {
                        String jsonVideoResponse = NetworkUtils
                                .getResponseFromHttpUrl(debugRequestUrlVedeo);
                        String jsonRevievsResponse = NetworkUtils
                                .getResponseFromHttpUrl(moviesRequestUrlReview);

                        String[] simpleJsonVideoData = JsonUtils.getSimpleMoviesFromJson(DetailActivity.this, jsonVideoResponse );
                        String[] simpleJsonReviewData = JsonUtils.getSimpleMoviesFromJson(DetailActivity.this, jsonRevievsResponse );
//                        int maxPages = JsonUtils.getTotalPages(DetailActivity.this, jsonMovieResponse );
//                        if (i >=maxPages){
//                            i = mRequests;
//                        }

                        for (int j=0; j<simpleJsonVideoData.length; j++){
                            result.add(simpleJsonVideoData[j]);
                        }
                        //TODO check result.addALL


                        for (int j=0; j<simpleJsonReviewData.length; j++){
                            result2.add(simpleJsonReviewData[j]);
                    }

                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                    i++;
                }
                // for (int k=0; k < result.size(); k++ )
                //  System.out.println("Debug result=" + result.get(k));
                return result.toArray(new String[result.size()]);
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
        mData = data ;
/*        if (null != data) {
            //TODo remove that
            mRecyclerView.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    public void onLoaderReset(Loader<String[]> loader) {

    }

}
