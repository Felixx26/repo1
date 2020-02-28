package com.example.handsup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    Button btnVolver;
    TextView tvAcertadas;
    TextView tvFallidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);



        btnVolver = (Button)findViewById(R.id.btnVolver);
        tvAcertadas =(TextView)findViewById(R.id.tvAcertadas);
        tvFallidas = (TextView)findViewById(R.id.tvFallidas);

        //Volver a Pantalla de Inicio

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer sb = MediaPlayer.create(ScoreActivity.this, R.raw.sonidoboton);
                sb.start();
                Intent intent=new Intent(ScoreActivity.this,Inicio_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        //Mostrar Score

        String Acertadas = getIntent().getStringExtra("Acertadas");
        String Fallidas = getIntent().getStringExtra("Fallidas");
        tvAcertadas.setText(Acertadas);
        tvFallidas.setText(Fallidas);





    }
}
