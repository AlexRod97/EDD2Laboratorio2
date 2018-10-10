package com.e.edd2laboratorio2.Classes;

import java.util.ArrayList;

public class ZigZag {

    int cont = 0;
    int row = -1,col = -1;
    boolean flag = true;
    int sizeOlas = 0;
    int cantOlas = 0;
    int sizeBloque = 0;
    String crestas = "", bases = "";
    ArrayList<String> bloques = new ArrayList();

    public String Cifrar(String phrase, int nivel, String fileName) {

        StringBuilder result = new StringBuilder();
        int longitud = phrase.length();
        sizeOlas = (nivel*2) -2;
        cantOlas = (int) Math.ceil((double)longitud/(double)sizeOlas);
        sizeBloque = 2 * cantOlas;
        String letra = "";
        int vueltas = sizeOlas*cantOlas;
        String[][] array = new String[nivel][vueltas];
        cont = 0;
        col = -1;
        row = -1;
        flag = true;

        while(cont < (vueltas)) {

            if(flag) {
                row++;
                col++;
                if(cont >= longitud) {
                    array[col][row] = "@";
                }
                else {
                    letra = String.valueOf(phrase.charAt(cont));
                    array[col][row] = letra;
                }

            }
            else {
                row++;
                col--;
                if(cont >= longitud) {
                    array[col][row] = "@";
                }
                else {
                    letra = String.valueOf(phrase.charAt(cont));
                    array[col][row] = letra;
                }
            }

            if(col == nivel-1) {
                flag = !flag;
            }

            if(col == 0 && row > 0) {
                flag = !flag;
                //j++;
            }

            cont++;
        }

        for (int k = 0; k < nivel; k++) {
            for (int l = 0; l < vueltas; l++) {
                letra = array[k][l];
                if( letra != null) {
                    result.append(letra);
                }
            }
        }
        SaveText(fileName,result.toString());
        return result.toString();
    }

    private void SaveText(String filename, String content) {
        String fileName = filename + "_cifrado.txt";
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);

        try {
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
