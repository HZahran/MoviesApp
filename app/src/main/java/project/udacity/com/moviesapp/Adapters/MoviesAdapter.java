package project.udacity.com.moviesapp.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;
import project.udacity.com.moviesapp.MyApplication;

public class MoviesAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> moviePostersPaths;

    public MoviesAdapter(Context c, ArrayList<String> moviePostersPaths, MyApplication app) {
        mContext = c;
        this.moviePostersPaths = moviePostersPaths;
    }

    public int getCount() {
        return moviePostersPaths.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }

        String posterUrl = MyApplication.POSTERS_BASE_URL + moviePostersPaths.get(position);
        Picasso.with(mContext).load(posterUrl).into(imageView);

        return imageView;
    }

}