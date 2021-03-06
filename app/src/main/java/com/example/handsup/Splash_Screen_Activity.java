package com.example.handsup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;

public class Splash_Screen_Activity extends AppCompatActivity {

   int sonido_de_reproduccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen_);

        //Crear hilo para hacer que el Splash_Screen desaparezca despues de un tiempo

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splash_Screen_Activity.this,Inicio_Activity.class);
                startActivity(intent);
                finish();
                MediaPlayer mp = MediaPlayer.create(Splash_Screen_Activity.this, R.raw.musica);
                mp.start();
                mp.setLooping(true);


            }
        },4000);



    }
}
