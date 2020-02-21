package com.example.handsup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                Intent intent = new Intent(Inicio_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
