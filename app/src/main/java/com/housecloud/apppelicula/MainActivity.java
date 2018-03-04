package com.housecloud.apppelicula;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNum = findViewById(R.id.etNum);
    }

    public void consultar(View v) {
        int n = 0;
        boolean valorValido = true;
        try {
            n = Integer.parseInt(etNum.getText().toString());
            if (n <= 0) {
                valorValido = false;
            }

        } catch (NumberFormatException e) {
            valorValido = false;
        }

        if (valorValido) {
            Intent i = new Intent(MainActivity.this,
                    ListaPeliculasActivity.class);
            i.putExtra(getResources().getString(R.string.clave_num_pel),
                    n);
            startActivity(i);
        } else {
            Toast.makeText(this,
                    getResources().getString(R.string.msg_num_no_valido),
                    Toast.LENGTH_LONG).show();
        }

    }
}
