package project.udacity.com.moviesapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.List;

import project.udacity.com.moviesapp.Adapters.ReviewsAdapter;
import project.udacity.com.moviesapp.Adapters.VideosAdapter;
import project.udacity.com.moviesapp.Models.Movie;
import project.udacity.com.moviesapp.Models.Review;
import project.udacity.com.moviesapp.Models.ReviewsModel;
import project.udacity.com.moviesapp.Models.Video;
import project.udacity.com.moviesapp.Models.VideosModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private MyApplication app;
    private MovieDetailsFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        app = (MyApplication) getApplication();

        String movieJSON = getIntent().getStringExtra(MyApplication.MOVIE_DETAILS);
        Movie movie = app.getGson().fromJson(movieJSON, Movie.class);

        listFragment = new MovieDetailsFragment();
        listFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.llDetailsContainer, listFragment).commit();


        String videosUrl = MyApplication.MOVIES_BASE_URL + movie.getId() + "/videos" + MyApplication.API_KEY_QUERY;
        JsonObjectRequest videosRequest = new JsonObjectRequest
                (Request.Method.GET, videosUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        final VideosModel videosModel = app.getGson().fromJson(response.toString(), VideosModel.class);

                        final List<Video> videosData = videosModel.getResults();

                        listFragment.updateVideos(videosData);


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MovieDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        String reviewsUrl = MyApplication.MOVIES_BASE_URL + movie.getId() + "/reviews" + MyApplication.API_KEY_QUERY;

        JsonObjectRequest reviewsRequest = new JsonObjectRequest
                (Request.Method.GET, reviewsUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        final ReviewsModel reviewsModel = app.getGson().fromJson(response.toString(), ReviewsModel.class);

                        final List<Review> reviewsData = reviewsModel.getResults();

                        listFragment.updateReviews(reviewsData);


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MovieDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        app.addToRequestQueue(videosRequest);
        app.addToRequestQueue(reviewsRequest);

    }


}
