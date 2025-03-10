
package com.example.postapp.api;

import com.example.postapp.models.Post;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

public interface SupabaseApi {
    String DATABASE_URL = "https://vhqcdiaoqrlcsnqvjpqh.supabase.co/rest/v1/";
    String STORAGE_URL = "https://vhqcdiaoqrlcsnqvjpqh.supabase.co/storage/v1/";
    String SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZocWNkaWFvcXJsY3NucXZqcHFoIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mzg2NTY4NjcsImV4cCI6MjA1NDIzMjg2N30.uZ1zHyL4LMAsy_BcNE8MbJz73jw_9Mhj7dHtVKds6Qw";

    @Headers({
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZocWNkaWFvcXJsY3NucXZqcHFoIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mzg2NTY4NjcsImV4cCI6MjA1NDIzMjg2N30.uZ1zHyL4LMAsy_BcNE8MbJz73jw_9Mhj7dHtVKds6Qw",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZocWNkaWFvcXJsY3NucXZqcHFoIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mzg2NTY4NjcsImV4cCI6MjA1NDIzMjg2N30.uZ1zHyL4LMAsy_BcNE8MbJz73jw_9Mhj7dHtVKds6Qw",
            "Content-Type: application/json"
    })

    @GET("posts")
    Call<List<Post>> getPosts();

    @POST("posts")
    @Headers({
            "apikey: " + SupabaseApi.SUPABASE_KEY,
            "Authorization: Bearer " + SupabaseApi.SUPABASE_KEY,
            "Content-Type: application/json"
    })
    Call<Void> createPost(@Body Map<String, Object> post);

    @POST("comments")
    Call<Void> addComment(@Body Map<String, Object> comment);

    // upload file

    @PUT
    Call<ResponseBody> uploadFile(
            @Url String url,
            @HeaderMap Map<String, String> headers,
            @Body RequestBody fileBody
    );
}
