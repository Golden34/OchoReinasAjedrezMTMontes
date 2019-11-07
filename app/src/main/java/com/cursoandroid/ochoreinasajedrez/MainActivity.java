package com.cursoandroid.ochoreinasajedrez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        }


    public void BotonBuscarResultado(View view) {
        // Ir a la actividad BuscarYDibujarTableroResultadoActivity
        Intent intent = new Intent(this, BuscarYDibujarTableroResultadoActivity.class);
        startActivity(intent);
    }

    public void BotonSalir(View view) {
        //Salir de la APP
        finish();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
            }
        }
    }
