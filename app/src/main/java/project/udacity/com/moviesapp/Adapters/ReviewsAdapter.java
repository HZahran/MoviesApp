package project.udacity.com.moviesapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import project.udacity.com.moviesapp.Models.Review;
import project.udacity.com.moviesapp.Models.Video;
import project.udacity.com.moviesapp.MyApplication;
import project.udacity.com.moviesapp.R;

public class ReviewsAdapter extends BaseAdapter {
    private Context mContext;
    private List<Review> reviews;
    private final LayoutInflater inflater;

    private MyApplication app;

    public ReviewsAdapter(Context c, List<Review> reviews, MyApplication app) {
        mContext = c;
        this.app = app;
        this.reviews = reviews;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return reviews.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_row_review, null);

        final Review review = reviews.get(position);
        TextView reviewTitleTextView = (TextView) view.findViewById(R.id.text_view_review_title);
        TextView reviewContentTextView = (TextView) view.findViewById(R.id.text_view_review_content);

        reviewTitleTextView.setText(review.getAuthor());
        reviewContentTextView.setText(review.getContent());

        return view;
    }

}