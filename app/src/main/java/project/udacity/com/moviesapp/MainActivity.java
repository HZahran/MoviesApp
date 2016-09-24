package project.udacity.com.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;

import project.udacity.com.moviesapp.Adapters.MoviesAdapter;
import project.udacity.com.moviesapp.Models.MoviesModel;
import project.udacity.com.moviesapp.Models.Movie;

public class MainActivity extends AppCompatActivity {

    private MyApplication app;
    private GridView moviesGridView;

    private String sortingType = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (MyApplication) getApplication();

        moviesGridView = (GridView) findViewById(R.id.grid_view_movies);

        getMoviesRequest();
    }

    public void getMoviesRequest() {
        if (!isOnline())
            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        else {
            String moviesUrl = app.MOVIES_BASE_URL + sortingType + app.API_KEY_QUERY;
            JsonObjectRequest moviesRequest = new JsonObjectRequest
                    (Request.Method.GET, moviesUrl, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            Toast.makeText(MainActivity.this, "Successfully Fetched Movies", Toast.LENGTH_SHORT).show();

                            final MoviesModel moviesModel = app.getGson().fromJson(response.toString(), MoviesModel.class);

                            updateView(moviesModel.getResults());

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

    public void updateView(final ArrayList<Movie> moviesData) {
        ArrayList<String> moviesPosterPaths = new ArrayList<String>();

        for (Movie movie : moviesData)
            moviesPosterPaths.add(movie.getPoster_path());

        moviesGridView.setAdapter(new MoviesAdapter(MainActivity.this, moviesPosterPaths, app));
        moviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, moviesData.get(position).getTitle(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MainActivity.this, MovieDetailsActivity.class);
                i.putExtra(app.MOVIE_DETAILS, app.getGson().toJson(moviesData.get(position)));

                startActivity(i);
            }
        });
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
                        updateView(favorites);
                    else
                        Toast.makeText(MainActivity.this, "No Favorites Found", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
