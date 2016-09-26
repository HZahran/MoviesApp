package project.udacity.com.moviesapp.Adapters;

import android.content.ActivityNotFoundException;
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
import project.udacity.com.moviesapp.R;

public class VideosAdapter extends BaseAdapter {
    private Context mContext;
    private List<Video> videos;
    private final LayoutInflater inflater;

    public VideosAdapter(Context c, List<Video> videos) {
        mContext = c;
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

        final Video video = videos.get(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_trailer, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.trailerImageBtn = (ImageButton) convertView.findViewById(R.id.image_button_trailer);
            viewHolder.trailerNameTextView = (TextView) convertView.findViewById(R.id.text_view_trailer_name);
            convertView.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.trailerNameTextView.setText(video.getName());

        viewHolder.trailerImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video.getKey()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + video.getKey()));
                try {
                    mContext.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    mContext.startActivity(webIntent);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageButton trailerImageBtn;
        TextView trailerNameTextView;
    }

}