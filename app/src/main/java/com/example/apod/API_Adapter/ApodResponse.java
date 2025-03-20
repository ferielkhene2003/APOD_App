package com.example.apod.API_Adapter;
import com.google.gson.annotations.SerializedName;

public class ApodResponse {
    @SerializedName("title")
    private String title;

    @SerializedName("explanation")
    private String explanation;

    @SerializedName("url")
    private String imageUrl;

    public String getTitle() {
        return title;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
