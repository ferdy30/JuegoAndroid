package com.example.juegoz2;

import android.widget.ImageView;

public class Usuario {
    String Nombre,Edad, Email, Fecha,Imagen,Password, Uid;
    int Score;



    public Usuario() {

    }

    public Usuario(String edad, String email,String nombre, String fecha, String password, String uid, int score,String imagen) {
        Edad = edad;
        Nombre = nombre;
        Email = email;
        Imagen = imagen;
        Fecha = fecha;
        Password = password;
        Uid = uid;
        Score = score;
    }

    public String getEdad() {
        return Edad;
    }

    public void setEdad(String edad) {
        Edad = edad;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }


    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }
}
