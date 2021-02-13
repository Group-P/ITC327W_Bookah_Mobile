package com.example.bookah.Activities.SplashScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.bookah.Activities.Login;
import com.example.bookah.R;

public class VerificationSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verification_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent verifySplashIntent = new Intent(VerificationSplash.this, Login.class);
                startActivity(verifySplashIntent);
                finish();
            }
        },10000);
    }
}