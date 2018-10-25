package com.e.edd2laboratorio2.Classes;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class RSA {
    int e, d, n;

    public boolean esPrimo(int num) {
        boolean result = false;
        int cont = 0;

        for (int i = 1; i <= num; i++) {
            if(num % i == 0) {
                cont++;
            }
        }

        if(cont <= 2) {
            result = true;
        }
        else {
            result = false;
        }

        return result;
    }

    private int getE(int n, int on) {
        ArrayList<Integer> iteraciones = new ArrayList<Integer>();
        ArrayList<Integer> coPrimos = new ArrayList<Integer>();

        for (int i = 2; i < on; i++) {
            if(i % 2 != 0) {
                iteraciones.add(i);
            }
        }

        for (int i = 0; i < iteraciones.size(); i++) {
            int num = iteraciones.get(i);

            if((n % num != 0) && (on % num != 0)) {
                coPrimos.add(num);
            }
        }
        // int selectedPosition = getRandom(0,coPrimos.size()-1);
        int e =  coPrimos.get(0); // regresar selectedPosition
        return e;
    }


    private int getD(int e, int on) {
        int d = 0;
        ArrayList<Integer> opciones = new ArrayList<Integer>();

        for (int i = 0; i < on*2; i++) {
            if(e*i % on == 1) {
                opciones.add(i);
            }
        }
        d =  opciones.get(0);

        return d;
    }

    public void generarLlaves(int p, int q) {
        n = p*q;
        int On = (p-1) * (q-1);
        e = getE(n, On);
        String llavePublica = e + "," + n;
        saveData("Llave_publica", llavePublica);
        d = getD(e, On);
        String llavePrivada = d + "," + n;
        saveData("Llave_privada", llavePrivada);
    }

    private void saveData(String filename, String content) {
        String fileName = filename + ".txt";
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);

        try {
            FileOutputStream fos = null;
            fos = new FileOutputStream(file,true);
            fos.write(content.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String Cifrar(int texto, int e1, int n1, String name) {
        String result = "";
        BigInteger E,N;

        BigInteger Char = BigInteger.valueOf(texto);
        E = BigInteger.valueOf(e1);
        N = BigInteger.valueOf(n1);
        BigInteger power =  Char.modPow(E, N);

        result = String.valueOf(Character.toChars((int) power.longValue()));
        saveData(name+"_rsaCypher",result);

        return result;
    }

    public String Descifrar(int texto, int d1, int n1) {
        String result = "";
        BigInteger D,N;

        BigInteger Char = BigInteger.valueOf(texto);
        D = BigInteger.valueOf(d1);
        N = BigInteger.valueOf(n1);
        BigInteger power =  Char.modPow(D, N);

        result = String.valueOf(Character.toChars((int) power.longValue()));

        return result;
    }


}
