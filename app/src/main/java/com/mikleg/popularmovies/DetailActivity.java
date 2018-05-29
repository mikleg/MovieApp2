package com.mikleg.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mikleg.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mDescriptionTextView;
    private TextView mOriginTextView;
    private TextView mOtherNamesTextView;
    private TextView mIngredientsTextView;
    private ImageView imageView;

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";

    private String mSzString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

     //   ImageView ingredientsIv = findViewById(R.id.image_iv);
        mDescriptionTextView = (TextView) findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            mSzString = intent.getStringExtra(Intent.EXTRA_TEXT);
            mDescriptionTextView.setText(mSzString);
        }
        Gson gson = new Gson();
        Movie movieData = gson.fromJson(getIntent().getStringExtra("jsonText"), Movie.class);

        populateUI(movieData);
/*        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);*/

       // setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Movie s) {
      //  imageView = (ImageView) findViewById(R.id.image_iv);
        mDescriptionTextView = (TextView) findViewById(R.id.description_tv);
/*        mOriginTextView = (TextView) findViewById(R.id.origin_tv);
        mOtherNamesTextView = (TextView) findViewById(R.id.also_known_tv);
        mIngredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        mOriginTextView.append(s.getPlaceOfOrigin());*/
        mDescriptionTextView.append(s.getDescription());
       // for (String name : s.getAlsoKnownAs()) mOtherNamesTextView.append(name + "; ");
       // for (String ing : s.getIngredients()) mIngredientsTextView.append(ing + "; ");


    }
}
