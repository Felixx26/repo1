package com.example.handsup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;


public class ScoreActivity extends AppCompatActivity {

    Button btnVolver;
    TextView tvAcertadas;
    TextView tvFallidas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        btnVolver = (Button) findViewById(R.id.btnVolver);
        tvAcertadas = (TextView) findViewById(R.id.tvAcertadas);
        tvFallidas = (TextView) findViewById(R.id.tvFallidas);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Mostrar Score

        String acertadas = getIntent().getStringExtra("Acertadas");
        String fallidas = getIntent().getStringExtra("Fallidas");

        final String[] palabrasAcertadas = getIntent().getStringArrayExtra("palabrasAcertadas");
        final String[] palabrasFallidas = getIntent().getStringArrayExtra("palabrasFallidas");

        tvAcertadas.setText(acertadas);
        tvAcertadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = "";
                if (palabrasAcertadas[0]!=null) {
                    for (int i = 0; i < palabrasAcertadas.length; i++) {
                        if (palabrasAcertadas[i] == null) {
                            break;
                        }
                        texto += palabrasAcertadas[i] + "\n";
                    }
                    mostrarPalabras(texto, true);
                }
            }
        });
        tvFallidas.setText(fallidas);
        tvFallidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = "";
                if (palabrasFallidas[0]!=null) {
                    for (int i = 0; i < palabrasFallidas.length; i++) {
                        if (palabrasFallidas[i] != null) {
                            texto += palabrasFallidas[i]+ "\n";
                        }
                    }
                    mostrarPalabras(texto, false);
                }
            }
        });
    }

    private void mostrarPalabras(String palabras, Boolean res){
        Intent intent=new Intent(ScoreActivity.this,ScoreActivity2.class);
        intent.putExtra("palabras", palabras);
        intent.putExtra("respuesta", res);
        startActivity(intent);
    }
}
