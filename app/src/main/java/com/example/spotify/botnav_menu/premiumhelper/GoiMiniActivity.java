package com.example.spotify.botnav_menu.premiumhelper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.spotify.MainActivity;
import com.example.spotify.R;

public class GoiMiniActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goi_mini);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });


        Button btnPayment = findViewById(R.id.payMini);
        btnPayment.setOnClickListener(v -> {
            Intent intent = new Intent(this, PaymentActivity.class);
            startActivity(intent);
        });
    }
}