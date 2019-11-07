package com.cursoandroid.ochoreinasajedrez;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.Gravity.CENTER;
import static com.cursoandroid.ochoreinasajedrez.R.color.colorAccent;
import static com.cursoandroid.ochoreinasajedrez.R.color.colorAzulCielo;
import static com.cursoandroid.ochoreinasajedrez.R.color.colorBlanco;
import static com.cursoandroid.ochoreinasajedrez.R.color.colorNegro;
import static com.cursoandroid.ochoreinasajedrez.R.color.colorAmarillo;



public class BuscarYDibujarTableroResultadoActivity<fila> extends AppCompatActivity {

    public String[][] tableroAjedrez = new String[8][8];
    public int fila;
    public int[] columna = new int[8];
    public Boolean[] cuadriculaBuenaEncontrada = new Boolean[8];

    //Para dibujar y rellenar el layout dinámico con el resultado de las posiciones de las 8 reinas
    public int tope_layout_x = 8;
    public int tope_layout_y = 8;
    public int totalCuadriculas = 64;       //El tablero de ajedrez tiene 8 filas * 8 columnas = 64 cuadriculas donde poner a las reinas
    //public int tamañoImagen;



    // Inicialización de variables, llamar a los métodos que buscará el resultado y mostrará el resultado en Layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar_y_dibujar_tablero_resultado);

        for (int i = 0; i < tableroAjedrez.length; i++) {
            for (int j = 0; j < tableroAjedrez[i].length; j++) {
                tableroAjedrez[i][j] = "Libre---";
            }
        };

        fila =0;

        for (int i = 0; i < columna.length; i++) {
                columna[i] = 0;
            }

        for (int i = 0; i < cuadriculaBuenaEncontrada.length; i++) {
            cuadriculaBuenaEncontrada[i] = false;
        }

        EncontrarCuadriculaBuena(fila);

        //DisplayMetrics metrics = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //double anchoPixeles = metrics.widthPixels; // ancho absoluto en pixels

        //tamañoImagen = (int) (8 * (anchoPixeles / 1080) + 5);

