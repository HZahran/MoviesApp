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

import it.sephiroth.android.library.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieHeaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieHeaderFragment extends Fragment {
    private static final String MOVIE_POSTER_PATH = "MOVIE_POSTER";
    private static final String MOVIE_RELEASE_DATE = "MOVIE_RELEASE_DATE";
    private static final String MOVIE_VOTE_AVERAGE = "MOVIE_VOTE_AVERAGE";
    private static final String MOVIE_OVERVIEW = "MOVIE_OVERVIEW";

    private String moviePosterPath;
    private String movieReleaseDate;
    private String movieVoteAverage;
    private String movieOverview;


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

    public static MovieHeaderFragment newInstance(String moviePosterPath, String movieReleaseDate, Double movieVoteAverage, String movieOverview) {
        MovieHeaderFragment fragment = new MovieHeaderFragment();
        Bundle args = new Bundle();
        args.putString(MOVIE_POSTER_PATH, moviePosterPath);
        args.putString(MOVIE_RELEASE_DATE, movieReleaseDate + "");
        args.putString(MOVIE_VOTE_AVERAGE, movieVoteAverage + "/10");
        args.putString(MOVIE_OVERVIEW, movieOverview);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (MyApplication) getActivity().getApplication();

        if (getArguments() != null) {
            moviePosterPath = getArguments().getString(MOVIE_POSTER_PATH);
            movieReleaseDate = getArguments().getString(MOVIE_RELEASE_DATE);
            movieVoteAverage = getArguments().getString(MOVIE_VOTE_AVERAGE);
            movieOverview = getArguments().getString(MOVIE_OVERVIEW);
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
        String posterUrl = app.POSTERS_BASE_URL + moviePosterPath;
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
        favoriteImageButton.setImageResource(isFavorite ? android.R.drawable.star_big_on : android.R.drawable.star_big_off);
        favoriteTextView.setText(isFavorite ? "Added To Favorites" : "Add To Favorites");
        isFavorite = !isFavorite;
    }

}
