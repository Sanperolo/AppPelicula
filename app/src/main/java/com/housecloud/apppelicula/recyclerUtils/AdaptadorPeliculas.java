package com.housecloud.apppelicula.recyclerUtils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.housecloud.apppelicula.R;
import com.housecloud.apppelicula.model.*;

import java.util.ArrayList;

/**
 * Created by profesoresi on 05/02/2018.
 */

public class AdaptadorPeliculas extends RecyclerView.Adapter<AdaptadorPeliculas.VHPelicula>
        implements View.OnClickListener {

    private ArrayList<Pelicula> datos;
    private View.OnClickListener listener;

    public static class VHPelicula extends RecyclerView.ViewHolder {
        private TextView tvNombre;
        private TextView tvDirector;
        private TextView tvClasificacion;

        public VHPelicula(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDirector = itemView.findViewById(R.id.tvDirector);
            tvClasificacion = itemView.findViewById(R.id.tvClasif);
        }

        public TextView getTvNombre() {
            return tvNombre;
        }
    }

    public AdaptadorPeliculas(ArrayList<Pelicula> datos) {
        this.datos = datos;
    }

    @Override
    public VHPelicula onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pelicula_layout, parent, false);
        v.setOnClickListener(this);
        VHPelicula vhp = new VHPelicula(v);
        return vhp;
    }

    @Override
    public void onBindViewHolder(VHPelicula holder, int position) {
        holder.tvNombre.setText(datos.get(position).getNombre());
        holder.tvDirector.setText(datos.get(position).getDirector());
        holder.tvClasificacion.setText(datos.get(position).getClasificacion());
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }
}
