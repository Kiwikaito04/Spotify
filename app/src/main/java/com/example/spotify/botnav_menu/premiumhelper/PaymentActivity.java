package com.example.spotify.botnav_menu.premiumhelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.spotify.MainActivity;
import com.example.spotify.R;

public class PaymentActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        ImageButton btnBack = findViewById(R.id.btnBackToMain);
        Button payButton = findViewById(R.id.payButton);
        RadioButton chonMini = findViewById(R.id.chonMini);
        RadioButton chonIndi = findViewById(R.id.chonIndi);
        RadioButton chonStu = findViewById(R.id.chonStu);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        payButton.setOnClickListener(v -> {
            if (isChecked(chonMini) || isChecked(chonIndi) || isChecked(chonStu)) {
                if (isChecked(chonMini)) {
                    Toast.makeText(getApplicationContext(), "Đăng ký Gói Premium Mini Thành Công", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("packageType", "Mini");
                    editor.apply();
                    Intent intent = new Intent(this, UserPreActivity.class);
                    startActivity(intent);
                } else if (isChecked(chonIndi)) {
                    Toast.makeText(getApplicationContext(), "Đăng ký Gói Premium Individual Thành Công", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("packageType", "Individual");
                    editor.apply();
                    Intent intent = new Intent(this, UserPreActivity.class);
                    startActivity(intent);
                } else if (isChecked(chonStu)) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập đầy đủ thông tin để Đăng Ký gói này!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Vui lòng chọn một gói Premium", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isChecked(RadioButton radioButton) {
        return radioButton.isChecked();
    }
}