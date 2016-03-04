package com.example.khoa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.khoa.firstapplication.R;
import com.example.khoa.model.Post;

import java.util.List;

/**
 * Created by khoa.nguyen on 3/4/2016.
 */
public class PostsAdapter extends ArrayAdapter<Post> {
    // View lookup cache
    private static class ViewHolder {
        TextView post;
    }

    public PostsAdapter(Context context, List<Post> posts) {
        super(context, R.layout.item_post, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get data item for this position
        Post post = getItem(position);
        // Check if existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // View lookup stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_post, parent, false);
            viewHolder.post = (TextView) convertView.findViewById(R.id.tvPost);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.post.setText(post.getContent());
        // Render the completed view to render on the screen.
        return convertView;
    }
}
