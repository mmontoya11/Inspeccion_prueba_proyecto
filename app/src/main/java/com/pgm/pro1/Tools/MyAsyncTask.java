package com.pgm.pro1.Tools;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyAsyncTask extends AsyncTask<Void, Integer,Void> {

    ProgressBar Progresbar;
    TextView textview;
    Button button;


    public MyAsyncTask(Button button, ProgressBar progressBar, TextView textView){
        this.button= button;
        this.Progresbar= progressBar;
        this.textview = textView;

    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... voids) {
        //aqui va la descarga



        for(int i= 1; i<=100; i++){
            publishProgress(i);
            SystemClock.sleep(100);
        }


        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Progresbar.setProgress(values[0]);
        textview.setText(values[0]+"%");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        button.setVisibility(View.VISIBLE);
        textview.setText("Listo!");
    }

}
