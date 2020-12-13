package io.github.collins993.videostremo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {
    public static final String VIDEO_DATA_EXTRA = "videoData";
    private ArrayList<Video> allVideos;
    private Context context;

    public RecAdapter(ArrayList<Video> allVideos, Context context) {
        this.allVideos = allVideos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rec_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(allVideos.get(position).getTitle());
        holder.author.setText(allVideos.get(position).getAuthor());
        Picasso.get().load(allVideos.get(position).getImageUrl()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putSerializable(VIDEO_DATA_EXTRA, allVideos.get(position));
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allVideos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, author;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageVideo);
            title = itemView.findViewById(R.id.vidTitle);
            cardView = itemView.findViewById(R.id.cv);
            author = itemView.findViewById(R.id.textViewGoogle);
        }
    }
}
