package com.example.juegoz2;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.checkerframework.common.subtyping.qual.Bottom;

public class login extends AppCompatActivity {
    EditText correoLogin,passLogin;
    Button botonLogin;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correoLogin = findViewById(R.id.correoLogin);
        passLogin = findViewById(R.id.passLogin);
        botonLogin = findViewById(R.id.botonLogin);

     botonLogin.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             String email = correoLogin.getText().toString();
             String pass = passLogin.getText().toString();
             auth = FirebaseAuth.getInstance();

             if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                 correoLogin.setError("Correo no valido");
                 correoLogin.setFocusable(true);
             }else if (pass.length()<6){
                 passLogin.setError("Contraseña debe ser mayor a 6");
                 passLogin.setFocusable(true);
             }else {
                 logeoDeJugador(email,pass);
             }
         }
     });
    }

    private void logeoDeJugador(String email, String pass) {
        auth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            startActivity(new Intent(login.this, Menu.class));
                            assert user != null;
                            Toast.makeText(login.this, "Estamos listos!"+user.getEmail(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

}