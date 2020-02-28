package com.example.handsup;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity2 extends AppCompatActivity {

    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score2);

        tv1 = findViewById(R.id.txtScore);

        String texto = getIntent().getStringExtra("palabras");

        tv1.setText(texto);
    }
}
