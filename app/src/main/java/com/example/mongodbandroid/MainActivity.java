package com.example.mongodbandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public RecyclerView postListRecyclerView;
    private PostAdapter postViewAdapter;
    private Button createPostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "load data", Toast.LENGTH_SHORT).show();
        createPostButton = findViewById(R.id.createPostBtn);
        callAPI();
        createPostButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent intent = new Intent();
                                                    intent.setClassName("com.example.mongodbandroid","com.example.mongodbandroid.CreatePostActivity");
                                                    startActivity(intent);
                                                }
                                            }
        );
    }

    private void callAPI() {
        /** 虛擬機API接口為10:0.2.2
         * 後台PORT是3000
        **/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API request = retrofit.create(API.class);
        // GET method  getAllPostData
        Call<List<Post>> create = request.getAllPostData();
        create.enqueue(new Callback<List<Post>>() {

            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.code() != 200){
                    System.out.println("response code = " + response.code());
                }else{
                    displayPostList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                System.out.println("response failed = " + t.toString());
            }
        });
    }
    public void displayPostList(List<Post> postList) {
        postListRecyclerView = findViewById(R.id.postListRecyclerView);
        postListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postListRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        postViewAdapter = new PostAdapter(this, postList);
        postListRecyclerView.setAdapter(postViewAdapter);
    }
}