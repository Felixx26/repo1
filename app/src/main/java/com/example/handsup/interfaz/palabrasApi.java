package com.example.handsup.interfaz;

import com.example.handsup.model.Palabras;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface palabrasApi {

    @GET("palabras/search/{categoria}")
    Call<List<Palabras>> getPalabras(@Path("categoria") String categoria);
}
