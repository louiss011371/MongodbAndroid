package com.example.mongodbandroid;

import com.google.gson.annotations.SerializedName;

public class Post {
    /** mongodb object _id -> id
     *  mongodb object STOP_ID -> stopId
     */
    @SerializedName("_id")
    private String id;
    @SerializedName("STOP_ID")
    private String stopId;

    public Post(String _id, String stopIdText) {
        this.id = _id;
        this.stopId = stopIdText;
    }

    public String getId() {
        return id;
    }
    public String getStopID() {
        return stopId;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

}
