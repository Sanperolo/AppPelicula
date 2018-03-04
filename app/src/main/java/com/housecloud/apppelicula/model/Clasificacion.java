package com.housecloud.apppelicula.model;

/**
 * Created by profesoresi on 28/02/2018.
 */
import com.housecloud.apppelicula.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clasificacion {

    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("icono")
    @Expose
    private String icono;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public int getIcono(String nomIcono){
        int iconId = 0;
        if (nomIcono.equals("drama.png")) {
            iconId = R.drawable.drama;
        }
        else if (nomIcono.equals("comedia.png")) {
            iconId = R.drawable.comedia;
        }
        else if (nomIcono.equals("documental.png")) {
            iconId = R.drawable.documental;
        }
        else if (nomIcono.equals("accion.png")) {
            iconId = R.drawable.accion;
        }
        else if (nomIcono.equals("suspense.png")) {
            iconId = R.drawable.suspense;
        }
        return iconId;

    }

}
