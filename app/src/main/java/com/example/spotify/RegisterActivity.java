package com.example.spotify;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Interpolator;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.spotify.loginregister.AuthorizeHelper;
import com.example.spotify.loginregister.Utils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {
    AuthorizeHelper authorize;
    ImageView imgAvatar;
    RelativeLayout relativeLayout;
    EditText Username, Email, Password, ConfirmPass;
    Button btnSignUp, btnLogin;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        LoadFunction();
        LoadBtnAction();
    }
    void LoadFunction() {
        authorize = new AuthorizeHelper(this);

        //
        relativeLayout = findViewById(R.id.relativeLayout);
        imgAvatar = findViewById(R.id.imageViewAvatar);
        imgAvatar.setImageBitmap(Utils.loadBitmapFromAssets(this, "select_avatar_placeholder.png"));
        //
        toolbar = findViewById(R.id.materialToolbarSignUp);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //
        Username = findViewById(R.id.SignUp_UserName);
        Email = findViewById(R.id.SignUp_Email);
        Password = findViewById(R.id.SignUp_Password);
        ConfirmPass = findViewById(R.id.SignUp_ConfirmPassword);
        btnSignUp = findViewById(R.id.SignUp_btnRegister);
        btnLogin = findViewById(R.id.SignUp_btnLogin);
    }
    void LoadBtnAction() {
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayBottomSheet();
            }
        });
        //Quay về LoginActivity
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        //Xử lý khi nhấp đăng ký
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DiaLogProcess.showDialog(RegisterActivity.this,"Loading...");
                //
                String email = Email.getText().toString(),
                        username = Username.getText().toString(),
                        password = Password.getText().toString();
                if (checkRegister(email, username, password)) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Sign up Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    boolean checkRegister(String email, String username, String password) {
        if (username.length() < 6) {
            Username.setError("Invalid username");
            return false;
        }

        if (email.length() < 6) {
            Email.setError("Invalid Email.");
            return false;
        }

        if (password.length() < 3) {
            Password.setError("Invalid password.");
            return false;
        }

        if (!password.equals(ConfirmPass.getText().toString())) {
            ConfirmPass.setError("Password not match.");
            return false;
        }
        if (authorize.CheckEmailOrUserName(email, username)) {
            Toast.makeText(RegisterActivity.this, "User already exists", Toast.LENGTH_LONG).show();
            return false;
        }
        Boolean user = authorize.InsertData(email, username, password);
        if (!user) {
            Toast.makeText(RegisterActivity.this, "Sign up failed", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(RegisterActivity.this, "Sign up Successfully", Toast.LENGTH_LONG).show();
        return true;
    }
    private void displayBottomSheet() {

        // creating a variable for our bottom sheet dialog.
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);

        // passing a layout file for our bottom sheet dialog.
        View layout = LayoutInflater.from(this).inflate(R.layout.select_photo_bottom_sheet_layout, relativeLayout, false);

        // passing our layout file to our bottom sheet dialog.
        bottomSheetTeachersDialog.setContentView(layout);

        // below line is to set our bottom sheet dialog as cancelable.
        bottomSheetTeachersDialog.setCancelable(false);

        // below line is to set our bottom sheet cancelable.
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        bottomSheetTeachersDialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog d = (BottomSheetDialog) dialogInterface;

            FrameLayout bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        // below line is to display our bottom sheet dialog.
        bottomSheetTeachersDialog.show();

        // initializing our text views and image views.
        Button btGallery = layout.findViewById(R.id.buttonSelectGallery);
        btGallery.setOnClickListener(view -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            imagePickerActivityResult.launch(photoPickerIntent);
        });
        Button btCapture = layout.findViewById(R.id.buttonSelectCapture);
        btCapture.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageCaptureActivityResult.launch(intent);
        });
        // creating a variable for document reference.

    }

    ActivityResultLauncher<Intent> imagePickerActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null) {
                        Intent data = result.getData();
                        Uri imageUri = data.getData();
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            imgAvatar.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
    );

    ActivityResultLauncher<Intent> imageCaptureActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null) {
                        Intent intent = result.getData();
                        Bitmap photo = (Bitmap) intent.getExtras().get("data");
                        imgAvatar.setImageBitmap(photo);
                    }
                }
            }
    );

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    private void ClearError() {
        Username.setError(null);
        Email.setError(null);
        Password.setError(null);
        ConfirmPass.setError(null);
    }
}