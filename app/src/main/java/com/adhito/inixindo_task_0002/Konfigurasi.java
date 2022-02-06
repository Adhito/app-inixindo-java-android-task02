package com.adhito.inixindo_task_0002;

public class Konfigurasi {
    // URL dimana Web API berada
    public static final String URL_GET_ALL = "http://192.168.5.88/api_nasabah/tampil_nasabah_all.php";
    public static final String URL_GET_DETAIL = "http://192.168.5.88/api_nasabah/tampil_nasabah.php?id=";
//    public static final String URL_ADD = "http://192.168.5.88/api_nasabah/tambahPgw.php";
//    public static final String URL_UPDATE = "http://192.168.5.88/api_nasabah/updatePgw.php";
//    public static final String URL_DELETE = "http://192.168.5.88/api_nasabah/hapusPgw.php";

    // Key and Value JSON yang muncul di browser
    public static final String KEY_PGW_ID = "id";
    public static final String KEY_PGW_NAMA = "name";
    public static final String KEY_PGW_JABATAN = "desg";
    public static final String KEY_PGW_GAJI = "salary";

    // Flag JSON
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_JSON_ID = "id";
    public static final String TAG_JSON_NAMA = "name";
    public static final String TAG_JSON_JABATAN = "desg";
    public static final String TAG_JSON_SALDO_REKENING = "saldo_rekening";
    public static final String TAG_JSON_NOMOR_REKENING = "no_rekening";

    // Variabel ID pegawai
    public static final String PGW_ID = "emp_id";
}
