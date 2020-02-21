package com.example.handsup.interfaz;

import com.example.handsup.model.Categorias;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface categoriasApi {

    @GET("categorias")
    Call<List<Categorias>> getCategorias();
}
