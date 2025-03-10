package com.example.postapp.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.postapp.R;
import com.example.postapp.models.Post;

import java.io.IOException;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private final Context context;
    private final List<Post> postList;
    private MediaPlayer mediaPlayer; // Giữ một MediaPlayer để tránh lỗi khi phát nhiều lần

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
        this.mediaPlayer = new MediaPlayer();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);

        holder.tvTitle.setText(post.getTitle());
        holder.tvContent.setText(post.getContent());
        holder.tvTimestamp.setText(post.getTimestamp());

        Log.d("Adapter", "Binding post: " + post.getTitle());

        String mediaUrl = post.getMediaUrl();
        holder.resetMediaViews(); // Ẩn tất cả trước khi hiển thị nội dung phù hợp

        if (mediaUrl != null && !mediaUrl.isEmpty()) {
            if (mediaUrl.endsWith(".jpg") || mediaUrl.endsWith(".png") || mediaUrl.endsWith(".jpeg")) {
                holder.imageView.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(mediaUrl)
                        .placeholder(R.drawable.placeholder_image) // Hiển thị ảnh mặc định khi tải
                        .error(R.drawable.error_image) // Hiển thị ảnh lỗi nếu tải thất bại
                        .into(holder.imageView);
            } else if (mediaUrl.endsWith(".mp4") || mediaUrl.endsWith(".avi") || mediaUrl.endsWith(".mkv")) {
                holder.videoView.setVisibility(View.VISIBLE);
                holder.videoView.setVideoURI(Uri.parse(mediaUrl));
                holder.videoView.setOnPreparedListener(mp -> {
                    mp.setLooping(true);
                    mp.start();
                });
            } else if (mediaUrl.endsWith(".mp3") || mediaUrl.endsWith(".wav")) {
                holder.btnPlayAudio.setVisibility(View.VISIBLE);
                holder.btnPlayAudio.setOnClickListener(v -> playAudio(mediaUrl));
            }
        }
    }

    private void playAudio(String audioUrl) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }

        try {
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(MediaPlayer::start);
        } catch (IOException e) {
            Log.e("Adapter", "Error playing audio", e);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent, tvTimestamp;
        ImageView imageView;
        VideoView videoView;
        Button btnPlayAudio;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            imageView = itemView.findViewById(R.id.imageView);
            videoView = itemView.findViewById(R.id.videoView);
            btnPlayAudio = itemView.findViewById(R.id.btnPlayAudio);

        }

        public void resetMediaViews() {
            imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.GONE);
            btnPlayAudio.setVisibility(View.GONE);
        }
    }
}
