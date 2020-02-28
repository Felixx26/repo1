package com.example.handsup;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity2 extends AppCompatActivity {

    TextView tv1;
    TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score2);

        tv1 = findViewById(R.id.txtScore);
        tv2 = findViewById(R.id.txtRes);

        String texto = getIntent().getStringExtra("palabras");

        tv1.setText(texto);
        Boolean respuesta = getIntent().getBooleanExtra("respuesta", true);
        if (respuesta){

            tv2.setText("Correctas");

        }else {
            tv2.setText("Incorrectas");
        }
    }
}
