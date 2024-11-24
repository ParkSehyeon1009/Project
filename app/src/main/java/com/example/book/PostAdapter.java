package com.example.book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.book.UserService.Post;

import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {
    private List<Post> postList;

    public PostAdapter(Context context, List<Post> posts) {
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // View 재사용을 위해 convertView 확인
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_post, parent, false);
        }

        Post post = getItem(position);

        TextView titleTextView = convertView.findViewById(R.id.textViewTitle);
        TextView authorTextView = convertView.findViewById(R.id.textViewAuthor);
        TextView createdAtTextView = convertView.findViewById(R.id.textViewCreatedAt);

        titleTextView.setText(post.getTitle());
        authorTextView.setText("By " + post.getAuthor());
        createdAtTextView.setText(post.getCreated_at());

        return convertView;
    }
    public void updateList(List<Post> newList) {
        postList.clear();
        postList.addAll(newList);
        notifyDataSetChanged();
    }
}

