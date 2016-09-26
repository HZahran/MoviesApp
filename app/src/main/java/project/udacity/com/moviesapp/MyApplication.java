package project.udacity.com.moviesapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import project.udacity.com.moviesapp.Models.Movie;

public class MyApplication extends Application {
    private RequestQueue mRequestQueue;
    private Gson gson;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;


    public final static String MOVIE_DETAILS = "MOVIE_DETAILS";
    private final static String FAVORITE_MOVIES = "FAVORITE_MOVIES";

    public final static String MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/";
    public final static String API_KEY_QUERY = "?api_key=" + BuildConfig.MOVIES_API_KEY;

    public final static String POSTERS_BASE_URL = "http://image.tmdb.org/t/p/w185/";

    @Override
    public void onCreate() {
        super.onCreate();

        gson = new Gson();
        getRequestQueue();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();
    }

    public Gson getGson() {
        return gson;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void addFavorite(Movie movie) {

        ArrayList<Movie> favoritesList = getFavorites();
        if (favoritesList == null)
            favoritesList = new ArrayList<Movie>();


        if (!favoritesList.contains(movie)) {
            favoritesList.add(movie);
            saveFavorites(favoritesList);
        }
    }

    public void removeFavorite(Movie movie) {

        ArrayList<Movie> favoritesList = getFavorites();

        if (favoritesList != null) {
            favoritesList.remove(movie);
            saveFavorites(favoritesList);
        }
    }


    public ArrayList<Movie> getFavorites() {

        ArrayList<Movie> favoritesList;
        if (sharedPref.contains(FAVORITE_MOVIES)) {

            String favoritesJSON = sharedPref.getString(FAVORITE_MOVIES, null);
            favoritesList = getGson().fromJson(favoritesJSON, new TypeToken<ArrayList<Movie>>() {
            }.getType());

        } else
            return null;

        return favoritesList;
    }

    private void saveFavorites(ArrayList<Movie> favoritesList) {

        String favoritesJSON = getGson().toJson(favoritesList);

        editor.putString(FAVORITE_MOVIES, favoritesJSON);
        editor.commit();
    }

    private void removeAllFavorites() {
        editor.remove(FAVORITE_MOVIES);
        editor.commit();
    }
}

