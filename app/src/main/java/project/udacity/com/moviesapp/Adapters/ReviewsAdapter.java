package project.udacity.com.moviesapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import project.udacity.com.moviesapp.Models.Review;
import project.udacity.com.moviesapp.R;

public class ReviewsAdapter extends BaseAdapter {
    private Context mContext;
    private List<Review> reviews;
    private final LayoutInflater inflater;


    public ReviewsAdapter(Context c, List<Review> reviews) {
        mContext = c;
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

        final Review review = reviews.get(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_review, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.reviewTitleTextView = (TextView) convertView.findViewById(R.id.text_view_review_title);
            viewHolder.reviewContentTextView = (TextView) convertView.findViewById(R.id.text_view_review_content);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.reviewTitleTextView.setText(review.getAuthor());
        viewHolder.reviewContentTextView.setText(review.getContent());

        return convertView;
    }

    class ViewHolder {
        TextView reviewTitleTextView;
        TextView reviewContentTextView;
    }

}