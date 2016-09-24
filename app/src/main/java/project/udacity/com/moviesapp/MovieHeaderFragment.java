package project.udacity.com.moviesapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import it.sephiroth.android.library.picasso.Picasso;
import project.udacity.com.moviesapp.Models.Movie;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieHeaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieHeaderFragment extends Fragment {
    private static final String MOVIE_INSTANCE = "MOVIE_INSTANCE";

    private String moviePosterPath;
    private String movieReleaseDate;
    private String movieVoteAverage;
    private String movieOverview;

    private Movie movie;
    private ImageView moviePosterImgView;
    private TextView movieReleaseDateTextView;
    private TextView movieVoteAverageTextView;
    private TextView movieOverviewTextView;

    private ImageButton favoriteImageButton;
    private TextView favoriteTextView;
    private boolean isFavorite;

    private MyApplication app;

    public MovieHeaderFragment() {
        // Required empty public constructor
    }

    public static MovieHeaderFragment newInstance(String movieJSON) {
        MovieHeaderFragment fragment = new MovieHeaderFragment();
        Bundle args = new Bundle();
        args.putString(MOVIE_INSTANCE, movieJSON);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (MyApplication) getActivity().getApplication();

        if (getArguments() != null) {
            String movieJSON = getArguments().getString(MOVIE_INSTANCE);
            movie = app.getGson().fromJson(movieJSON, Movie.class);

            moviePosterPath = movie.getPoster_path();
            movieReleaseDate = movie.getRelease_date() + "";
            movieVoteAverage = movie.getVote_average() + "/10";
            movieOverview = movie.getOverview();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_header, container, false);

        moviePosterImgView = (ImageView) view.findViewById(R.id.image_view_movie_poster);
        movieReleaseDateTextView = (TextView) view.findViewById(R.id.text_view_movie_date);
        movieVoteAverageTextView = (TextView) view.findViewById(R.id.text_view_movie_vote_average);
        movieOverviewTextView = (TextView) view.findViewById(R.id.text_view_movie_overview);

        //Poster
        String posterUrl = MyApplication.POSTERS_BASE_URL + moviePosterPath;
        Picasso.with(getActivity()).load(posterUrl).into(moviePosterImgView);

        //Release Date
        movieReleaseDateTextView.setText(movieReleaseDate);

        //Vote Avg
        movieVoteAverageTextView.setText(movieVoteAverage);

        //Favorite
        favoriteImageButton = (ImageButton) view.findViewById(R.id.image_button_movie_favorite);
        favoriteTextView = (TextView) view.findViewById(R.id.text_view_movie_favorite);
        favoriteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavorite();
            }
        });

        //OverView
        movieOverviewTextView.setText(movieOverview);
        return view;
    }

    public void toggleFavorite() {
        if (!isFavorite) {
            app.addFavorite(movie);
            favoriteImageButton.setImageResource(android.R.drawable.star_big_on);
            favoriteTextView.setText("Favorite");
            Toast.makeText(getActivity(), "Added To Favorites", Toast.LENGTH_SHORT).show();
        } else {
            app.removeFavorite(movie);
            favoriteImageButton.setImageResource(android.R.drawable.star_big_off);
            favoriteTextView.setText("Add To Favorites");
            Toast.makeText(getActivity(), "Removed From Favorites", Toast.LENGTH_SHORT).show();
        }

        isFavorite = !isFavorite;
    }

}
