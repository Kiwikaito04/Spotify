package com.example.spotify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spotify.loginregister.AuthorizeHelper;

public class LoginActivity extends AppCompatActivity {
    AuthorizeHelper authorize;
    EditText EmailOrUsername, Password;

    Button btnRegister, btnLogin, btnForgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoadFunction();
        LoadBtnAction();
    }

    private void LoadBtnAction() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailOrUsername = EmailOrUsername.getText().toString(),
                        password = Password.getText().toString();
                if(CheckLogin(emailOrUsername, password))
                {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void LoadFunction() {
        authorize = new AuthorizeHelper(this);
        btnRegister = findViewById(R.id.Login_btnSignUp);
        btnLogin = findViewById(R.id.Login_btnLogin);
        btnForgotPassword = findViewById(R.id.Login_btnForgotPassword);
        EmailOrUsername = findViewById(R.id.Login_EmailOrUsername);
        Password = findViewById(R.id.Login_Password);
    }
    private boolean CheckLogin(String emailOrUsername, String password)
    {
        ClearError();
        if(emailOrUsername.isEmpty())
        {
            EmailOrUsername.setError("This field is required!");
            return false;
        }
        if(password.isEmpty())
        {
            Password.setError("Password is required!");
            return false;
        }
        if(!authorize.CheckAccount(emailOrUsername,password))
        {
            Toast.makeText(LoginActivity.this, "Account does not match!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void ClearError() {
        EmailOrUsername.setError(null);
        Password.setError(null);
    }
}