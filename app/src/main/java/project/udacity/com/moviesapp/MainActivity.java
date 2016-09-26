package project.udacity.com.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;

import project.udacity.com.moviesapp.Models.MoviesModel;
import project.udacity.com.moviesapp.Models.Movie;

public class MainActivity extends AppCompatActivity implements MoviesListFragment.OnMovieListener {

    private boolean mTwoPane;

    private MyApplication app;

    private MoviesListFragment listFragment;
    private String sortingType = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout flPanel2 = (FrameLayout) findViewById(R.id.flPanel_Two);
        mTwoPane = flPanel2 != null;

        listFragment = new MoviesListFragment();
        listFragment.setmListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.flPanel_One, listFragment).commit();

        app = (MyApplication) getApplication();


        getMoviesRequest();
    }

    public void getMoviesRequest() {
        if (!isOnline())
            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        else {
            String moviesUrl = MyApplication.MOVIES_BASE_URL + sortingType + MyApplication.API_KEY_QUERY;
            JsonObjectRequest moviesRequest = new JsonObjectRequest
                    (Request.Method.GET, moviesUrl, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            Toast.makeText(MainActivity.this, "Successfully Fetched Movies", Toast.LENGTH_SHORT).show();

                            final MoviesModel moviesModel = app.getGson().fromJson(response.toString(), MoviesModel.class);

                            listFragment.updateView(moviesModel.getResults());

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            app.addToRequestQueue(moviesRequest);
        }
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.most_popular:
                if (!sortingType.equals("popular")) {
                    sortingType = "popular";
                    getMoviesRequest();
                }
                return true;
            case R.id.top_rated:
                if (!sortingType.equals("top_rated")) {
                    sortingType = "top_rated";
                    getMoviesRequest();
                }
                return true;
            case R.id.favorites:
                if (!sortingType.equals("favorite")) {
                    sortingType = "favorite";
                    ArrayList<Movie> favorites = app.getFavorites();
                    if (favorites != null)
                        listFragment.updateView(favorites);
                    else
                        Toast.makeText(MainActivity.this, "No Favorites Found", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMovieSelected(Movie movie) {
        if (mTwoPane) {
            MovieDetailsFragment listFragment = new MovieDetailsFragment();
            Bundle b = new Bundle();
            b.putString(MyApplication.MOVIE_DETAILS, app.getGson().toJson(movie));
            listFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().add(R.id.flPanel_One, listFragment).commit();
        } else {
            Toast.makeText(MainActivity.this, movie.getTitle(), Toast.LENGTH_SHORT).show();

            Intent i = new Intent(MainActivity.this, MovieDetailsActivity.class);
            i.putExtra(MyApplication.MOVIE_DETAILS, app.getGson().toJson(movie));

            startActivity(i);
        }
    }
}
