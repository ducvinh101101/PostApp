package com.example.postapp.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.postapp.R;
import com.example.postapp.api.ApiClient;
import com.example.postapp.models.Post;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        holder.countLike.setText(String.valueOf(post.getCount_like()));
        holder.countComment.setText(String.valueOf(post.getCount_comment()));
        checkLikeStatus(post.getPostId(), holder);
        holder.btnLike.setOnClickListener(v -> {
            if (holder.isLiked) {
                removeLike(post.getPostId(), holder);
            } else {
                addLike(post.getPostId(), holder);
            }
        });
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
            }
//            else if (mediaUrl.endsWith(".mp4") || mediaUrl.endsWith(".avi") || mediaUrl.endsWith(".mkv")) {
//                holder.videoView.setVisibility(View.VISIBLE);
//                holder.videoView.setVideoURI(Uri.parse(mediaUrl));
//                holder.videoView.setOnPreparedListener(mp -> {
//                    mp.setLooping(true);
//                    mp.start();
//                });
//            } else if (mediaUrl.endsWith(".mp3") || mediaUrl.endsWith(".wav")) {
//                holder.btnPlayAudio.setVisibility(View.VISIBLE);
//                holder.btnPlayAudio.setOnClickListener(v -> playAudio(mediaUrl));
//            }
        }
    }
    private void checkLikeStatus(int postId, PostViewHolder holder) {
        int userId = 1; // Lấy ID người dùng hiện tại
//        int userId = getCurrentUserId();
        ApiClient.getDatabaseApi().checkLikeStatus("eq." + postId, "eq." + userId, "*")
                .enqueue(new Callback<List<Map<String, Object>>>() {
                    @Override
                    public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                        if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                            Log.d("Like", "Người dùng đã like bài viết");
                            holder.isLiked = true;
                            holder.btnLike.setImageResource(R.drawable.button_liked);
                        } else {
                            Log.d("Like", "Người dùng chưa like bài viết");
                            holder.isLiked = false;
                            holder.btnLike.setImageResource(R.drawable.button_like);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {
                        Log.e("Like", "Lỗi kiểm tra like", t);
                    }
                });

    }


    private void addLike(int postId, PostViewHolder holder) {
        Map<String, Object> body = new HashMap<>();
        body.put("post_id", postId);
        body.put("user_id", 1);  // Cần sửa nếu có userId thực tế
//       body.put("user_id", getCurrentUserId());

        holder.isLiked = true;
        int currentLikeCount = Integer.parseInt(holder.countLike.getText().toString());
        int newLikeCount = currentLikeCount + 1;
        holder.countLike.setText(String.valueOf(newLikeCount));
        holder.btnLike.setImageResource(R.drawable.button_liked);

        // Gửi request đến API
        ApiClient.getDatabaseApi().addLike(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    // Nếu lỗi, rollback UI
                    Log.e("Like", "Lỗi thêm like: " + response.errorBody());
                    rollbackLikeUI(holder, currentLikeCount, false);
                } else {
                    updateLikeCount(postId, newLikeCount);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Like", "Lỗi kết nối khi thêm like", t);
                rollbackLikeUI(holder, currentLikeCount, false);
            }
        });
    }

    private void removeLike(int postId, PostViewHolder holder) {
        String userId = "1"; // Cần lấy userId thực tế từ session hoặc database
        String url = "https://vhqcdiaoqrlcsnqvjpqh.supabase.co/rest/v1/likes?" + "post_id=eq." + postId + "&user_id=eq." + userId;

        // Cập nhật UI ngay lập tức
        holder.isLiked = false;
        int currentLikeCount = Integer.parseInt(holder.countLike.getText().toString());
        int newLikeCount = currentLikeCount - 1;
        holder.countLike.setText(String.valueOf(currentLikeCount - 1));
        holder.btnLike.setImageResource(R.drawable.button_like);

        // Gửi request đến API
        ApiClient.getDatabaseApi().removeLike(url).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Log.e("Like", "Lỗi xóa like: " + response.errorBody());
                    holder.isLiked = true;
                    holder.countLike.setText(String.valueOf(currentLikeCount));
                    updateLikeCount(postId, newLikeCount);
                    holder.btnLike.setImageResource(R.drawable.button_liked);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Like", "Lỗi kết nối API: " + t.getMessage());
                holder.isLiked = true;
                holder.countLike.setText(String.valueOf(currentLikeCount));
                holder.btnLike.setImageResource(R.drawable.button_liked);
            }
        });
    }
    // Hàm rollback UI khi request thất bại
    private void rollbackLikeUI(PostViewHolder holder, int likeCount, boolean isLiked) {
        holder.isLiked = isLiked;
        holder.countLike.setText(String.valueOf(likeCount));
        holder.btnLike.setImageResource(isLiked ? R.drawable.button_liked : R.drawable.button_like);
    }
    private void updateLikeCount(int postId, int newLikeCount) {
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("count_like", newLikeCount);

        ApiClient.getDatabaseApi().updateLike("eq." + postId, updateData)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Log.d("API", "Cập nhật like thành công!");
                        } else {
                            Log.e("API", "Lỗi cập nhật like: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("API", "Lỗi kết nối khi cập nhật like", t);
                    }
                });
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
        TextView tvTitle, tvContent, tvTimestamp,countLike, countComment;
        ImageView imageView;
        ImageButton btnLike, btnComment;
        boolean isLiked = false;
//        VideoView videoView;
//        Button btnPlayAudio;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            imageView = itemView.findViewById(R.id.imageView);
            btnComment = itemView.findViewById(R.id.button_comment);
            btnLike = itemView.findViewById(R.id.button_like);
            countLike = itemView.findViewById(R.id.countLike);
            countComment = itemView.findViewById(R.id.countComment);
//            videoView = itemView.findViewById(R.id.videoView);
//            btnPlayAudio = itemView.findViewById(R.id.btnPlayAudio);

        }

        public void resetMediaViews() {
            imageView.setVisibility(View.GONE);
//            videoView.setVisibility(View.GONE);
//            btnPlayAudio.setVisibility(View.GONE);
        }
    }
}
