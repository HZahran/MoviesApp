package project.udacity.com.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import project.udacity.com.moviesapp.Adapters.MoviesAdapter;
import project.udacity.com.moviesapp.Models.Movie;


public class MoviesListFragment extends Fragment {

    private MyApplication app;
    private GridView moviesGridView;
    private OnMovieListener mListener;

    public MoviesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MyApplication) getActivity().getApplication();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_list, container, false);
        moviesGridView = (GridView) view.findViewById(R.id.grid_view_movies);
        return view;
    }

    public void updateView(final ArrayList<Movie> moviesData) {
        ArrayList<String> moviesPosterPaths = new ArrayList<String>();

        for (Movie movie : moviesData)
            moviesPosterPaths.add(movie.getPoster_path());

        moviesGridView.setAdapter(null);
        moviesGridView.setAdapter(new MoviesAdapter(getActivity(), moviesPosterPaths, app));
        moviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onMovieSelected(moviesData.get(position));
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMovieListener) {
            mListener = (OnMovieListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMovieListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnMovieListener {
        void onMovieSelected(Movie movie);
    }

    public OnMovieListener getmListener() {
        return mListener;
    }

    public void setmListener(MoviesListFragment.OnMovieListener mListener) {
        this.mListener = mListener;
    }

}
