package com.example.bookah.Activities.SplashScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.bookah.Activities.Login;
import com.example.bookah.R;

public class OpeningSplash extends AppCompatActivity {

    //Variables
    View first,second,third,fourth,fifth,sixth;
    ImageView logo;
    //Animations
    Animation topAnimation,fadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_opening_splash);

        //Hooks
        first = findViewById(R.id.first_line);
        second = findViewById(R.id.second_line);
        third = findViewById(R.id.third_line);
        fourth = findViewById(R.id.fourth_line);
        fifth = findViewById(R.id.fifth_line);
        sixth = findViewById(R.id.sixth_line);
        logo = findViewById(R.id.Logo);

        //AnimationCalls
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        fadeIn = AnimationUtils.loadAnimation(this,R.anim.fade_in);

        //Setting animations to elements of splash
        first.setAnimation(topAnimation);
        second.setAnimation(topAnimation);
        third.setAnimation(topAnimation);
        fourth.setAnimation(topAnimation);
        fifth.setAnimation(topAnimation);
        sixth.setAnimation(topAnimation);
        logo.setAnimation(fadeIn);

        //Splash screen code to call new activity after set time
        new Handler().postDelayed(() -> {
            Intent splashIntent = new Intent(OpeningSplash.this, Login.class);
            startActivity(splashIntent);
            finish();
        },3000);

    }
}