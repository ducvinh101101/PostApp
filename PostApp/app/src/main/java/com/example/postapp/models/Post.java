package com.example.postapp.models;
import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("post_Id")
    private int postId;

    @SerializedName("author_Id")
    private int authorId;

    private String title;
    private String content;
    private String timestamp;
    private String media_url;
    private String media_type;

    public Post() {}

    public Post(int postId, int authorId, String title, String content, String timestamp, String media_url, String media_type) {
        this.postId = postId;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
        this.media_url = media_url;
        this.media_type = media_type;
    }

    public int getPostId() { return postId; }
    public int getAuthorId() { return authorId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getTimestamp() { return timestamp; }
    public String getMediaUrl() { return media_url; }
    public String getMediaType() { return media_type; }
}
