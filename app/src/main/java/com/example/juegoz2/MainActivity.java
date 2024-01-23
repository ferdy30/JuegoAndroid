package com.example.juegoz2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public MediaPlayer ring;
    TextView Bienvenido;
    Button BNTLOGIN,BNTREGISTRO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bienvenido = findViewById(R.id.Bienvenido);
        String ubicacion = "fuentes/Deathridge.ttf";
        Typeface tf = Typeface.createFromAsset(MainActivity.this.getAssets(), ubicacion);
        Bienvenido.setTypeface(tf);

       /*ring = MediaPlayer.create(MainActivity.this,R.raw.musica1);
       ring.start();

*/

        BNTLOGIN = findViewById(R.id.BNTLOGIN);
        BNTREGISTRO = findViewById(R.id.BTNREGISTRO);

        BNTLOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this,login.class);
                startActivity(intent);
            }
        });
        BNTREGISTRO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this,Registro.class);
            startActivity(intent);
            }
        });
    }
}