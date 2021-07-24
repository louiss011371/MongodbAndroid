package com.example.mongodbandroid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {

    @GET("/posts")
    Call<List<Post>> getAllPostData();
//    @DELETE("/posts/{postId}")
//    Call<POST> deletePostData(@Path("postId") String postId);
//    @POST("/")
//    Call<POST> createPostItem(@Body Post post);
}
