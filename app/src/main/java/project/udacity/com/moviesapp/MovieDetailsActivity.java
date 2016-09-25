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

    private TextView movieTitleTextView;
    private ListView trailerListView;
    private ListView reviewListView;
    private Dialog reviewDialog;

    MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        app = (MyApplication) getApplication();

        String movieJSON = getIntent().getStringExtra(app.MOVIE_DETAILS);
        Movie movie = app.getGson().fromJson(movieJSON, Movie.class);

        //Title
        movieTitleTextView = (TextView) findViewById(R.id.text_view_movie_title);
        movieTitleTextView.setText(movie.getTitle());

        //Header
        MovieHeaderFragment movieHeaderFragment = MovieHeaderFragment.newInstance(movieJSON);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_movie_header, movieHeaderFragment).commit();

        //Trailers
        trailerListView = (ListView) findViewById(R.id.list_view_movie_trailers);

        String videosUrl = app.MOVIES_BASE_URL + movie.getId() + "/videos" + app.API_KEY_QUERY;
        JsonObjectRequest videosRequest = new JsonObjectRequest
                (Request.Method.GET, videosUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        final VideosModel videosModel = app.getGson().fromJson(response.toString(), VideosModel.class);

                        final List<Video> videosData = videosModel.getResults();

                        VideosAdapter videosAdapter = new VideosAdapter(MovieDetailsActivity.this, videosData, app);
                        trailerListView.setAdapter(videosAdapter);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MovieDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        //Review
        reviewListView = (ListView) findViewById(R.id.list_view_movie_reviews);

        String reviewsUrl = app.MOVIES_BASE_URL + movie.getId() + "/reviews" + app.API_KEY_QUERY;

        JsonObjectRequest reviewsRequest = new JsonObjectRequest
                (Request.Method.GET, reviewsUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        final ReviewsModel reviewsModel = app.getGson().fromJson(response.toString(), ReviewsModel.class);

                        final List<Review> reviewsData = reviewsModel.getResults();

                        final ReviewsAdapter reviewsAdapter = new ReviewsAdapter(MovieDetailsActivity.this, reviewsData, app);
                        reviewListView.setAdapter(reviewsAdapter);

                        //Review Dialog
                        reviewDialog(reviewsData);

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

    public void reviewDialog(final List<Review> reviewsData) {
        AlertDialog.Builder reviewDialogBuilder = new AlertDialog.Builder(MovieDetailsActivity.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View reviewDialogView = inflater.inflate(R.layout.dialog_review, null);
        reviewDialogBuilder.setView(reviewDialogView);

        reviewDialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        reviewDialog = reviewDialogBuilder.create();

        final TextView reviewDialogContentTextView = (TextView) reviewDialogView.findViewById(R.id.text_view_dialog_review_content);

        reviewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                reviewDialog.setTitle(reviewsData.get(position).getAuthor());
                reviewDialogContentTextView.setText(reviewsData.get(position).getContent());
                reviewDialog.show();
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        if(reviewDialog != null)
        reviewDialog.dismiss();
    }
}
