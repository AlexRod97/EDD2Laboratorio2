package com.e.edd2laboratorio2.Classes;

import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class SDES {

    int llaveUsuario = 0;
    String key1 = "", key2 = "";
    String s0 [][] = {{"01","00","11","10"},
            {"11","10","01","00"},
            {"00","10","01","11"},
            {"11","01","11","10"}};

    String s1 [][] = {{"00","01","10","11"},
            {"10","00","01","11"},
            {"11","00","01","00"},
            {"10","01","00","11"}};


    public void getKeys(String num) {
        String p10, ls_1, ls_2, p8;
        String param1, param2;

        p10 = P10(num);
        param1 = p10.substring(0,5);
        param2 = p10.substring(5,10);
        ls_1 = LS1(param1);
        ls_2 = LS1(param2);
        p8 = P8(ls_1 + ls_2);

        key1 = p8;

        ls_1 = LS2(ls_1);
        ls_2 = LS2(ls_2);
        p8 = P8(ls_1 + ls_2);
        key2 = p8;
    }

    private String P10(String num) {
        StringBuilder result = new StringBuilder();
        String binary = "";
        binary = Integer.toBinaryString(Integer.valueOf(num));
        binary = fillBinary(binary);

        result.append(binary.charAt(0));
        result.append(binary.charAt(2));
        result.append(binary.charAt(4));
        result.append(binary.charAt(6));
        result.append(binary.charAt(8));
        result.append(binary.charAt(1));
        result.append(binary.charAt(3));
        result.append(binary.charAt(5));
        result.append(binary.charAt(7));
        result.append(binary.charAt(9));

        return result.toString();
    }

    private String fillBinary(String num) {
        int cant = 10 - num.length();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < cant; i++) {
            result.append("0");
        }
        result.append(num);

        return result.toString();
    }

    private String LS1(String num) {
        StringBuilder result = new StringBuilder();

        for (int i = 1; i <= 4; i++) {
            result.append(num.charAt(i));
        }
        result.append(num.charAt(0));

        return result.toString();
    }

    private static int getRandom(int min, int max){
        int x = (int) ((Math.random()*((max-min)+1))+min);

        return x;
    }

    private String P8(String num) {
        StringBuilder result = new StringBuilder();

        result.append(num.charAt(1));
        result.append(num.charAt(3));
        result.append(num.charAt(5));
        result.append(num.charAt(7));
        result.append(num.charAt(2));
        result.append(num.charAt(4));
        result.append(num.charAt(6));
        result.append(num.charAt(8));

        return result.toString();
    }

    private String LS2(String num) {
        StringBuilder result = new StringBuilder();

        for (int i = 2; i < 5; i++) {
            result.append(num.charAt(i));
        }
        result.append(num.charAt(0));
        result.append(num.charAt(1));

        return result.toString();
    }

    public String set8Bit(String character) {
        int a = (char) character.charAt(0);
        String result = Integer.toBinaryString(Integer.valueOf(a));
        StringBuilder bits = new StringBuilder();

        for (int i = 0; i < 8 - result.length(); i++) {
            bits.append("0");
        }
        bits.append(result);
        return bits.toString();
    }

    private String IP(String num) {
        StringBuilder result = new StringBuilder();

        result.append(num.charAt(1));
        result.append(num.charAt(5));
        result.append(num.charAt(2));
        result.append(num.charAt(0));
        result.append(num.charAt(3));
        result.append(num.charAt(7));
        result.append(num.charAt(4));
        result.append(num.charAt(6));

        return result.toString();
    }

    private String EP(String num) {
        StringBuilder result = new StringBuilder();

        result.append(num.charAt(3));
        result.append(num.charAt(0));
        result.append(num.charAt(1));
        result.append(num.charAt(2));
        result.append(num.charAt(1));
        result.append(num.charAt(2));
        result.append(num.charAt(3));
        result.append(num.charAt(0));

        return result.toString();
    }

    private String S0(String num) {
        String result;
        String puente = String.valueOf(num.charAt(1)) +String.valueOf(num.charAt(2));
        int col = Integer.parseInt(puente,2);
        puente = String.valueOf(num.charAt(0)) +String.valueOf(num.charAt(3));
        int row = Integer.parseInt(puente,2);
        result = s0[col][row];

        return result;
    }

    private String S1(String num) {
        String result;
        String puente = String.valueOf(num.charAt(1)) +String.valueOf(num.charAt(2));
        int col = Integer.parseInt(puente,2);
        puente = String.valueOf(num.charAt(0)) +String.valueOf(num.charAt(3));
        int row = Integer.parseInt(puente,2);
        result = s1[col][row];

        return result;
    }

    private String P4(String num) {
        StringBuilder result = new StringBuilder();

        result.append(num.charAt(1));
        result.append(num.charAt(3));
        result.append(num.charAt(2));
        result.append(num.charAt(0));

        return result.toString();
    }

    private String XOR (String num1, String num2, int vueltas) {
        StringBuilder result = new StringBuilder();
        int a, b;

        for (int i = 0; i < vueltas; i++) {
            a = Integer.valueOf(num1.charAt(i));
            b = Integer.valueOf(num2.charAt(i));
            if(a == b) {
                result.append("0");
            }
            else {
                result.append("1");
            }
        }

        return result.toString();
    }

    private String IP_inv (String num) {
        String[] chain = new String[8];

        chain[1] = String.valueOf(num.charAt(0));
        chain[5] = String.valueOf(num.charAt(1));
        chain[2] = String.valueOf(num.charAt(2));
        chain[0] = String.valueOf(num.charAt(3));
        chain[3] = String.valueOf(num.charAt(4));
        chain[7] = String.valueOf(num.charAt(5));
        chain[4] = String.valueOf(num.charAt(6));
        chain[6] = String.valueOf(num.charAt(7));

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            result.append(chain[i]);
        }

        return result.toString();
    }

    public String Cypher(String name, String cadena) {
        String result;
        String ip_1, ip_2, ep, xor, xor_1, xor_2, box0, box1, p4, xor2;
        String SW_ip_1, SW_ip_2, SW_ep, SW_xor, SW_xor_1, SW_xor_2, SW_box0, SW_box1, SW_p4, SW_xor2;

        String ip = IP(cadena);
        ip_1 = ip.substring(0,4);
        ip_2 = ip.substring(4,8);

        ep = EP(ip_2);

        xor = XOR(ep, key1,8);
        xor_1 = xor.substring(0,4);
        xor_2 = xor.substring(4,8);

        box0 = S0(xor_1);
        box1 = S1(xor_2);

        p4 = P4(box0 + box1);

        xor2 = XOR(ip_1,p4,4);

        //sw

        SW_ip_1 = ip_2;
        SW_ip_2 = xor2;

        SW_ep = EP(SW_ip_2);

        SW_xor = XOR(SW_ep, key2,8);
        SW_xor_1 = SW_xor.substring(0,4);
        SW_xor_2 = SW_xor.substring(4,8);

        SW_box0 = S0(SW_xor_1);
        SW_box1 = S1(SW_xor_2);

        SW_p4 = P4(SW_box0 + SW_box1);

        SW_xor2 = XOR(SW_p4, SW_ip_1,4);

        result = IP_inv(SW_xor2 + SW_ip_2);

        saveData(name, result);

        return result;
    }

    private void saveData(String filename, String content) {
        String fileName = filename + "_SDES.txt";
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



    public String Decypher(String cadena) {
        String result;
        String ip_1, ip_2, ep, xor, xor_1, xor_2, box0, box1, p4, xor2;
        String SW_ip_1, SW_ip_2, SW_ep, SW_xor, SW_xor_1, SW_xor_2, SW_box0, SW_box1, SW_p4, SW_xor2;

        String ip = IP(cadena);
        ip_1 = ip.substring(0,4);
        ip_2 = ip.substring(4,8);

        ep = EP(ip_2);

        xor = XOR(ep, key2,8);
        xor_1 = xor.substring(0,4);
        xor_2 = xor.substring(4,8);

        box0 = S0(xor_1);
        box1 = S1(xor_2);

        p4 = P4(box0 + box1);

        xor2 = XOR(ip_1,p4,4);

        //sw

        SW_ip_1 = ip_2;
        SW_ip_2 = xor2;

        SW_ep = EP(SW_ip_2);

        SW_xor = XOR(SW_ep, key1,8);
        SW_xor_1 = SW_xor.substring(0,4);
        SW_xor_2 = SW_xor.substring(4,8);

        SW_box0 = S0(SW_xor_1);
        SW_box1 = S1(SW_xor_2);

        SW_p4 = P4(SW_box0 + SW_box1);

        SW_xor2 = XOR(SW_p4, SW_ip_1,4);

        result = IP_inv(SW_xor2 + SW_ip_2);

        return result;
    }



}
