package com.example.mongodbandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreatePostActivity extends AppCompatActivity {

    private Button createButton;
    private TextView stopIdTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        createButton = findViewById(R.id.createBtn);
        stopIdTextView = findViewById(R.id.stopIdTextView);
        createButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                               String stopIdText =  stopIdTextView.getText().toString();

                                                Retrofit retrofit = new Retrofit.Builder()
                                                        .baseUrl("http://10.0.2.2:3000")
                                                        .addConverterFactory(GsonConverterFactory.create())
                                                        .build();
                                                API request = retrofit.create(API.class);
                                                // mongodb item id 是自動創建的 留空可
                                                Post postData = new Post("",stopIdText);
                                                Call<Post> create = request.createPostItem(postData);
                                                create.enqueue(new Callback<Post>() {

                                                    @Override
                                                    public void onResponse(Call<Post> call, Response<Post> response) {
                                                        if(response.code() != 200){
                                                            System.out.println("response code = " + response.code());
                                                        }else{
                                                            Intent intent = new Intent();
                                                            System.out.println("createStop id successful " + response.body());
                                                            Toast.makeText(getApplication(),"create data successful", Toast.LENGTH_SHORT).show();
                                                            intent.setClassName("com.example.mongodbandroid","com.example.mongodbandroid.MainActivity");
                                                            startActivity(intent);
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Post> call, Throwable t) {
                                                        System.out.println("response failed = " + t.toString());
                                                    }
                                                });
                                            }
                                        }
        );
    }
}