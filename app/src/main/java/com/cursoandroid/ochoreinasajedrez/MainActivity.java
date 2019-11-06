package com.cursoandroid.ochoreinasajedrez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity<fila> extends AppCompatActivity {


    String[][] tableroAjedrez = new String[8][8];
    int fila;
    int[] columna = new int[8];
    Boolean[] cuadriculaBuenaEncontrada = new Boolean[8];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }




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
}
