package com.mikleg.popularmovies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mikleg.popularmovies.model.Movie;
import com.mikleg.popularmovies.model.Review;
import com.mikleg.popularmovies.model.Video;
import com.mikleg.popularmovies.utils.JsonUtils;
import com.mikleg.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String[]> {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mDescriptionTextView;
    private TextView mDateTextView;
    private TextView mRatingTextView;
    private TextView mTitleTextView;
    private TextView mVotesTextView;
    private List<TextView> mVideoTextViews = new ArrayList<TextView>();
    private List<TextView> mReviewTextViews = new ArrayList<TextView>();
    private ImageView imageView;
    private String[] mData ;
    private static final int LOADER_ID = 11;
    private int mRequests = 1;
    private String mMovieId;



    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";

    private String mSzString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        Gson gson = new Gson();
        Movie movieData = gson.fromJson(getIntent().getStringExtra("jsonText"), Movie.class);

        LoaderManager.LoaderCallbacks<String[]> callback = DetailActivity.this;
        final Bundle bundleForLoader = null;
        getSupportLoaderManager().initLoader(LOADER_ID, bundleForLoader, callback);
        mMovieId = movieData.getId();
        populateUI(movieData);
        for (Integer i =0; i < 6; i++){
            String name = "videos_tv" + i.toString();
            String name2 = "reviews_tv" + i.toString();
            int id = getResources().getIdentifier(name, "id", getPackageName());
            int id2 = getResources().getIdentifier(name2, "id", getPackageName());
            TextView textView = (TextView) findViewById(id);
            TextView textView2 = (TextView) findViewById(id2);
            textView.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.INVISIBLE);
            mVideoTextViews.add(textView);
            mReviewTextViews.add(textView2);
        }



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
       // System.out.println(mData.length);
        //mVideoTextView.append(mData[0]);

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
               // ArrayList<String> result2 = new ArrayList<>();
                int i = 1;

                while (i<= mRequests){
                    //String page = Integer.toString(i);
                    URL debugRequestUrlVedeo = NetworkUtils.buildUrl("video", mMovieId);
                    URL moviesRequestUrlReview = NetworkUtils.buildUrl("review", mMovieId);
                    System.out.println("moviesRequestUrl= " + debugRequestUrlVedeo);
                    System.out.println("moviesRequestUrl= " + moviesRequestUrlReview                                                                                                                                       );
                   // System.out.println("moviesRequestUrl= " + moviesRequestUrl);
                    try {
                        String jsonVideoResponse = NetworkUtils
                                .getResponseFromHttpUrl(debugRequestUrlVedeo);
                        String jsonRevievsResponse = NetworkUtils
                                .getResponseFromHttpUrl(moviesRequestUrlReview);

                        String[] simpleJsonVideoData = JsonUtils.getSimpleDataFromJson(DetailActivity.this, jsonVideoResponse );
                        String[] simpleJsonReviewData = JsonUtils.getSimpleDataFromJson(DetailActivity.this, jsonRevievsResponse );
//                        int maxPages = JsonUtils.getTotalPages(DetailActivity.this, jsonMovieResponse );
//                        if (i >=maxPages){
//                            i = mRequests;
//                        }

                        for (int j=0; j<simpleJsonVideoData.length; j++){
                            result.add(simpleJsonVideoData[j]);
                        }
                        Integer divider = result.size();


                        for (int j=0; j<simpleJsonReviewData.length; j++){
                            result.add(simpleJsonReviewData[j]);
                        }
                        //how long is the videos list:
                        result.add(divider.toString());
                        //TODO check result.addALL


                  /*      for (int j=0; j<simpleJsonReviewData.length; j++){
                            result2.add(simpleJsonReviewData[j]);
                    }*/

                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                    i++;
                }

                String[] debug = result.toArray(new String[result.size()]);
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
        System.out.println("debug onLoadFinished");
        addVideosAndReviews();

    }

    @Override
    public void onLoaderReset(Loader<String[]> loader) {
    }

    private void addVideosAndReviews(){
        Integer videosTotal = Math.min(Integer.parseInt(mData[mData.length-1]), 6);
        Gson gson = new Gson();
        for (Integer i = 0; i < videosTotal; i++){
            Video video = gson.fromJson(mData[i], Video.class);
            mVideoTextViews.get(i).append(video.getName().substring(0, Math.min(video.getName().length(), 60)));
            mVideoTextViews.get(i).setVisibility(View.VISIBLE);
        }

        for (Integer i = videosTotal; i < mData.length -1 && i < 6 + videosTotal; i++){
            Review review = gson.fromJson(mData[i], Review.class);
            mReviewTextViews.get(i - videosTotal).append(review.getContent());
            mReviewTextViews.get(i- videosTotal).setVisibility(View.VISIBLE);
        }


    }


}
