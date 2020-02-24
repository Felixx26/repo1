package com.example.handsup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handsup.interfaz.categoriasApi;
import com.example.handsup.model.Categorias;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Poner el icono en el accion Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //layout para indicar el contenedor para los botones
        linearLayout = findViewById(R.id.contenedor);
        getCategorias();


    }

    private void getCategorias() {
        //Obtener las categorías utilizando la api creada
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-rest-palabras.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        categoriasApi categoriasApi = retrofit.create(categoriasApi.class);
        //utilizando la interfaz de categoria api para obtener un molde de lista de las categorias
        Call<List<Categorias>> call = categoriasApi.getCategorias();
        //llamando a las categorias de la api
        call.enqueue(new Callback<List<Categorias>>() {
            @Override
            public void onResponse(Call<List<Categorias>> call, Response<List<Categorias>> response) {
                //Si la respuesta no es sastisfactoria
                if (!response.isSuccessful()) {
                    return;
                }

                Toast.makeText(getApplicationContext(),"Categorias", Toast.LENGTH_SHORT).show();
                //agregando a un List la respuestra traida por la api
                List<Categorias> categoriasList = response.body();
                //estableciendo los layoutParams para agregar a los botones y as[i esten relacionados con el layout actual
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                for (final Categorias categoria: categoriasList){
                    Button button = new Button(getApplicationContext());
                    button.setText(categoria.getNombre());
                    button.setBackgroundResource(R.drawable.botones_menu);
                    button.setTextSize(50);
                    button.setTextColor(getApplication().getResources().getColor(R.color.blanco));
                    button.setHeight(170);
                    button.setLayoutParams(lp);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(),"Categoria: " + categoria.getNombre(), Toast.LENGTH_SHORT).show();
                            //button.setImageResource(R.drawable.icono);
                            //button.setPadding(10,20,10,20);
                            //button.setWidth(1);
                            Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                            startActivity(intent);
                        }
                    });
                    linearLayout.addView(button);
                }

            }

            @Override
            public void onFailure(Call<List<Categorias>> call, Throwable t) {
                //mJsonTextView.setText(t.getMessage());
            }
        });
    }
}
