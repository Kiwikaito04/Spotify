package com.example.spotify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spotify.loginregister.AuthorizeHelper;
import com.example.spotify.loginregister.UserAdapter;

public class LoginActivity extends AppCompatActivity {
    AuthorizeHelper authorize;
    EditText Username, Password;
    UserAdapter User;
    Button btnRegister, btnLogin, btnForgotPassword;
    SharedPreferences SECTION;
    String KEY_SECTION = "user";
    String KEY_EMAIL = "user_email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoadFunction();
        LoadBtnAction();
    }

    private void LoadUserSection() {
        SECTION = getSharedPreferences(KEY_SECTION, Context.MODE_PRIVATE);
        String User_Email = SECTION.getString(String.format("%s",KEY_EMAIL), null);
        if(User_Email != null) {
            Cursor _user = authorize.isAvailableEmail(User_Email);
            if(_user.moveToNext()) {
                User = new UserAdapter(_user.getString(0),
                        _user.getString(1),
                        _user.getString(2));
                Username.setText(User.getUsername());
                Password.setText(User.getPassword());
                ClearSection();
            }
        }
    }

    private void ClearSection() {
        SharedPreferences.Editor editor = SECTION.edit();
        editor.clear();
        editor.apply();
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
                String emailOrUsername = Username.getText().toString(),
                        password = Password.getText().toString();
                if(TryLogin(emailOrUsername, password))
                {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void LoadFunction() {
        authorize = new AuthorizeHelper(this);

        btnRegister = findViewById(R.id.Login_btnSignUp);
        btnLogin = findViewById(R.id.Login_btnLogin);
        btnForgotPassword = findViewById(R.id.Login_btnForgotPassword);
        Username = findViewById(R.id.Login_EmailOrUsername);
        Password = findViewById(R.id.Login_Password);
    }
    private boolean TryLogin(String emailOrUsername, String password)
    {
        ClearError();
        int flag = 0;
        if(emailOrUsername.isEmpty())
        {
            Username.setError("This field is required!");
            flag++;
        }
        if(password.isEmpty())
        {
            Password.setError("Password is required!");
            flag++;
        }
        if(flag > 0)
            return false;
        if(!authorize.CheckAccount(emailOrUsername,password))
        {
            Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void ClearError() {
        Username.setError(null);
        Password.setError(null);
    }
    //Hàm sẽ thực hiện sau khi được finish()
    @Override
    public void onResume() {
        super.onResume();
        LoadUserSection();
    }
}