package com.dromel.proyectfinaldes6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.dromel.proyectfinaldes6.Clases.DatePickerFragment;

public class TareaNuevaActivity extends AppCompatActivity {


    private EditText et_nombre, et_fecha;
    private AdminSQLiteHelper admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea_nueva);

        et_nombre = findViewById(R.id.txtNombreATN);
        et_fecha = findViewById(R.id.txtFechaATN);
        Button btn_guardar = findViewById(R.id.btnGuardarATN);
        admin = new AdminSQLiteHelper(this, "db", null, 1);
        et_fecha.setOnClickListener(this::onClick);
        btn_guardar.setOnClickListener(this::onClick);
    }


    public void AgregarTarea(){
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
            onBackPressed();
        }else{
            Toast.makeText(this, "Debe Llenar Todos Los campos", Toast.LENGTH_SHORT).show();
        }

    }



    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtFechaATN:
                showDatePickerDialog();
                break;
            case R.id.btnGuardarATN:
                AgregarTarea();
                break;
        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + "/" + (month+1) + "/" + year;
                et_fecha.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");


    }

    public FragmentActivity getActivity() {
        return this;
    }
}