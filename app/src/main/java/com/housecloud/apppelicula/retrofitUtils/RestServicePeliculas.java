package com.housecloud.apppelicula.retrofitUtils;

import com.housecloud.apppelicula.model.Clasificacion;
import com.housecloud.apppelicula.model.Pelicula;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by profesoresi on 05/02/2018.
 */

public interface RestServicePeliculas {
    public static final String BASE_URL = "http://10.0.2.2:3000/";

    @GET("peliculas")
    Call<ArrayList<Pelicula>> obtenerPeliculas(@Query("_limit") String limite);

    @GET("peliculas/{id_peli}")
    Call<Pelicula> obtenerPeli(@Path("id_peli") String codigo);

    @GET("clasificacion")
    Call<ArrayList<Clasificacion>> obtenerClasif(@Query("nombre") String clasif);
}
