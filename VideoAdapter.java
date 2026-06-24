package com.example.assmohamedfaridyoutube;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {

    private Context context;
    private List<VideoModel> list;

    public VideoAdapter(Context context, List<VideoModel> list) {
        this.context = context;
        this.list = list;
    }

    public static class VideoHolder extends RecyclerView.ViewHolder {

        ImageView imgThumbnail;
        TextView txtTitle;
        TextView txtDescription;
        TextView txtChannel;
        TextView txtDate;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);

            imgThumbnail = itemView.findViewById(R.id.imgThumbnail);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtChannel = itemView.findViewById(R.id.txtChannel);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_video, parent, false);

        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {

        VideoModel video = list.get(position);

        holder.txtTitle.setText(video.getTitle());
        holder.txtDescription.setText(video.getDescription());
        holder.txtChannel.setText("Channel: " + video.getChannel());
        holder.txtDate.setText("Published: " + video.getDate());

        Glide.with(context)
                .load(video.getThumbnail())
                .into(holder.imgThumbnail);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}