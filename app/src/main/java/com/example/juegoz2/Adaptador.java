package com.example.juegoz2;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.model.content.CircleShape;

import java.io.ObjectInputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Adaptador extends RecyclerView.Adapter<Adaptador.MyHolder>{

    //CONSTRUCTOR
    private Context context;
    private List<Usuario> usuarioList;
  //  private ObjectInputStream.GetField Picasso;

    public Adaptador(Context context, List<Usuario> usuarioList) {
        this.context = context;
        this.usuarioList = usuarioList;
    }



    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.jugadores,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {


        String Imagen = usuarioList.get(i).getImagen();
        String Nombres = usuarioList.get(i).getNombre();
        String Correo = usuarioList.get(i).getEmail();
        int Scores = usuarioList.get(i).getScore();
        //CONVERSION
        String Z = String.valueOf(Scores);

        holder.NombreJugador.setText(Nombres);
        holder.CorreoJugador.setText(Correo);
        holder.puntajeJugador.setText(Z);

        //imagen jugador
  /*
        try {
            Picasso.get().load(Imagen).into(holder.ImagenJugador);
        }catch (Exception e){

        }

*/

    }
    @Override
    public int getItemCount() {
        return usuarioList.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder{

       CircleImageView ImagenJugador;
       TextView NombreJugador, puntajeJugador, CorreoJugador;



        public MyHolder(@NonNull View itemView) {
            super(itemView);


            ImagenJugador = itemView.findViewById(R.id.ImagenJugador);
            NombreJugador = itemView.findViewById(R.id.NombreJugador);
            puntajeJugador = itemView.findViewById(R.id.puntajeJugador);
            CorreoJugador = itemView.findViewById(R.id.CorreoJugador);
        }
    }
}
