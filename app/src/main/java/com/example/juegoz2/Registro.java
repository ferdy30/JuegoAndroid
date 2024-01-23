package com.example.juegoz2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    FirebaseAuth auth;
    EditText correoEt,passEt,nombreEt;
    TextView fechaTxt;
    Button Registrar;
    FirebaseFirestore mFirestore;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        //conexion
        correoEt = findViewById(R.id.correoEt);
        passEt =findViewById(R.id.passEt);
        nombreEt = findViewById(R.id.nombreEt);
        fechaTxt = findViewById(R.id.fechaTxt);
        Registrar = findViewById(R.id.Registrar);



        Date date = new Date();
        SimpleDateFormat fecha = new SimpleDateFormat("d  'de' MMMM 'del' yyyy" );
        String stringFecha = fecha.format(date);
        fechaTxt.setText(stringFecha);

/*validacion*/
       Registrar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String email = correoEt.getText().toString();
               String password =passEt.getText().toString();

               if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                  correoEt.setError("Correo no valido");
                  correoEt.setFocusable(true);
               }else if (password.length()<6){
                   passEt.setError("Contraseña debe ser mayor a 6");
                   correoEt.setFocusable(true);
               }else {
                   RegistrarJugador(email,password);
               }
           }
       });


    }

    private void RegistrarJugador(String email, String password) {
        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()){

                            FirebaseUser user = auth.getCurrentUser();
                            int contador =0;
                            String uidString = user.getUid();

                            String correoString = correoEt.getText().toString();
                            String passString = passEt.getText().toString();
                            String nombreString = nombreEt.getText().toString();
                            String fechaString = fechaTxt.getText().toString();

                            HashMap<String,Object> DatosJUGADOR = new HashMap<>();

                            DatosJUGADOR.put("Uid",uidString);
                            DatosJUGADOR.put("Email",correoString);
                            DatosJUGADOR.put("Password",passString);
                            DatosJUGADOR.put("Nombres",nombreString);
                            DatosJUGADOR.put("Fecha",fechaString);
                            DatosJUGADOR.put("Score",contador);



                            database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("JUGADORES");
                            reference.child(uidString).setValue(DatosJUGADOR);

                            Toast.makeText(Registro.this, "USUARIO REGISTRADO EXITOSAMENTE", Toast.LENGTH_SHORT).show();
                            Toast.makeText(Registro.this, "Bienvenido", Toast.LENGTH_SHORT).show();

                            finish();
                        }else {

                            Toast.makeText(Registro.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Registro.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}