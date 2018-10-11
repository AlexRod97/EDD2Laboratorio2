package com.e.edd2laboratorio2.Classes;

import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CifradoZigzag {

    int cont = 0;
    int row = -1,col = -1;
    boolean flag = true;
    int sizeOlas = 0;
    int cantOlas = 0;
    int sizeBloque = 0;
    String crestas = "", bases = "";
    ArrayList<String> bloques = new ArrayList();

    public String Descifrar(String phrase, int nivel) {
        StringBuilder result = new StringBuilder();
        StringBuilder finalResult = new StringBuilder();
        bloques = new ArrayList();
        int longitud = phrase.length();
        sizeOlas = (nivel*2) -2;
        cantOlas = (int) Math.ceil((double)longitud/(double)sizeOlas);
        sizeBloque = 2 * cantOlas;
        getCrestas(phrase, cantOlas);
        getBases(phrase,cantOlas);
        getComplemento(phrase, cantOlas, sizeBloque);
        int count = 0;

        for (int i = 0; i < cantOlas; i++) {
            result.append(String.valueOf(crestas.charAt(i)));
            for (int j = 0; j < bloques.size(); j++) {
                String word = bloques.get(j);
                result.append(String.valueOf(word.charAt(count)));
            }
            result.append(String.valueOf(bases.charAt(i)));
            count++;
            for (int j = bloques.size()-1; j > -1; j--) {
                String word = bloques.get(j);
                result.append(String.valueOf(word.charAt(count)));
            }
            count++;
        }

        for (int i = 0; i < result.length(); i++) {
            String letra = String.valueOf(result.charAt(i));
            if(!letra.equals("@")) {
                finalResult.append(letra);
            }
        }

        return finalResult.toString();
    }

    private void getCrestas(String word, int cant) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cant; i++) {
            result.append(String.valueOf(word.charAt(i)));
        }
        crestas = result.toString();
    }

    private void getBases(String word, int cant) {
        StringBuilder result = new StringBuilder();
        int inicio = word.length() - cant;
        for (int i = inicio; i < word.length(); i++) {
            result.append(String.valueOf(word.charAt(i)));
        }
        bases = result.toString();
    }

    private void getComplemento(String word, int cb, int cant) {
        StringBuilder result = new StringBuilder();
        StringBuilder cadena = new StringBuilder();

        for (int i =cb; i < word.length()- cb; i++) {
            result.append(String.valueOf(word.charAt(i)));
        }

        int start = 0, end = cant;
        int vueltas = result.length() / cant;

        for (int i = 0; i < vueltas; i++) {
            cadena.append(result.substring(start, end));
            start = end;
            end = end + cant;
            bloques.add(cadena.toString());
            cadena.setLength(0);
        }
    }

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
