package com.example.handsup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Inicio_Activity extends AppCompatActivity {

    //Declaración
    Button btnJugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_);

        btnJugar = (Button)findViewById(R.id.button);






        //Mentodo del botón Jugar
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer sb = MediaPlayer.create(Inicio_Activity.this, R.raw.sonidoboton);
                sb.start();
                Intent intent = new Intent(Inicio_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
