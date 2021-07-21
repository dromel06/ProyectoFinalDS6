package com.dromel.proyectfinaldes6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class IngresarLogin extends AppCompatActivity {

    private EditText et_usuario,et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_login);

        et_usuario = (EditText)findViewById(R.id.txt_usuario);
        et_password = (EditText)findViewById(R.id.txt_password);
    }

    public void registrarse (View view){
        Intent registrarse = new Intent(this,RegistrarLogin.class);
        startActivity(registrarse);
    }

    public void ingresar (View view){

        String nombre = et_usuario.getText().toString();
        String password = et_password.getText().toString();

        if (!nombre.isEmpty() && !password.isEmpty()){
        Intent ingresar = new Intent(this,MainActivity.class);
        startActivity(ingresar);
    }
        else {
        Toast.makeText(this, "Debe Llenar Todos Los campos", Toast.LENGTH_SHORT).show();
    }
    }
}