package com.pgm.pro1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.pgm.pro1.Database.GestionBD;

import java.util.ArrayList;

public class ActivityLogin extends AppCompatActivity {
    Button ingresar;
    Spinner Sdependencia;
    Spinner Sinspector;
    private ArrayAdapter adapterDirecciones,adapterInspectores;
    private ArrayList nombreDirecciones,nombreInspectores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ingresar= findViewById(R.id.login_ingresar_button);
        Sinspector = findViewById(R.id.spinner_login_inspector);
        Sdependencia = findViewById(R.id.spinner_login_dependencia);

        nombreDirecciones = DirectionNames();
        adapterDirecciones = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,nombreDirecciones);
        Sdependencia.setAdapter(adapterDirecciones);

        nombreInspectores = InspectorNames();
        adapterInspectores = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,nombreInspectores);
        Sinspector.setAdapter(adapterInspectores);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this ,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public ArrayList<String> DirectionNames(){

        GestionBD gestion = new GestionBD(getApplicationContext(),"Infraccion",null, 1);
        SQLiteDatabase db = gestion.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM c_direccion", null);
        ArrayList<String> nombresDirecciones= new ArrayList<>();
        String nombre;

       while (cursor.moveToNext()){
           nombre = cursor.getString(1);
           nombresDirecciones.add(nombre);

       }

        return nombresDirecciones;
    }


    public ArrayList<String> InspectorNames(){

        GestionBD gestion = new GestionBD(getApplicationContext(),"Infraccion",null, 1);
        SQLiteDatabase db = gestion.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM c_inspector", null);
        ArrayList<String> nombresInspectore= new ArrayList<>();
        String nombre;

       while (cursor.moveToNext()){
           nombre = cursor.getString(1);
           nombresInspectore.add(nombre);

       }

        return nombresInspectore;
    }
}
