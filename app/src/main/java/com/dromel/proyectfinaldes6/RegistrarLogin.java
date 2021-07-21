package com.dromel.proyectfinaldes6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrarLogin extends AppCompatActivity {

    private EditText et_usuario,et_password, et_password_dos;
    private AdminSQLiteHelper admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_login);

        et_usuario = (EditText)findViewById(R.id.txt_new_usuario);
        et_password = (EditText)findViewById(R.id.txt_contrasena);
        et_password_dos = (EditText)findViewById(R.id.txt_contrasena_dos);

        admin = new AdminSQLiteHelper(this, "db", null, 1);
    }

    public void Agregarlogin(View view){
        SQLiteDatabase Base = admin.getReadableDatabase();

        String nombre = et_usuario.getText().toString();
        String password = et_password.getText().toString();
        String password_dos = et_password_dos.getText().toString();

        if (!nombre.isEmpty() && !password.isEmpty()){
            if (password.equals(password_dos)) {
                ContentValues login_new = new ContentValues();
                login_new.put("usuario", nombre);
                login_new.put("password", password);

                Base.insert("login", null, login_new);

                Base.close();

                et_usuario.setText("");
                et_password.setText("");
                et_password_dos.setText("");
                Toast.makeText(this, "Se registro login correctamente", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
            else
            {
                Toast.makeText(this, "los password no son iguales", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debe Llenar Todos Los campos", Toast.LENGTH_SHORT).show();
        }
    }

}