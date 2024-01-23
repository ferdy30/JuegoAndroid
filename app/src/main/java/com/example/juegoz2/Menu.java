package com.example.juegoz2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Menu extends AppCompatActivity {


    TextView Nombre, Correo, Uid,fantasmas,MiPuntuacion;
    FirebaseAuth auth;
    FirebaseUser user;
    Button CerrarSesion,JugarBTN,PuntuacionesBTN,AcercaDeBTN;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference JUGADORES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        firebaseDatabase = FirebaseDatabase.getInstance();
        JUGADORES = firebaseDatabase.getReference("JUGADORES");




        String ubicacion = "fuentes/Deathridge.ttf";
        Typeface tf = Typeface.createFromAsset(Menu.this.getAssets(), ubicacion);
//INICIALIZACION BOTONES
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        CerrarSesion = findViewById(R.id.CerrarSesion);
        JugarBTN = findViewById(R.id.JugarBTN);
        PuntuacionesBTN = findViewById(R.id.PuntuacionesBTN);
        AcercaDeBTN = findViewById(R.id.AcercaDeBTN);


//INICIALIZACION PARTE ARRIBA
        Nombre = findViewById(R.id.Nombre);
        Correo = findViewById(R.id.Correo);
        Uid = findViewById(R.id.Uid);
        fantasmas = findViewById(R.id.fantasmas);
        MiPuntuacion = findViewById(R.id.MiPuntuacion);

//CAMBIO DE LETRA
        MiPuntuacion.setTypeface(tf);
        Nombre.setTypeface(tf);
        fantasmas.setTypeface(tf);
        MiPuntuacion.setTypeface(tf);

        //BOTONES
       JugarBTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {



               Toast.makeText(Menu.this, "Jugar", Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(Menu.this,EscenarioJuego.class);

               String Uids = Uid.getText().toString();
               String nombress = Nombre.getText().toString();
               String fantasmas1 = fantasmas.getText().toString();

               intent.putExtra("UID",Uids);
               intent.putExtra("NOMBRE",nombress);
               intent.putExtra("ZOMBIE",fantasmas1);

               startActivity(intent);
               Toast.makeText(Menu.this, "Enviando parametros", Toast.LENGTH_SHORT).show();

           }
       });
       PuntuacionesBTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(Menu.this,Puntajes.class);
               startActivity(intent);
               Toast.makeText(Menu.this, "Mi puntuacion ", Toast.LENGTH_SHORT).show();
           }
       });
       AcercaDeBTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Toast.makeText(Menu.this, "Acerca de", Toast.LENGTH_SHORT).show();
           }
       });
        CerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CerrarSesion();
            }
        });
    }

    @Override
    protected void onStart() {
        Usuariologeado();
        super.onStart();
    }

    private void Usuariologeado(){
        if (user != null){
            Consulta();
            Toast.makeText(this, "Jugador en linea", Toast.LENGTH_SHORT).show();
        }else{
            startActivity(new Intent(Menu.this, MainActivity.class));
            finish();
        }
    }

    private void CerrarSesion(){
        auth.signOut();
        startActivity(new Intent(Menu.this,MainActivity.class));
        Toast.makeText(this, "Cerrado sesión exitosamente", Toast.LENGTH_SHORT).show();

    }

    private void Consulta(){
        Query query = JUGADORES.orderByChild("Email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    String zombiestring = ""+ds.child("Score").getValue();
                    String uidstring = ""+ds.child("Uid").getValue();
                    String emailstring = ""+ds.child("Email").getValue();
                    String nombrestring = ""+ds.child("Nombres").getValue();

                    fantasmas.setText(zombiestring);
                    Uid.setText(uidstring);
                    Correo.setText(emailstring);
                    Nombre.setText(nombrestring);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}