package project.udacity.com.moviesapp;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class MyApplication extends Application {
    private RequestQueue mRequestQueue;
    private Gson gson;

    public final static String MOVIE_DETAILS = "MOVIE_DETAILS";

    public final static String MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/";
    public final static String API_KEY_QUERY = "?api_key=" + BuildConfig.MOVIES_API_KEY;

    public final static String POSTERS_BASE_URL = "http://image.tmdb.org/t/p/w185/";

    @Override
    public void onCreate() {
        super.onCreate();

        gson = new Gson();
        getRequestQueue();
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

}

