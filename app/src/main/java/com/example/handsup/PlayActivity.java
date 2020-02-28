package com.example.handsup;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.handsup.interfaz.palabrasApi;
import com.example.handsup.model.Palabras;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
    CountDownTimer countDownTimer;
    TextView tv1;
    TextView tvTime;
    List<Palabras> palabrasList;
    Map<String, Boolean> score = new HashMap<>();
    int aux = 0;
    int correctas = 0;
    int incorrectas = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        String categoria = getIntent().getExtras().getString("categoria");
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        tv1 = findViewById(R.id.txtPalabra);
        tvTime = findViewById(R.id.txtTime);
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
                CountDownTimer countDownTimer1 = new CountDownTimer(5000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        tv1.setText(String.format(Locale.getDefault(), "Póntelo en la frente \n%d", millisUntilFinished / 1000L));
                    }

                    @Override
                    public void onFinish() {
                        countDownTimer = new CountDownTimer(8000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                tvTime.setText(String.format(Locale.getDefault(), "%d", millisUntilFinished / 1000));

                            }

                            @Override
                            public void onFinish() {
                                menu();
                                tvTime.setText("");
                                this.cancel();
                            }
                        }.start();
                        activateSensor();
                    }
                }.start();
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

                @Override
                public void onSensorChanged(SensorEvent event) {
                    float z = event.values[2];
                    //Evento que se desata al mover el celular hacia abajo
                    if (z < -5 && aux == 0) {
                        aux++;
                        correctas++;
                        score.put(palabrasList.get(i).getPalabra(), true);
                        correctSound();
                    }
                    //Evento que se desata al mover el celular hacia arriba
                    if (z > 5 && aux == 0) {
                        aux++;
                        incorrectas++;
                        score.put(palabrasList.get(i).getPalabra(), false);
                        incorrectSound();

                        //Intent i = new Intent(PlayActivity.this, ScoreActivity.class);
                        //i.putExtra("Acertadas", aux);




                    }
                    //Reinicio de variables
                    if (aux == 1 && z < 2 && z > -2) {
                        i++;
                        if (i < palabrasList.size()) {
                            tv1.setText(palabrasList.get(i).getPalabra());
                            aux = 0;
                        } else {
                            countDownTimer.onFinish();

                            // Pasar variables al Score
                            Intent intent=new Intent(PlayActivity.this,ScoreActivity.class);
                            String correc = Integer.toString(correctas);
                            String incorrec= Integer.toString(incorrectas);
                            intent.putExtra("Acertadas", correc);
                            intent.putExtra("Fallidas", incorrec);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(),"SE TERMINÓ EL TIEMPO", Toast.LENGTH_SHORT).show();



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
        aux = -1;
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

