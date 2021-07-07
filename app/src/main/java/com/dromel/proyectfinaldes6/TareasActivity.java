package com.dromel.proyectfinaldes6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

public class TareasActivity extends AppCompatActivity {

    Spinner sp_estado;
    ListView lv_tareas;
    AdminSQLiteHelper admin;
    AdaptadorTarea adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);

        admin = new AdminSQLiteHelper(this, "db", null, 1);
        sp_estado = (Spinner)findViewById(R.id.spinnerEstados);
        lv_tareas = (ListView)findViewById(R.id.lvTareas);
        ListarTareas();

    }

    public void Listar(View view){
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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent actualizar = new Intent(view.getContext(), actualizarTareaActivity.class);
                actualizar.putExtra("id", lista[position][0]);
                actualizar.putExtra("nombre", lista[position][1]);
                actualizar.putExtra("precio", lista[position][2]);
                actualizar.putExtra("estado", lista[position][3]);
                startActivity(actualizar);
            }
        });




    }

    public void  nuevaTarea(View view){
        Intent intent = new Intent(this, nuevaTareaActivity.class);
        startActivity(intent);
    }
}
