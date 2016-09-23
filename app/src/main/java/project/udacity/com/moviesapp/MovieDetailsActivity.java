package project.udacity.com.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.List;

import project.udacity.com.moviesapp.Adapters.VideoAdapter;
import project.udacity.com.moviesapp.Models.Movie;
import project.udacity.com.moviesapp.Models.Video;
import project.udacity.com.moviesapp.Models.VideosModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView movieTitleTextView;
    private ListView trailerListView;
    MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        app = (MyApplication) getApplication();

        Movie movie = app.getGson().fromJson(getIntent().getStringExtra(app.MOVIE_DETAILS), Movie.class);

        //Title
        movieTitleTextView = (TextView) findViewById(R.id.text_view_movie_title);
        movieTitleTextView.setText(movie.getTitle());

        //Header
        MovieHeaderFragment movieHeaderFragment = MovieHeaderFragment.newInstance(movie.getPoster_path(), movie.getRelease_date(), movie.getVote_average(), movie.getOverview());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_movie_header, movieHeaderFragment).commit();

        //Trailers
        trailerListView = (ListView) findViewById(R.id.list_view_movie_trailers);

        String videosUrl = app.MOVIES_BASE_URL + movie.getId() + "/videos" + app.API_KEY_QUERY;
        JsonObjectRequest videosRequest = new JsonObjectRequest
                (Request.Method.GET, videosUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(MovieDetailsActivity.this, "Successfully Fetched Movies", Toast.LENGTH_SHORT).show();

                        final VideosModel videosModel = app.getGson().fromJson(response.toString(), VideosModel.class);

                        final List<Video> videosData = videosModel.getResults();

                        trailerListView.setAdapter(new VideoAdapter(MovieDetailsActivity.this, videosData, app));

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MovieDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        app.addToRequestQueue(videosRequest);

    }
}
