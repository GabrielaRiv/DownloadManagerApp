package com.example.gabriela.downloadmanagerapp.activities;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gabriela.downloadmanagerapp.MainActivity;
import com.example.gabriela.downloadmanagerapp.R;
import com.example.gabriela.downloadmanagerapp.notification.NotiDownload;
import com.example.gabriela.downloadmanagerapp.services.DownloadService;
import com.example.gabriela.downloadmanagerapp.utils.NotiMgr;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DownloadActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_ACCESS_STORAGE = 111;
    private static final String TITLE_NOTIFICATION = "New Download";
    private static final String CHANNEL_ID = "10";
    @BindView(R.id.etxtUrl) EditText url;
    @BindView(R.id.pgbDownload) ProgressBar progress;

    private BroadcastReceiver mMessageReceiver;
    private String action;
    private Boolean permission;
    private String DownloadLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        ButterKnife.bind(this);
        this.permission = false;

        // Our handler for received Intents. This will be called whenever an Intent
        // with an action named "custom-event-name" is broadCasted.
        /**
         * Crea un "manejador" (broadcastReceiver) para recibir los intent enviados por el service. Al recibir el intent
         * se obtiene la "accion que identifica al mensaje o identificador de mensaje" y
         * se programa para cada uno lo que debe hacer
         */
        this.mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                action = intent.getStringExtra("action");
                switch (action){
                    case DownloadService.ACTION_CONNECTED_DOWNLOAD:
                        Toast.makeText(DownloadActivity.this, intent.getStringExtra("message"), Toast.LENGTH_SHORT).show();
                        break;

                    case DownloadService.ACTION_START_DOWNLOAD:
                        Toast.makeText(DownloadActivity.this, intent.getStringExtra("message"), Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.VISIBLE);
                        break;

                    case DownloadService.ACTION_END_DOWNLOAD:
                        Toast.makeText(DownloadActivity.this, intent.getStringExtra("message"), Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.INVISIBLE);
                        break;

                    case DownloadService.ACTION_ERROR_DOWNLOAD:
                        Toast.makeText(DownloadActivity.this, intent.getStringExtra("message"), Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        };

        //flecha hacia atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Metodo para mostras flecha hacia atras
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    /**
     * Se registra el "manejador" en el LocalBroadcastManager con la acción que debera "atender" o
     * filtrar de los intent recibidos (la acción es el mismo usado en el service ACTION_DOWNLOAD),
     * los intent que tengan especificado esa acción serán procesados en el onReceive() del "manejador"
     * tal cual se haya programado
     */
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(DownloadService.ACTION_DOWNLOAD));
    }

    /**
     * Quita el "manejador" registrado para que luego vuelva a ser registrado en onResume()
     * eso permite que los mensajes enviados desde el service se obtengan actualizados
     * al momento que se muestra la activity (asi lo entiendo :D)
     */
    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    /**
     * Inicia el servicio al hacer clic en el botón.
     */
    @OnClick(R.id.btnDownload)
    public void onClick(){
        if(permission){
            //Url de prueba: https://www.pixelstalk.net/wp-content/uploads/2016/08/1920x1080-Nebula-Space-Wallpaper.jpg
            DownloadService.startActionDownload(this, url.getText().toString());

            DownloadLink = url.getText().toString();

            // Create an explicit intent for an Activity in your app
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            NotiDownload.showNoti(this, getString(R.string.new_noti), DownloadLink, intent);

        }else {
            getStoragePermission();
        }
    }


    /**
     * Verifica si se ha consedido permiso de almacenamieto, sino, solicita al usuario los
     * permisos correspondientes (Android >= 6)
     */
    private void getStoragePermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
            permission = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_ACCESS_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    permission = true;
                }
            }
        }
    }
}
