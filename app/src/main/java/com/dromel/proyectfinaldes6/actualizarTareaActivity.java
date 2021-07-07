package com.dromel.proyectfinaldes6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.dromel.proyectfinaldes6.Clases.DatePickerFragment;

public class actualizarTareaActivity extends AppCompatActivity implements View.OnClickListener {

    String id, nombre, fecha, estado, newNombre, newFecha, newEstado;
    EditText txt_nombre, txt_fecha;
    RadioButton rb_pendiente, rb_proceso, rb_completado;
    AdminSQLiteHelper admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_tarea);
//      LLamara datos del activity anterior
        id = getIntent().getExtras().getString("id");
        nombre = getIntent().getExtras().getString("nombre");
        fecha = getIntent().getExtras().getString("fecha");
        estado = getIntent().getExtras().getString("estado");
//      conectar con el xml
        txt_nombre = (EditText)findViewById(R.id.txtNombreAAT);
        txt_fecha = (EditText)findViewById(R.id.txtfechaAAT);
//       conectar RadioButtons
        rb_pendiente = (RadioButton)findViewById(R.id.rbPendiente);
        rb_proceso = (RadioButton)findViewById(R.id.rbProceso);
        rb_completado = (RadioButton)findViewById(R.id.rbCompletado);
//      activar variable admin
        admin = new AdminSQLiteHelper(this, "db", null, 1);
        txt_fecha.setOnClickListener(this);
        MostrarInfo();

    }

    public void MostrarInfo(){
        txt_nombre.setText(nombre);
        txt_fecha.setText(fecha);
        if(estado.equals("Pendiente")){
            rb_pendiente.setChecked(true);
        }else if(estado.equals("Completado")){
            rb_completado.setChecked(true);
        }
        else{
            rb_proceso.setChecked(true);
        }

    }

    public void Actualizar(View view){
        SQLiteDatabase base = admin.getWritableDatabase();
        newNombre = txt_nombre.getText().toString();
        newFecha = txt_fecha.getText().toString();

        if(rb_pendiente.isChecked()){
            newEstado = "Pendiente";
        }
        else if(rb_proceso.isChecked()){
            newEstado = "En Proceso";
        }
        else{
            newEstado = "Completado";
        }
        if(!newNombre.isEmpty() && !newEstado.isEmpty() && !newFecha.isEmpty()){
            if (newNombre != nombre || newFecha != fecha || newEstado != estado){
                ContentValues tarea = new ContentValues();
                tarea.put("nombre", newNombre);
                tarea.put("fecha", newFecha);
                tarea.put("estado", newEstado);
                int cantidad = base.update("tareas", tarea, "idTareas = '" + id +"'", null);
                base.close();
                if(cantidad > 0){
                    Toast.makeText(this, "Tarea Actualizada", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
                else{
                    Toast.makeText(this, "Error al modificar la tarea", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Debe Cambiar al menos 1 valor", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Todo los campos deben estar llenos", Toast.LENGTH_SHORT).show();
        }

    }

    public void Eliminar(View view){
        SQLiteDatabase base = admin.getWritableDatabase();

        if(!id.isEmpty()){
            int cantidad = base.delete("tareas", "idTareas = " + id + "", null);
            base.close();
            if(cantidad > 0){
                Toast.makeText(this, "Tarea Eliminada", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
            else{
                Toast.makeText(this, "La tarea no existe", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Error con esta tarea", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtfechaAAT:
                showDatePickerDialog();
                break;
        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                txt_fecha.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");


    }

    public FragmentActivity getActivity() {
        return this;
    }


}
