package com.dromel.proyectfinaldes6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IngresarLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_login);
    }

    public void registrarse (View view){
        Intent registrarse = new Intent(this,RegistrarLogin.class);
        startActivity(registrarse);
    }

    public void ingresar (View view){
        Intent ingresar = new Intent(this,MainActivity.class);
        startActivity(ingresar);
    }

}