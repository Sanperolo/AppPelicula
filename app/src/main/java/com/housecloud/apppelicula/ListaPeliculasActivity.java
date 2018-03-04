package com.housecloud.apppelicula;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.housecloud.apppelicula.model.Pelicula;
import com.housecloud.apppelicula.recyclerUtils.AdaptadorPeliculas;
import com.housecloud.apppelicula.retrofitUtils.RestServicePeliculas;
import com.housecloud.apppelicula.retrofitUtils.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListaPeliculasActivity extends AppCompatActivity {

    private String numPel;
    private ProgressBar pb;
    private RecyclerView rv;
    private LinearLayoutManager llm;
    private AdaptadorPeliculas ap;
    private ArrayList<Pelicula> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_peliculas);

        lista = new ArrayList<Pelicula>();

        pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);

        numPel = getIntent().getIntExtra(getResources()
                .getString(R.string.clave_num_pel),10) + "";

        invocarWS();
    }

    private void invocarWS() {
        if (isNetworkAvailable()) {
            Retrofit rt = RetrofitClient.getClient(RestServicePeliculas.BASE_URL);
            RestServicePeliculas rsp = rt.create(RestServicePeliculas.class);
            Call<ArrayList<Pelicula>> call = rsp.obtenerPeliculas(numPel);

            call.enqueue(new Callback<ArrayList<Pelicula>>() {

                @Override
                public void onResponse(Call<ArrayList<Pelicula>> call,
                                       Response<ArrayList<Pelicula>> response) {
                    pb.setVisibility(View.GONE);

                    if (!response.isSuccessful()) {
                        Log.e("Error", response.code() + "");
                    } else {
                        ArrayList<Pelicula> peliculas = response.body();

                        for (Pelicula peli : peliculas) {
                            lista.add(peli);
                        }
                    }

                    configurarRV();
                }

                @Override
                public void onFailure(Call<ArrayList<Pelicula>> call, Throwable t) {
                    pb.setVisibility(View.GONE);
                    Log.e("error", t.toString());
                }
            });
        }
    }

    private void configurarRV() {
        rv = findViewById(R.id.rvPeliculas);
        rv.setHasFixedSize(true);

        llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        ap = new AdaptadorPeliculas(lista);
        ap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Pelicula pr = lista.get(rv.getChildAdapterPosition(view));

                Intent i = new Intent(ListaPeliculasActivity.this,
                        DatosPeliActivity.class);
                i.putExtra(getResources().getString(R.string.clave_num_pel),
                        pr.getId().toString());
                startActivity(i);
            }
        });

        rv.setAdapter(ap);
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
