package com.adhito.inixindo_task_0002;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class NasabahAll extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView list_view;
    private String JSON_STRING;
    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasabah_all);


        //
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Penanganan list view
        list_view = findViewById(R.id.list_view);
        list_view.setOnItemClickListener(this);

        // Method untuk ambil data JSON
        getJSON();

    }

    private void getJSON() {

        // Bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(NasabahAll.this,
                        "Mengambil data",
                        "Harap Menunggu",
                        false,
                        false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_ALL);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Log.d("DATA_JSON",JSON_STRING);
                Toast.makeText(NasabahAll.this, JSON_STRING, Toast.LENGTH_LONG).show();

                // Menampilkan data dalam list view
                displayAllData();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayAllData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            Log.d("DATA_JSON : ", JSON_STRING);

            for (int i = 0; i < result.length(); i++){
                JSONObject object = result.getJSONObject(i);
                String id = object.getString(Konfigurasi.TAG_JSON_ID);
                String name = object.getString(Konfigurasi.TAG_JSON_NAMA);

                HashMap<String, String> pegawai = new HashMap<>();
                pegawai.put(Konfigurasi.TAG_JSON_ID, id);
                pegawai.put(Konfigurasi.TAG_JSON_NAMA, name);

                // Ubah format JSON menjadi array list
                list.add(pegawai);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        // Adapter untuk meletakan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                getApplicationContext(),
                list,
                R.layout.activity_list_item,
                new String[]{Konfigurasi.TAG_JSON_ID, Konfigurasi.TAG_JSON_NAMA},
                new int[]{R.id.txt_id, R.id.txt_name}
        );
        list_view.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // ketika salah satu list dipilih
        // detail: ID, Name, Desg, Salary
        Intent myIntent = new Intent(NasabahAll.this,
                NasabahDetail.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String pgwId = map.get(Konfigurasi.TAG_JSON_ID).toString();
        myIntent.putExtra(Konfigurasi.PGW_ID, pgwId);
        startActivity(myIntent);
    }

    // Agar back-button bisa bekerja
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }

    private void Timer() {
        ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mProgressBar.setProgress(i);
        mCountDownTimer=new CountDownTimer(10000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                i++;
                mProgressBar.setProgress((int)i*100/(10000/1000));

            }

            @Override
            public void onFinish() {
                //Do what you want
                i++;
                mProgressBar.setProgress(100);
            }
        };
        mCountDownTimer.start();
    }
}