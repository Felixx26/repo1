package com.example.handsup;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.handsup.interfaz.palabrasApi;
import com.example.handsup.model.Palabras;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlayActivity extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;
    TextView tv1;
    int whip = 0;
    List<Palabras> palabrasList;
    Map<String, Boolean> score = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        String categoria = getIntent().getExtras().getString("categoria");
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        tv1 = findViewById(R.id.txtPalabra);
        if (sensor == null) {
            finish();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-rest-palabras.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        palabrasApi palabrasApi = retrofit.create(palabrasApi.class);
        //utilizando la interfaz de palabras api para obtener un molde de lista de las palabras
        Call<List<Palabras>> call = palabrasApi.getPalabras(categoria);

        //Llamada a la api
        call.enqueue(new Callback<List<Palabras>>() {
            @Override
            public void onResponse(Call<List<Palabras>> call, Response<List<Palabras>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                palabrasList = response.body();
                activateSensor();
            }

            @Override
            public void onFailure(Call<List<Palabras>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void activateSensor() {
        if (palabrasList != null) {

            //Randomizar el listado de palabras
            Collections.shuffle(palabrasList, new Random());
            tv1.setText(palabrasList.get(0).getPalabra());
            //Evento de sensor
            sensorEventListener = new SensorEventListener() {
                int i = 0;
                int aux = 0;

                @Override
                public void onSensorChanged(SensorEvent event) {
                    float z = event.values[2];
                    //Evento que se desata al mover el celular hacia abajo
                    if (z <= -5 && aux == 0) {
                        aux++;
                        score.put(palabrasList.get(i).getPalabra(), true);
                        correctSound();
                    }
                    //Evento que se desata al mover el celular hacia arriba
                    if (z >= 5 && aux == 0) {
                        aux++;
                        score.put(palabrasList.get(i).getPalabra(), false);
                        incorrectSound();
                    }
                    //Reinicio de variables
                    if (aux == 1 && z < 5 && z > -5) {
                        i++;
                        if (i < palabrasList.size()) {
                            tv1.setText(palabrasList.get(i).getPalabra());
                            aux = 0;
                        } else {
                            menu();
                            aux = -1;
                        }
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };
            start();
        }
    }

    private void correctSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.correct);
        mediaPlayer.start();
    }

    private void incorrectSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.incorrect);
        mediaPlayer.start();
    }

    private void start() {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stop() {
        sensorManager.unregisterListener(sensorEventListener);
    }

    private void menu() {
        String text = "";
        for (Map.Entry<String, Boolean> score1 : score.entrySet()) {
            text += score1.getKey() + " " + score1.getValue();
        }
        tv1.setText(text);
    }

    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        start();
        super.onResume();
    }
}

