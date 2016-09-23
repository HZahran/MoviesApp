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

import project.udacity.com.moviesapp.Models.Video;
import project.udacity.com.moviesapp.MyApplication;
import project.udacity.com.moviesapp.R;

public class VideoAdapter extends BaseAdapter {
    private Context mContext;
    private List<Video> videos;
    private final LayoutInflater inflater;

    private MyApplication app;

    public VideoAdapter(Context c, List<Video> videos, MyApplication app) {
        mContext = c;
        this.app = app;
        this.videos = videos;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return videos.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_row_trailer, null);

        final Video video = videos.get(position);
        ImageButton trailerImageBtn = (ImageButton) view.findViewById(R.id.image_button_trailer);
        TextView trailerNameTextView = (TextView) view.findViewById(R.id.text_view_trailer_name);

        trailerNameTextView.setText(video.getName());

        trailerImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + video.getKey()));
                mContext.startActivity(sendIntent);
            }
        });
        return view;
    }

}