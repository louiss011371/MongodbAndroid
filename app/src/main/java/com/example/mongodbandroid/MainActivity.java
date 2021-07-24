package com.example.mongodbandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public RecyclerView postListRecyclerView;
    private PostAdapter postViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callAPI();
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
                   // System.out.println("response success = " + response.body().get(0).getStopID());
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
        postListRecyclerView = findViewById(R.id.postListReyclerView);
        postListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postListRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        postViewAdapter = new PostAdapter(postList);
        postListRecyclerView.setAdapter(postViewAdapter);
    }
}