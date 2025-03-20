package com.example.apod;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button btnDate, btnValidate;
    private Calendar calendar;
    private String selectedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnDate = findViewById(R.id.BtnDate);
        btnValidate = findViewById(R.id.BtnValidate);
        calendar = Calendar.getInstance();
        updateDateButton(); // Set default date

        btnDate.setOnClickListener(v -> showDatePickerDialog());
        btnValidate.setOnClickListener(v -> fetchApodData());
    }

    private void fetchApodData() {
        // Get today's date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String todayDate = sdf.format(Calendar.getInstance().getTime());

        // Compare selected date with today's date
        if (selectedDate.compareTo(todayDate) > 0) {
            Toast.makeText(this, "You cannot select a future date!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Proceed to DetailsActivity if the date is valid
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("SELECTED_DATE", selectedDate);
        startActivity(intent);
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    updateDateButton();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void updateDateButton() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        selectedDate = sdf.format(calendar.getTime());
        btnDate.setText(selectedDate);
    }

}