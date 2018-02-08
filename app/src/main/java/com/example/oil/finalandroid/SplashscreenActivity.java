package com.example.oil.finalandroid;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashscreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Handler splashscreen = new Handler(getMainLooper());
        splashscreen.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashscreen=new Intent(SplashscreenActivity.this,LoginActivity.class);
                startActivity(splashscreen);
                finish();
            }
        },2000);
    }
}
