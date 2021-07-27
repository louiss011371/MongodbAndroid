package com.example.mongodbandroid;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Post> postList;
    private MainActivity mainActivity;

    PostAdapter(MainActivity mainActivity, List<Post> postList) {
        this.mainActivity = mainActivity;
        this.postList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post obj = postList.get(position);
        holder.stopIdTextView.setText(obj.getStopID());

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {
                                                  String itemId = obj.getId();
                                                  Intent intent = new Intent();
                                                  intent.setClassName("com.example.mongodbandroid","com.example.mongodbandroid.UpdatePostActivity");
                                                  intent.putExtra("stopObjId", itemId);
                                                  intent.putExtra("stopId", obj.getStopID());

                                                  System.out.println("putStringExtra stopObjId" +itemId );
                                                  System.out.println("putStringExtra stopId" + obj.getStopID() );
                                                  view.getContext().startActivity(intent);
                                              }
                                          }
        );
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemId = obj.getId();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:3000")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                API request = retrofit.create(API.class);
                Call<Post> deletePostDataCall = request.deletePostData(itemId);
                deletePostDataCall.enqueue(new Callback<Post>() {

                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if(response.code() != 200){
                            System.out.println("response code = " + response.code());
                        }else{
                            System.out.println("delete stop id successful");
                            // item被刪除後刷新recyclerview
                            mainActivity.recreate();
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

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView stopIdTextView;
        private Button editBtn;
        private Button deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stopIdTextView = (TextView) itemView.findViewById(R.id.stopId);
            editBtn = (Button) itemView.findViewById(R.id.editButton);
            deleteBtn = (Button) itemView.findViewById(R.id.deleteButton);
        }
    }
}