        RellenarTableroTextView();

    }


    // Método recursivo que intenta encontrar cuadriculas buena para las reinas por filas, si se ve que no era buena
    // se deshacer las asignaciones que sean necesarias, para seguir buscando por fuerza bruta
    public void EncontrarCuadriculaBuena(Integer f){

        while (cuadriculaBuenaEncontrada[f].equals(false)) {

         //   EscribirLogTableroAjedrez();

            if ((tableroAjedrez[f][columna[f]].equals("Libre---")) &&
               (VerificarCuadriculasAfectadasLibres(f, columna[f]))) {

                    Log.d("MiAPP", "Reina en: " + f + " , " + columna[f]);
                    cuadriculaBuenaEncontrada[f] = true;
                    tableroAjedrez[f][columna[f]] = "Reina---";
                    OcuparCuadriculasAfectadas(f, columna[f]);  //Se pone ocupadas las cuadriculas de la horizontal, vertical y diagonal

                    if (f < 7) {
                        f++;
                        Log.d("MiAPP", "nueva fila:" + f);
                      //  EscribirLogTableroAjedrez();
                        EncontrarCuadriculaBuena(f);
                    } else {
                        Log.d("MiAPP", "¡ENHORABUENA!, SOLUCIÓN ENCONTRADA ");
                        Log.d("MiAPP", "===================================");
                        EscribirLogTableroAjedrez();
                    }

            }
            else if (columna[f] == 7) {
                    do {
                        columna[f] = 0;
                        f--;
                        tableroAjedrez[f][columna[f]] = "Libre---";    //Quito a la reina de esa cuadricula
                        LiberarCuadriculasAfectadas(f, columna[f]);    //Se pone libres las cuadriculas de la horizontal, vertical y diagonal
                        cuadriculaBuenaEncontrada[f] = false;
                    } while (columna[f] == 7);
                    columna[f]++;
                    }
                else
                    columna[f]++;
        }

    }



    // Visualiza en el Logcat la situación del tablero de ajedrez
    private void EscribirLogTableroAjedrez() {
        Log.d("MiAPP", "tableroAjedrez: " +       tableroAjedrez[0][0] + " " + tableroAjedrez[0][1] + " " + tableroAjedrez[0][2]
                + " " + tableroAjedrez[0][3] + " " + tableroAjedrez[0][4] + " " + tableroAjedrez[0][5]
                + " " + tableroAjedrez[0][6] + " " + tableroAjedrez[0][7]);
        Log.d("MiAPP", "tableroAjedrez: " +        tableroAjedrez[1][0] + " " + tableroAjedrez[1][1] + " " + tableroAjedrez[1][2]
                + " " + tableroAjedrez[1][3] + " " + tableroAjedrez[1][4] + " " + tableroAjedrez[1][5]
                + " " + tableroAjedrez[1][6] + " " + tableroAjedrez[1][7]);
        Log.d("MiAPP", "tableroAjedrez: " +        tableroAjedrez[2][0] + " " + tableroAjedrez[2][1] + " " + tableroAjedrez[2][2]
                + " " + tableroAjedrez[2][3] + " " + tableroAjedrez[2][4] + " " + tableroAjedrez[2][5]
                + " " + tableroAjedrez[2][6] + " " + tableroAjedrez[2][7]);
        Log.d("MiAPP", "tableroAjedrez: " +        tableroAjedrez[3][0] + " " + tableroAjedrez[3][1] + " " + tableroAjedrez[3][2]
                + " " + tableroAjedrez[3][3] + " " + tableroAjedrez[3][4] + " " + tableroAjedrez[3][5]
                + " " + tableroAjedrez[3][6] + " " + tableroAjedrez[3][7]);
        Log.d("MiAPP", "tableroAjedrez: " +        tableroAjedrez[4][0] + " " + tableroAjedrez[4][1] + " " + tableroAjedrez[4][2]
                + " " + tableroAjedrez[4][3] + " " + tableroAjedrez[4][4] + " " + tableroAjedrez[4][5]
                + " " + tableroAjedrez[4][6] + " " + tableroAjedrez[4][7]);
        Log.d("MiAPP", "tableroAjedrez: " +        tableroAjedrez[5][0] + " " + tableroAjedrez[5][1] + " " + tableroAjedrez[5][2]
                + " " + tableroAjedrez[5][3] + " " + tableroAjedrez[5][4] + " " + tableroAjedrez[5][5]
                + " " + tableroAjedrez[5][6] + " " + tableroAjedrez[5][7]);
        Log.d("MiAPP", "tableroAjedrez: " +        tableroAjedrez[6][0] + " " + tableroAjedrez[6][1] + " " + tableroAjedrez[6][2]
                + " " + tableroAjedrez[6][3] + " " + tableroAjedrez[6][4] + " " + tableroAjedrez[6][5]
                + " " + tableroAjedrez[6][6] + " " + tableroAjedrez[6][7]);
        Log.d("MiAPP", "tableroAjedrez: " +        tableroAjedrez[7][0] + " " + tableroAjedrez[7][1] + " " + tableroAjedrez[7][2]
                + " " + tableroAjedrez[7][3] + " " + tableroAjedrez[7][4] + " " + tableroAjedrez[7][5]
                + " " + tableroAjedrez[7][6] + " " + tableroAjedrez[7][7]);
        Log.d("MiAPP", "---------------------------------------------------------------------------------------------");
    }



    //Verifica que si colocamos una reina en una cuadricula, esta no será comida por algunas de las ya colocadas en cuadriculas
    //Se verifica con los 3 movimientos que tienen las reinas: horizontal, vertical, y diagonal
    private Boolean VerificarCuadriculasAfectadasLibres(Integer f, Integer c) {
        Boolean estanLibres = true;
        int fila;
        int columna;

        // Se verifica la horizontal
        for (int j = 0; j < 8; j++) {
            if (tableroAjedrez[f][j] == "Reina---")
                   estanLibres = false;
        }

        // Se verifica la vertical
        for (int i = 0; i < 8; i++) {
               if (tableroAjedrez[i][c] == "Reina---")
                   estanLibres = false;
        }

        // Se verifica la diagonal ascendente izquierda
        fila = f - 1;
        columna = c - 1;

        while (fila > -1 && columna > -1) {
            if (tableroAjedrez[fila][columna] == "Reina---") {
                estanLibres = false;
            };
            fila--;
            columna--;
        }

        // Se verifica la diagonal ascendente derecha
        fila = f - 1;
        columna = c + 1;

        while (fila > -1 && columna < 8) {
            if (tableroAjedrez[fila][columna] == "Reina---") {
                estanLibres = false;
            }
            fila--;
            columna++;
        }

        // Se verifica la diagonal descendente derecha
        fila = f + 1;
        columna = c + 1;

        while (fila < 8 && columna < 8) {
            if (tableroAjedrez[fila][columna] == "Reina---") {
                estanLibres = false;
            };
            fila++;
            columna++;
        }

        // Se verifica la diagonal descendente izquierda
        fila = f + 1;
        columna = c - 1;

        while (fila < 8 && columna > -1) {
            if (tableroAjedrez[fila][columna] == "Reina---") {
                estanLibres = false;
            };
            fila++;
            columna--;
        }

        return (estanLibres);
    }



    //Cuando se deshace la asignación de una reina a una cuadricula, hay que liberar las cuadriculas
    //que se habian ocupado por los 3 movimientos que tienen las reinas: horizontal, vertical, y diagonal
    private void LiberarCuadriculasAfectadas(int f, int c) {
        int fila;
        int columna;
        String ocupadaPorFila = new String("Ocupada" + f);


        // Libero la horizontal
        for (int j = 0; j < 8; j++) {
            if (tableroAjedrez[f][j].equals(ocupadaPorFila)) {
                tableroAjedrez[f][j] = "Libre---";
            }
        }

        // Libero la vertical
        for (int i = 0; i < 8; i++) {
            if (tableroAjedrez[i][c].equals(ocupadaPorFila)) {
                tableroAjedrez[i][c] = "Libre---";
            }
        }

        // Libero la diagonal ascendente izquierda
        fila = f - 1;
        columna= c - 1;

        while (fila > -1 && columna > -1) {
            if (tableroAjedrez[fila][columna].equals(ocupadaPorFila)) {
                tableroAjedrez[fila][columna] = "Libre---";
            }
            fila--;
            columna--;
        }

        // Libero la diagonal ascendente derecha
        fila = f - 1;
        columna = c + 1;

        while (fila > -1 && columna < 8) {
            if (tableroAjedrez[fila][columna].equals(ocupadaPorFila)) {
                tableroAjedrez[fila][columna] = "Libre---";
            }
            fila--;
            columna++;
        }

        // Libero la diagonal descendente derecha
        fila = f + 1;
        columna = c + 1;

        while (fila < 8 && columna < 8) {
            if (tableroAjedrez[fila][columna].equals(ocupadaPorFila)) {
                tableroAjedrez[fila][columna] = "Libre---";
            }
            fila++;
            columna++;
        }

        // Libero la diagonal descendente izquierda
        fila = f + 1;
        columna = c - 1;

        while (fila < 8 && columna > -1) {
            if (tableroAjedrez[fila][columna].equals(ocupadaPorFila)) {
                tableroAjedrez[fila][columna] = "Libre---";
            }
            fila++;
            columna--;


        }

    }



    //Cuando se asigna una reina a una cuadricula, hay que marcar las cuadriculas en las que no se
    //podria poner otra reina debido a los 3 movimientos que tienen las reinas: horizontal, vertical, y diagonal
    private void OcuparCuadriculasAfectadas(Integer f, Integer c) {
        int fila;
        int columna;

        // Ocupo la horizontal
        for (int j = 0; j < 8; j++) {
            if (tableroAjedrez[f][j] == "Libre---") {
                tableroAjedrez[f][j] = "Ocupada" + f;
            }
        }

        // Ocupo la vertical
        for (int i = 0; i < 8; i++) {
            if (tableroAjedrez[i][c] == "Libre---"){
                tableroAjedrez[i][c] = "Ocupada" + f;
            }
        }

        // Ocupo la diagonal ascendente izquierda
        fila = f - 1;
        columna = c - 1;

        while (fila > -1 && columna > -1) {
            if ((tableroAjedrez[fila][columna] == "Libre---")) {
                tableroAjedrez[fila][columna] = "Ocupada" + f;
            }
            fila--;
            columna--;
        }

        // Ocupo la diagonal ascendente derecha
        fila = f - 1;
        columna = c + 1;

        while (fila > -1 && columna < 8) {
            if ((tableroAjedrez[fila][columna] == "Libre---")) {
                tableroAjedrez[fila][columna] = "Ocupada" + f;
            }
            fila--;
            columna++;
        }

        // Ocupola diagonal descendente derecha
        fila = f + 1;
        columna = c + 1;

        while (fila < 8 && columna < 8) {
            if ((tableroAjedrez[fila][columna] == "Libre---")) {
                tableroAjedrez[fila][columna] = "Ocupada" + f;
            }
            fila++;
            columna++;
        }

        // Ocupo la diagonal descendente izquierda
        fila = f + 1;
        columna = c - 1;

        while (fila < 8 && columna > -1) {
            if ((tableroAjedrez[fila][columna] == "Libre---")) {
                tableroAjedrez[fila][columna] = "Ocupada" + f;
            }
            fila++;
            columna--;
        }
    }



    //Crea layouts dinámicos donde se visualizará el tablero de ajedrez en la pantalla
    @SuppressLint("NewApi")
    private void RellenarTableroTextView() {
        // Layout dinámico que mostrará el tablero de ajedrez
        LinearLayout Layout_TABLERO = findViewById(R.id.tablero_ajedrez_layout);
        Layout_TABLERO.setPadding(10, 10, 10, 10);

        for (int y = 0; y < tope_layout_y; y++) {
            LinearLayout layout_y = GeneraLinearLayout(LinearLayout.HORIZONTAL);
            for (int x = 0; x < tope_layout_x; x++) {
                LinearLayout layout_x = GeneraLinearLayout(LinearLayout.HORIZONTAL);
                layout_x.addView(GeneraCaja(y, x));
                layout_y.addView(layout_x);
            }
            Layout_TABLERO.addView(layout_y);
        }
    }



    //Crea una lineaLayout con algunos párametros dinámicamente
    public LinearLayout GeneraLinearLayout(int Orientacion){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0F);
        LinearLayout LL = new LinearLayout(this);
        LL.setOrientation(Orientacion);
        if (Orientacion == LinearLayout.HORIZONTAL ){
            lp.setMargins(0, 0, 0, 0);
            LL.setLayoutParams(lp);
        }
        LL.setLayoutParams(lp);
        return LL;
    }



    //Crea una cuadricula del tablero de ajedrez
    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public TextView GeneraCaja(int ty, int tx){
        int posi = (ty*8 + tx);
        int suma;

        TextView  cuadricula = new TextView(this);

        if (tableroAjedrez[ty][tx] == "Reina---")
             cuadricula.setText(" R ");
        else
             cuadricula.setText("   ");

        cuadricula.setTextSize(30);

        cuadricula.setGravity(CENTER);
        cuadricula.setLayoutParams(new LinearLayout.LayoutParams(100, 100));

        Resources res = getResources();
        Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.cuadrar, null);
        cuadricula.setBackgroundDrawable(drawable);
        cuadricula.setTextColor(getResources().getColor(colorAccent));

        //Según la cuadricula sea par o impar la suma de sus coordenadas será de color negro o blanco
        suma = ty + tx;
        if ((suma % 2) == 1) {
             cuadricula.setBackgroundColor(getResources().getColor(colorNegro));
        } else {
             cuadricula.setBackgroundColor(getResources().getColor(colorBlanco));
        }

        return  cuadricula;
    }



    //Si el usuario teclea el botón salir vamos a la pantalla de inicio
    public void BotonSalir(View view) {
        // Ir a la actividad MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
