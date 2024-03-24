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

public class UserPreActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pre);
        EdgeToEdge.enable(this);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        ImageButton btnBack = findViewById(R.id.btnBack);
        Button dungPreButton = findViewById(R.id.dungPre);

        RadioButton chonMini = findViewById(R.id.dungMini);
        RadioButton chonIndi = findViewById(R.id.dungIndi);
        RadioButton chonStu = findViewById(R.id.dungStu);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        String packageType = sharedPreferences.getString("packageType", "");

        dungPreButton.setOnClickListener(v -> {
            if (!packageType.isEmpty()) {
                if (isChecked(chonMini) || isChecked(chonIndi) || isChecked(chonStu)) {
                    if (isChecked(chonMini) && packageType.equals("Mini")) {
                        Toast.makeText(getApplicationContext(), "Cảm ơn bạn đã dùng Gói Premium Mini", Toast.LENGTH_SHORT).show();
                    } else if (isChecked(chonIndi) && packageType.equals("Individual")) {
                        Toast.makeText(getApplicationContext(), "Cảm ơn bạn đã dùng Gói Premium Individual", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Bạn chưa Mua Gói Này!", Toast.LENGTH_SHORT).show();
                    }
                    Intent mainIntent = new Intent(this, MainActivity.class);
                    startActivity(mainIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn một gói Premium", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Bạn chưa Mua Gói Này!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isChecked(RadioButton radioButton) {
        return radioButton.isChecked();
    }
}