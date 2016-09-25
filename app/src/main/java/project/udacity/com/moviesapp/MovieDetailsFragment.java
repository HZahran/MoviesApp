package project.udacity.com.moviesapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import project.udacity.com.moviesapp.Adapters.ReviewsAdapter;
import project.udacity.com.moviesapp.Adapters.VideosAdapter;
import project.udacity.com.moviesapp.Models.Movie;
import project.udacity.com.moviesapp.Models.Review;
import project.udacity.com.moviesapp.Models.Video;


public class MovieDetailsFragment extends Fragment {


    private TextView movieTitleTextView;
    private ListView trailerListView;
    private ListView reviewListView;
    private Dialog reviewDialog;

    private String movieJSON;
    private Movie movie;
    private MyApplication app;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (MyApplication) getActivity().getApplication();

        if (getArguments() != null) {
            movieJSON = getArguments().getString(MyApplication.MOVIE_DETAILS);
            movie = app.getGson().fromJson(movieJSON, Movie.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        //Title
        movieTitleTextView = (TextView) view.findViewById(R.id.text_view_movie_title);
        movieTitleTextView.setText(movie.getTitle());

        //Header
        MovieHeaderFragment movieHeaderFragment = MovieHeaderFragment.newInstance(movieJSON);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_movie_header, movieHeaderFragment).commit();

        //Trailers
        trailerListView = (ListView) view.findViewById(R.id.list_view_movie_trailers);

        //Review
        reviewListView = (ListView) view.findViewById(R.id.list_view_movie_reviews);

        return view;
    }


    public void updateVideos(List<Video> videosData) {
        trailerListView.setAdapter(null);
        VideosAdapter videosAdapter = new VideosAdapter(getActivity(), videosData, app);
        trailerListView.setAdapter(videosAdapter);
    }

    public void updateReviews(List<Review> reviewsData) {
        reviewListView.setAdapter(null);
        final ReviewsAdapter reviewsAdapter = new ReviewsAdapter(getActivity(), reviewsData, app);
        reviewListView.setAdapter(reviewsAdapter);

        //Review Dialog
        reviewDialog(reviewsData);
    }
    
    public void reviewDialog(final List<Review> reviewsData) {
        AlertDialog.Builder reviewDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public void onStop() {
        super.onStop();
        if (reviewDialog != null)
            reviewDialog.dismiss();
    }

}
