package com.dromel.proyectfinaldes6;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import org.w3c.dom.Text;

public class AdaptadorTarea extends BaseAdapter {
    private static LayoutInflater inflater = null;

    Context contexto;
    String[][] datos;
    int canti;

    AdaptadorTarea(Context contexto, String datos[][], int canti){
        this.contexto = contexto;
        this.datos = datos;
        inflater = (LayoutInflater)contexto.getSystemService((contexto.LAYOUT_INFLATER_SERVICE));
        this.canti = canti;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final View vista = inflater.inflate(R.layout.elemento_lista, null);
        TextView nombre = vista.findViewById(R.id.txtNombre);
        TextView fecha = vista.findViewById(R.id.txtFecha);
        TextView estado = vista.findViewById(R.id.txtEstado);
        ImageView color = vista.findViewById(R.id.llColor);
        Button Coment = vista.findViewById(R.id.btnComentario);
        nombre.setText(datos[i][1]);
        fecha.setText("Fecha Limite: " + datos[i][2]);
        estado.setText(datos[i][3]);

        if(datos[i][3].equals("Pendiente")){
            color.setBackgroundResource(R.drawable.esquinas_redondas_pendiente);

        }else if(datos[i][3].equals("En Proceso")){
            color.setBackgroundResource(R.drawable.esquinas_redondas_proceso);
        }else{
            color.setBackgroundResource(R.drawable.esquinas_redondas_terminado);
        }
        Coment.setTag(i);
        Coment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contexto, ComentsActivity.class);
                intent.putExtra("id", datos[(Integer)view.getTag()][0]);
                contexto.startActivity(intent);
            }
        });
        color.setTag(i);
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actualizar = new Intent(view.getContext(), actualizarTareaActivity.class);
                actualizar.putExtra("id", datos[(Integer)view.getTag()][0]);
                actualizar.putExtra("nombre", datos[(Integer)view.getTag()][1]);
                actualizar.putExtra("fecha", datos[(Integer)view.getTag()][2]);
                actualizar.putExtra("estado", datos[(Integer)view.getTag()][3]);
                contexto.startActivity(actualizar);
            }
        });

        return vista;
    }

    @Override
    public int getCount() {
        return canti;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }




}
