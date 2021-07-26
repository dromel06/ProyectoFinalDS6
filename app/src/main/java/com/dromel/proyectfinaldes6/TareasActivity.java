package com.dromel.proyectfinaldes6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TareasActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner sp_estado;
    ListView lv_tareas;
    AdminSQLiteHelper admin;
    AdaptadorTarea adap;
    TextView compro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);

        admin = new AdminSQLiteHelper(this, "db", null, 1);
        sp_estado = (Spinner)findViewById(R.id.spinnerEstados);
        lv_tareas = (ListView)findViewById(R.id.lvTareas);
        sp_estado.setOnItemSelectedListener(this);
        compro = findViewById(R.id.txtMensaje);


        ListarTareas();

    }


    @Override
    protected void onResume() {
        super.onResume();
        comprobarfecha();
        ListarTareas();
    }


    public void ListarTareas(){
        SQLiteDatabase base = admin.getWritableDatabase();
        String query;

        if(sp_estado.getSelectedItem().toString().equals("Todo")){
            query = "SELECT * FROM tareas;";
        }else{
            query = "SELECT * FROM tareas WHERE estado = '" + sp_estado.getSelectedItem().toString() + "';";
        }
        Cursor fila = base.rawQuery(query, null);

        final String[][] lista = new String[fila.getCount()][4];
        if(fila.moveToFirst()){
            do{
                lista[fila.getPosition()][0] = fila.getString(0);
                lista[fila.getPosition()][1] = fila.getString(1);
                lista[fila.getPosition()][2] = fila.getString(2);
                lista[fila.getPosition()][3] = fila.getString(3);
            }while(fila.moveToNext());

        }
        base.close();
        adap = new AdaptadorTarea(this, lista, fila.getCount());
        lv_tareas.setAdapter(adap);

        lv_tareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent actualizar = new Intent(view.getContext(), actualizarTareaActivity.class);
                actualizar.putExtra("id", lista[i][0]);
                actualizar.putExtra("nombre", lista[i][1]);
                actualizar.putExtra("fecha", lista[i][2]);
                actualizar.putExtra("estado", lista[i][3]);
                startActivity(actualizar);
            }
        });






    }
    protected void comprobarfecha(){
        SQLiteDatabase base = admin.getWritableDatabase();
        Cursor fila = base.rawQuery("SELECT * FROM tareas ORDER BY idTareas", null);
        int i = 0;
        if(fila.moveToFirst()){
            do {
                String act = getfecha();
                String fecha = fila.getString( 2 );
                compro.setText(fecha);
                if(act.equals(fecha))
                    i= 1+i;
            }while(fila.moveToNext());
        }
        base.close();
        if(i!=0){
            compro.setText("Hay "+i+" tareas que necesitan ser entregadas hoy!");}
        else{
            compro.setText("No hay tareas pendientes para hoy!");}
    }

    private String getfecha(){
        return new SimpleDateFormat("dd/M/yyyy", Locale.getDefault()).format(new Date());
    }

    public void  nuevaTarea(View view){
        Intent intent = new Intent(this, TareaNuevaActivity.class);
        startActivity(intent);
    }
    

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ListarTareas();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        ListarTareas();
    }
}
