package com.dromel.proyectfinaldes6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ComentsActivity extends AppCompatActivity {

    private Button btn_save,btn_elimi, btnConsultar;
    private EditText txtMensaje;
    private TextView txtConsultar;
    private String idTarea;
    ListView lv_comentario;

    Cursor dbCursor = null;
    int _id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coments);

        btn_save = findViewById(R.id.btn_save);
//        btn_elimi = findViewById(R.id.btn_elimi);

        btn_save.setOnClickListener((new OnClickListener() {
            @Override
            public void onClick(View view) {
//                Boolean sw = false;
                setData();
            }
        }));
//        btn_elimi.setOnClickListener((new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DeleteRecord();
//                GetAllData();
//            }
//        }));
        lv_comentario = findViewById(R.id.lvComentario);


        txtMensaje = findViewById(R.id.txtMensaje);
//        txtConsultar = findViewById(R.id.txtConsultar);
        idTarea = getIntent().getStringExtra("id");
        GetAllData();

    }


    public Boolean setData()
    {
        Boolean sw = false;
        AdminSQLiteHelper appSQLiteOpenHelper = new AdminSQLiteHelper(this, "db", null, 1);
        if(this.txtMensaje.getText().toString() != ""){
            try {
                SQLiteDatabase db = appSQLiteOpenHelper.getWritableDatabase();
                ContentValues rows = new ContentValues();
                rows.put("comentario", this.txtMensaje.getText().toString());
                rows.put("idTareas", idTarea);
                db.insert("comentario", null, rows);
                Toast.makeText(this, "Comentario Agregado", Toast.LENGTH_LONG).show();
                db.close();

                sw= true;
                this.txtMensaje.setText("");
                this.GetAllData();

            }catch (Exception e){
                Toast.makeText(this, "Error al ingresar comentario", Toast.LENGTH_LONG).show();

            }
        }else {
            Toast.makeText(this, "Debe Ingresar un comentario ", Toast.LENGTH_LONG).show();

        }
        return sw;

    }

    public void GetAllData() {
        AdminSQLiteHelper appSQLiteOpenHepler= new AdminSQLiteHelper(this, "db", null, 1);
        SQLiteDatabase db = appSQLiteOpenHepler.getWritableDatabase();
        dbCursor = null;
        try {
            dbCursor = db.rawQuery("SELECT * FROM comentario WHERE idTareas="+ idTarea +"  ORDER BY comentario;", null);

            final String[][] lista = new String[dbCursor.getCount()][4];
            if(dbCursor.moveToFirst()){
                do{
                    lista[dbCursor.getPosition()][0] = dbCursor.getString(0);
                    lista[dbCursor.getPosition()][1] = dbCursor.getString(1);
                    lista[dbCursor.getPosition()][2] = dbCursor.getString(2);

                }while(dbCursor.moveToNext());

            }
            db.close();
            AdaptadorComentario adap = new AdaptadorComentario(this, lista, dbCursor.getCount());
            lv_comentario.setAdapter(adap);

//            if (dbCursor !=null){
//                dbCursor.moveToFirst();
//                String cod= dbCursor.getString(0);
//                String name= dbCursor.getString(1);
//                txtConsultar.append(""+cod+" - "+ name +"\n");
//                txtConsultar.setMovementMethod(new ScrollingMovementMethod());
//
//            }
        }catch (Exception e){

        }
    }

    private boolean DeleteRecord() {
        boolean sw = false;
        AdminSQLiteHelper appSQLiteOpenHepler = new AdminSQLiteHelper(this, "db", null, 1);
        if (_id != 0){
            try {
                SQLiteDatabase db= appSQLiteOpenHepler.getWritableDatabase();
                int result= db.delete("comentario", "_id = " + String.valueOf(_id), null);
                Toast.makeText(this,"Comentario Eliminado", Toast.LENGTH_LONG).show();
                db.close();
                if (result > 0){
                    sw = true;
                    this.txtMensaje.setText("");
                }
            }catch (Exception e){
                Toast.makeText(this,"Error al eliminar", Toast.LENGTH_LONG).show();

            }
        }
        return sw;
    }

    private void consultar(){
        try {
            AdminSQLiteHelper appSQLiteOpenHepler = new AdminSQLiteHelper(this, "db", null, 1);
            SQLiteDatabase db = appSQLiteOpenHepler.getWritableDatabase();

            dbCursor = null;
            dbCursor = db.rawQuery("SELECT * FROM comentario ORDER BY _id", null);

            txtMensaje.setText("");
            if(dbCursor != null){
                if (dbCursor.moveToFirst()){
                    do {
                        String cod= dbCursor.getString(0);
                        String name= dbCursor.getString(1);
                        txtConsultar.append(""+cod+" - "+ name +"\n");
                        txtConsultar.setMovementMethod(new ScrollingMovementMethod());
                    }while (dbCursor.moveToNext());
                }
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error al consultar", Toast.LENGTH_LONG).show();
        }
    }

    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btn_save:
                Boolean sw = false;
                sw = setData();
                break;

//            case R.id.btn_elimi:
//                if (_id > 0){
//                    DeleteRecord();
//                    GetAllData();
//                }
//                break;
        }
    }
}
