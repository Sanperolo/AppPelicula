package com.housecloud.apppelicula;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.housecloud.apppelicula.model.*;
import com.housecloud.apppelicula.retrofitUtils.*;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DatosPeliActivity extends AppCompatActivity {

    private String idPeli;
    private TextView tvNom;
    private TextView tvDir;
    private TextView tvClasif;
    private ImageView iv;

    private Pelicula peli;
    private Clasificacion clasif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_peli);

        idPeli = getIntent().getStringExtra(getResources()
                .getString(R.string.clave_num_pel));

        tvNom = (TextView) findViewById(R.id.tvNombreP);
        tvDir = (TextView) findViewById(R.id.tvDirectorP);
        tvClasif = (TextView) findViewById(R.id.tvCladifP);
        iv = (ImageView) findViewById(R.id.ivClasif);

        invocarWSPeli();
    }

    private void invocarWSPeli() {
        if (isNetworkAvailable()) {
            Retrofit rt = RetrofitClient.getClient(RestServicePeliculas.BASE_URL);
            final RestServicePeliculas rsp = rt.create(RestServicePeliculas.class);
            Call<Pelicula> call = rsp.obtenerPeli(idPeli);

            call.enqueue(new Callback<Pelicula>() {

                @Override
                public void onResponse(Call<Pelicula> call, Response<Pelicula> response) {
                    //pb.setVisibility(View.GONE);

                    if (!response.isSuccessful()) {
                        Log.e("Error", response.code() + "");
                    } else {
                        peli = response.body();

                        cargarDatos();

                        invocarWSClasif(rsp);
                    }

                }

                @Override
                public void onFailure(Call<Pelicula> call, Throwable t) {
                    Log.e("error", t.toString());
                }
            });
        }
    }

    private void cargarDatos() {
        tvNom.setText(peli.getNombre());
        tvDir.setText(peli.getDirector());
        tvClasif.setText(peli.getClasificacion());

    }

    private void invocarWSClasif(RestServicePeliculas rsp) {
        Call<ArrayList<Clasificacion>> call = rsp.obtenerClasif(peli.getClasificacion());

        call.enqueue(new Callback<ArrayList<Clasificacion>>() {

            @Override
            public void onResponse(Call<ArrayList<Clasificacion>> call, Response<ArrayList<Clasificacion>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Error", response.code() + "");
                } else {
                    ArrayList<Clasificacion> clasifs = response.body();
                    clasif = clasifs.get(0);
                    Drawable drawable = getResources().getDrawable(clasif.getIcono(clasif.getIcono()));
                    iv.setImageDrawable(drawable);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Clasificacion>> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });

    }

    private boolean isNetworkAvailable() {
        boolean isAvailable=false;
        //Gestor de conectividad
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        //Objeto que recupera la información de la red
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //Si la información de red no es nula y estamos conectados
        //la red está disponible
        if (networkInfo!=null && networkInfo.isConnected()) isAvailable=true;

        return isAvailable;

    }
}
