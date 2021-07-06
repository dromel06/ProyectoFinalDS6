package com.dromel.proyectfinaldes6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class nuevaTareaActivity extends AppCompatActivity {

    private EditText et_nombre, et_fecha;
    private AdminSQLiteHelper admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_tarea);

        et_nombre = (EditText)findViewById(R.id.txtNombreANT);
        et_fecha = (EditText)findViewById(R.id.txtfechaANT);

        admin = new AdminSQLiteHelper(this, "db", null, 1);

    }

    public void AgregarTarea(View view){
        SQLiteDatabase Base = admin.getReadableDatabase();

        String nombre = et_nombre.getText().toString();
        String fecha = et_fecha.getText().toString();
        String estado = "Pendiente";

        if (!nombre.isEmpty() && !fecha.isEmpty()){
            ContentValues tarea = new ContentValues();
            tarea.put("nombre", nombre);
            tarea.put("fecha", fecha);
            tarea.put("estado", estado);

            Base.insert("tareas", null, tarea);

            Base.close();

            et_nombre.setText("");
            et_fecha.setText("");
            Toast.makeText(this, "Nueva Tarea Agregada", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Debe Llenar Todos Los campos", Toast.LENGTH_SHORT).show();
        }

    }


}
