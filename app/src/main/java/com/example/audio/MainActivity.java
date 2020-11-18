package com.example.audio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaRecorder grabacion;
    private String archivoSalida=null;
    private ImageButton btnGrabar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGrabar = (ImageButton)findViewById(R.id.btnGrabar);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }
    }
    public void Recorder(View view){
        if(grabacion==null) {
            archivoSalida = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Grabacion.mp3";
            grabacion = new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            grabacion.setOutputFile(archivoSalida);
            try{
                grabacion.prepare();
                grabacion.start();
            }catch (IOException e){

            }
            btnGrabar.setBackgroundResource(R.drawable.rec);
            Toast.makeText(this,"Grabando....",Toast.LENGTH_SHORT).show();
        }else if(grabacion!=null){
            grabacion.stop();
            grabacion.release();
            grabacion=null;
            Toast.makeText(this,"Grabacion finalizada",Toast.LENGTH_SHORT).show();
            btnGrabar.setBackgroundResource(R.drawable.stop_rec);
        }
    }
    public void reproducir(View view){
        MediaPlayer mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.setDataSource(archivoSalida);
            mediaPlayer.prepare();

        }catch (IOException e){

        }
        mediaPlayer.start();
        Toast.makeText(this,"Reproduciendo...",Toast.LENGTH_SHORT).show();
    }

}