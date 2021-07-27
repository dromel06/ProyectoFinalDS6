package com.dromel.proyectfinaldes6;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AdaptadorComentario extends BaseAdapter {
    private static LayoutInflater inflater = null;

    Context contexto;
    String[][] datos;
    private Object TextView;
    int canti;

    AdaptadorComentario(Context contexto, String datos[][], int canti){
        this.contexto = contexto;
        this.datos = datos;
        inflater = (LayoutInflater)contexto.getSystemService((contexto.LAYOUT_INFLATER_SERVICE));
        this.canti = canti;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final View vista = inflater.inflate(R.layout.elemento_comentario, null);
        TextView comentario = (TextView)vista.findViewById(R.id.txtComentarioEC);
        Button eliminar = vista.findViewById(R.id.btnEliminarEC);


        comentario.setText(datos[i][1]);
        eliminar.setTag(i);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteRecord(datos[(Integer)view.getTag()][0], datos[(Integer)view.getTag()][2]);
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

    private boolean DeleteRecord(String i, String a) {
        boolean sw = false;

        AdminSQLiteHelper appSQLiteOpenHepler = new AdminSQLiteHelper(contexto, "db", null, 1);

        try {
            SQLiteDatabase db= appSQLiteOpenHepler.getWritableDatabase();
            int result = db.delete("comentario", "idComentario = " + String.valueOf(i), null);
            Toast.makeText(contexto,"Comentario Eliminado", Toast.LENGTH_SHORT).show();
            db.close();
            if (result > 0){
                sw = true;

            }
            Intent intent = new Intent(contexto, TareasActivity.class);
            contexto.startActivity(intent);
        }catch (Exception e){
            Toast.makeText(contexto,"Error al eliminar", Toast.LENGTH_LONG).show();

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return sw;

    }

}

