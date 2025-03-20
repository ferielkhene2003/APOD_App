package com.example.apod;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.apod.API_Adapter.ApodApiService;
import com.example.apod.API_Adapter.ApodResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity {

    private TextView txtDate, txtTitle, txtDescription;
    private ImageView imgApod;
    private static final String API_KEY = "DEMO_KEY"; // Replace with your API key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);

        txtDate = findViewById(R.id.TxtDate);
        txtTitle = findViewById(R.id.TxtTitle);
        txtDescription = findViewById(R.id.TxtDescription);
        imgApod = findViewById(R.id.ImgApod);

        String selectedDate = getIntent().getStringExtra("SELECTED_DATE");

        if (selectedDate != null) {
            txtDate.setText("Selected Date: " + selectedDate);
            fetchApodData(selectedDate);
        }
    }

    private void fetchApodData(String date) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApodApiService apiService = retrofit.create(ApodApiService.class);
        Call<ApodResponse> call = apiService.getApod(API_KEY, date);

        call.enqueue(new Callback<ApodResponse>() {
            @Override
            public void onResponse(Call<ApodResponse> call, Response<ApodResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApodResponse apod = response.body();
                    txtTitle.setText(apod.getTitle());
                    txtDescription.setText(apod.getExplanation());

                    // Load image using Glide
                    Glide.with(DetailsActivity.this)
                            .load(apod.getImageUrl())
                            .into(imgApod);
                } else {
                    Toast.makeText(DetailsActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApodResponse> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}