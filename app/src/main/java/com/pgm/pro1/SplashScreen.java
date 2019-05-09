package com.pgm.pro1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.pgm.pro1.Database.GestionBD;
import com.pgm.pro1.Tools.Connection;
import com.pgm.pro1.Tools.MyAsyncTask;
import io.fabric.sdk.android.Fabric;

public class SplashScreen extends AppCompatActivity {
    private Connection c;
    ProgressBar myProgress;
    TextView myTextView;
    Button myButton;
    Context context;
    private String msj="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.slpash_screen);


        c = new Connection(getApplicationContext());
        myProgress = findViewById(R.id.splash_screen_progresbar);
        myTextView = findViewById(R.id.splash_screen_text);
        myButton = findViewById(R.id.splash_screen_button);


        PedirPermisos();



       new ingresarBd().execute();

        MyAsyncTask asyncTask= new MyAsyncTask(myButton,myProgress,myTextView);
        asyncTask.execute();

        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashScreen.this, ActivityLogin.class);

               startActivity(intent);
            }
        });

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 9999:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
            case 1111:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
                return;
        }
    }


    public  void insertar() {
        if (!c.search("http://apidiv.guadalajara.gob.mx:8085/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
            if (!c.search("http://apidiv.guadalajara.gob.mx:8085/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("null")) {
                eliminaRegistros("C_Direccion");
                c.insetarRegistros("http://apidiv.guadalajara.gob.mx:8085/serverSQL/getC_Direccion.php", "C_Direccion");
            }
            if (!c.search("http://apidiv.guadalajara.gob.mx:8085/serverSQL/getc_insepctor.php").trim().equalsIgnoreCase("null")) {
                eliminaRegistros("C_inspector");
                c.insetarRegistros("http://apidiv.guadalajara.gob.mx:8085/serverSQL/getc_insepctor.php", "C_inspector");
            }



        }
    }

    public void eliminaRegistros(String tabla) {
        GestionBD gestion = new GestionBD(getApplicationContext(), "Infraccion",null,1);
        SQLiteDatabase db = gestion.getReadableDatabase();
        db.beginTransaction();
        try {

            db.delete(tabla, "1", null);

            db.setTransactionSuccessful();

        } catch (SQLiteException e) {
            Log.e("SQLiteException ", e.getMessage());
        }
        finally {
            db.endTransaction();
            db.close();
        }
    }


    public void PedirPermisos(){

        // Validando permisos de ESCRITURA

        if (ContextCompat.checkSelfPermission(SplashScreen.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

            //Si no se tienen permisos se piden
            ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 9999);


        }else{
            Toast.makeText(SplashScreen.this,ContextCompat.checkSelfPermission(SplashScreen.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)+" Permisos de Escritura", Toast.LENGTH_SHORT).show();

        }

        // Validando permisos de RED
        if (ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.INTERNET}, 1111);


        }else{
            Toast.makeText(SplashScreen.this,ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.INTERNET)+" Persmiso internet", Toast.LENGTH_SHORT).show();
        }

        if(ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.CAMERA}, 2222);

        }else{
            Toast.makeText(SplashScreen.this,ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.CAMERA)+" Persmiso CAMERA", Toast.LENGTH_SHORT).show();
        }

    }

     class ingresarBd extends AsyncTask<String,String,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            insertar();
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }




}

