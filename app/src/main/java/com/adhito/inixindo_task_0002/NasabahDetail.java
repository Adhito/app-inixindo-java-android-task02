package com.adhito.inixindo_task_0002;
//package com.adhito.inixindo_project_0005;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

public class NasabahDetail extends AppCompatActivity {

    EditText edit_id, edit_name, edit_jabatan, edit_saldo_rekening, edit_no_rekening;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_detail_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Data Nasabah");

        edit_id = findViewById(R.id.edit_id);
        edit_name = findViewById(R.id.edit_nama);
        edit_jabatan = findViewById(R.id.edit_jabatan);
        edit_saldo_rekening = findViewById(R.id.edit_saldo_rekening);
        edit_no_rekening = findViewById(R.id.edit_no_rekening);

        // Menerima intent dari class LihatDataActivity
        Intent receiveIntent = getIntent();
        id = receiveIntent.getStringExtra(Konfigurasi.PGW_ID);
        edit_id.setText(id);

        // Mengambil data
        getJSON();
    }

    // Agar back-button bisa bekerja
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }

    private void getJSON() {

        // Bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(NasabahDetail.this,
                        "Mengambil data",
                        "Harap Menunggu",
                        false,
                        false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    Thread.sleep(10000);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_DETAIL, id);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                displayDetailData(message);

            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String nama = object.getString(Konfigurasi.TAG_JSON_NAMA);
            String jabatan = object.getString(Konfigurasi.TAG_JSON_JABATAN);
            String saldo_rekening = object.getString(Konfigurasi.TAG_JSON_SALDO_REKENING);
            String no_rekening = object.getString(Konfigurasi.TAG_JSON_NOMOR_REKENING);

            edit_name.setText(nama);
            edit_jabatan.setText(jabatan);
            edit_saldo_rekening.setText(saldo_rekening);
            edit_no_rekening.setText(no_rekening);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}