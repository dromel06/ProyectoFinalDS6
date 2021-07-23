package com.dromel.proyectfinaldes6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class IngresarLogin extends AppCompatActivity {

    private EditText et_usuario,et_password;
    private AdminSQLiteHelper admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_login);

        et_usuario = (EditText)findViewById(R.id.txt_usuario);
        et_password = (EditText)findViewById(R.id.txt_password);
        admin = new AdminSQLiteHelper(this, "db", null, 1);
    }

    public void registrarse (View view){
        Intent registrarse = new Intent(this,RegistrarLogin.class);
        startActivity(registrarse);
    }

    public void ingresar (View view){
        SQLiteDatabase Base = admin.getReadableDatabase();

        String nombre = et_usuario.getText().toString();
        String password = et_password.getText().toString();

        if (!nombre.isEmpty() && !password.isEmpty()){
            Cursor fila = Base.rawQuery("Select password from logines where usuario = '" + nombre +"'", null );

            if (fila.moveToFirst()){

                String et_password_validar = fila.getString(0);

                if (password.equals(et_password_validar)){
                    Intent ingresar = new Intent(this,MainActivity.class);
                    startActivity(ingresar);
                }
                else {

                    Toast.makeText(this, "Password erroneo", Toast.LENGTH_SHORT).show();
                }
               Base.close();
            }
            else {
               Toast.makeText(this, "usuario no existe", Toast.LENGTH_SHORT).show();
               Base.close();
            }
    }
        else {
        Toast.makeText(this, "Debe Llenar Todos Los campos", Toast.LENGTH_SHORT).show();
            //Base.close();
    }
    }
}