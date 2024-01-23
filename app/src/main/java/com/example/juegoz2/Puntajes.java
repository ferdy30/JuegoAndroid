package com.example.juegoz2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Puntajes extends AppCompatActivity {


    LinearLayoutManager mLayoutManager;
    RecyclerView RecicleViewUsuarios;
    Adaptador adaptador;
    List<Usuario> usuarioList;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntajes);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Puntajes");

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mLayoutManager = new LinearLayoutManager(this);
        firebaseAuth = FirebaseAuth.getInstance();
        RecicleViewUsuarios = findViewById(R.id.RecicleViewUsuarios);

        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        RecicleViewUsuarios.setHasFixedSize(true);
        RecicleViewUsuarios.setLayoutManager(mLayoutManager);
        usuarioList = new ArrayList<>();

        ObtenerTodosLosUsuarios();
    }

    private void ObtenerTodosLosUsuarios() {

        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("JUGADORES");
        ref.orderByChild("Score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuarioList.clear();
           for (DataSnapshot ds : dataSnapshot.getChildren()){

                    Usuario usuario = ds.getValue(Usuario.class);
                    if (!usuario.getUid().equals(fuser.getUid())){
                        usuarioList.add(usuario);






                    adaptador = new Adaptador(Puntajes.this,usuarioList);
                    RecicleViewUsuarios.setAdapter(adaptador);

                }
            }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}