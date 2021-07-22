package com.dromel.proyectfinaldes6;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import org.w3c.dom.Text;

public class AdaptadorTarea extends BaseAdapter {
    private static LayoutInflater inflater = null;

    Context contexto;
    String[][] datos;
    private Object textView;
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
        TextView nombre = (TextView)vista.findViewById(R.id.txtNombre);
        TextView fecha = (TextView)vista.findViewById(R.id.txtFecha);
        TextView estado = (TextView)vista.findViewById(R.id.txtEstado);
        ImageView color = (ImageView)vista.findViewById(R.id.llColor);

        nombre.setText(datos[i][1]);
        fecha.setText("Fecha Limite: " + datos[i][2]);
        estado.setText(datos[i][3]);
        if(datos[i][3].equals("Pendiente")){
            color.setBackgroundColor(Color.RED);
        }else if(datos[i][3].equals("En Proceso")){
            color.setBackgroundColor(Color.BLUE);
        }else{
            color.setBackgroundColor(Color.GREEN);
        }



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
