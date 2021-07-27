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

public class UpdatePostActivity extends AppCompatActivity {

    private Button updateButton;
    private TextView stopIdTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post);
        updateButton = findViewById(R.id.updateBtn);
        stopIdTextView = findViewById(R.id.stopIdTextView);

        Intent intent = getIntent();
        String stopObjId =  intent.getStringExtra("stopObjId");
        String stopId =  intent.getStringExtra("stopId");
        System.out.println("getStringExtra stopId" + stopId );
        // stopid 從上一頁點選的row取得
        stopIdTextView.setText(stopId);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String finalStopIdTextValue = stopIdTextView.getText().toString();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:3000")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                API request = retrofit.create(API.class);
                Post postData = new Post(stopObjId,finalStopIdTextValue);
                Call<Post> create = request.updatePostData(postData.getId(), postData);
                create.enqueue(new Callback<Post>() {

                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if (response.code() != 200) {
                            System.out.println("response code = " + response.code());
                        } else {
                            System.out.println("updateStop id successful " + response.body());
                            Toast.makeText(getApplication(),"update data successful", Toast.LENGTH_SHORT).show();
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

        });
    }
}