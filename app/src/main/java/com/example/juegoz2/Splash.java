package com.example.juegoz2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;



public class Splash extends AppCompatActivity {
    public MediaPlayer ring1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int Duracion_Splash = 2000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ring1 = MediaPlayer.create(Splash.this,R.raw.musica2);
                ring1.start();

                Intent intent = new Intent(Splash.this,Menu.class);
                startActivity(intent);
            }
        },Duracion_Splash);
}}