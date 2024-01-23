package com.example.juegoz2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.PointerIcon;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class EscenarioJuego extends AppCompatActivity {
    public MediaPlayer ring1;
    String UIDS, NOMBRES,ZOMBIES;
    TextView contador, TvNombre, TvTiempo, AnchoTv, AltoTv;
    ImageView Tvzombie;
    int Anchopantalla;
    int Altopantalla;
    int contador1 = 0;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference JUGADORES;

    Random aleatorio;
    Boolean GameOver = false;
    Dialog miDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escenario_juego);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        JUGADORES = firebaseDatabase.getReference("JUGADORES");


        contador = findViewById(R.id.contador);
        TvNombre = findViewById(R.id.TVnombre);
        TvTiempo = findViewById(R.id.TVtiempo);
        Tvzombie = findViewById(R.id.TvZombie);
        AnchoTv = findViewById(R.id.AnchoTv);
        AltoTv = findViewById(R.id.AltoTv);

        miDialog = new Dialog(EscenarioJuego.this);

        Bundle intent = getIntent().getExtras();

        UIDS = intent.getString("UID");
        NOMBRES = intent.getString("NOMBRE");
        ZOMBIES = intent.getString("ZOMBIE");

        TvNombre.setText(NOMBRES);
        contador.setText(ZOMBIES);


        //PANTALLA
        pantalla();
        CuentaAtras();

        Tvzombie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(!GameOver) {
                ring1 = MediaPlayer.create(EscenarioJuego.this,R.raw.musica3);
                ring1.start();
                contador1++;
                contador.setText(String.valueOf(contador1));

                Tvzombie.setImageResource(R.drawable.zombieaplastado);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Tvzombie.setImageResource(R.drawable.zombie);
                        movimiento();

                    }
                }, 200);
            }
            }
        });

    }

    //TAMAÑO DE PANTALLA

    private void pantalla(){
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();

        display.getSize(point);

        Anchopantalla = point.x;
        Altopantalla = point.y;

        String ANCHOS = String.valueOf(Anchopantalla);
        String ALTOS = String.valueOf(Altopantalla);

        AnchoTv.setText(ANCHOS);
        AltoTv.setText(ALTOS);

        aleatorio =new Random();

    }

    private void movimiento(){
        int min = 0;
        int max = Anchopantalla - Tvzombie.getWidth();
        int maxy = Altopantalla - Tvzombie.getHeight();

        int randomX =aleatorio.nextInt((max-min)+1)+min;
        int randomy = aleatorio.nextInt((maxy-min)+1)+min;

        Tvzombie.setX(randomX);
        Tvzombie.setY(randomy);

    }

    private void CuentaAtras(){

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                long segundosRestantes = millisUntilFinished/1000;
                TvTiempo.setText(segundosRestantes+"s");
            }

            public void onFinish() {
                TvTiempo.setText("0S");
                GameOver = true;
                MensajeGameOver();
                GuardarResultados("Score",contador1);
            }
        }.start();

    }

    private void MensajeGameOver() {
     String ubicacion = "fuentes/Deathridge.ttf";
        Typeface typeface = Typeface.createFromAsset(EscenarioJuego.this.getAssets(),ubicacion);

        TextView SeacaboTXT, HasmatadoTXT, NumeroTXT;
        Button JUGARDE, IRMENU,PUNTAJES;

        miDialog.setContentView(R.layout.gameover);

        SeacaboTXT = miDialog.findViewById(R.id.SeacaboTXT);
        HasmatadoTXT = miDialog.findViewById(R.id.HasmatadoTXT);
        NumeroTXT = miDialog.findViewById(R.id.NumeroTXT);

        JUGARDE = miDialog.findViewById(R.id.JUGARDE);
        IRMENU = miDialog.findViewById(R.id.IRMENU);
        PUNTAJES = miDialog.findViewById(R.id.PUNTAJES);

      String zombies= String.valueOf(contador1);
      NumeroTXT.setText(zombies);

      JUGARDE.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              contador1=0;
              miDialog.dismiss();
              contador.setText("0");
              GameOver = false;
              CuentaAtras();
              movimiento();
          }
      });


      IRMENU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EscenarioJuego.this,Menu.class));
            }
        });



      PUNTAJES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EscenarioJuego.this, "Menu", Toast.LENGTH_SHORT).show();
            }
        });

      miDialog.show();
    }

    private void GuardarResultados(String key,int zombies){

        HashMap<String,Object>hashMap= new HashMap<>();
        hashMap.put(key,zombies);

        JUGADORES.child(user.getUid()).updateChildren(hashMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(EscenarioJuego.this, "Puntaje ha sido actualizado", Toast.LENGTH_SHORT).show();
            }
        });

    }
}