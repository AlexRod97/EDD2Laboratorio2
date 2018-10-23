package com.e.edd2laboratorio2.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.e.edd2laboratorio2.Classes.SDES;
import com.e.edd2laboratorio2.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class SdesDecypherFragment extends Fragment {
    private static final String TAG = "SdesDecypherFragment";
    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 42;
    Button btnOpenFile, btnDescifrar;
    TextView tvInput, tvOutput;
    EditText etNivel;
    String mainData,path;
    java.io.File File;
    int numLlave;
    String name,line;
    SDES sdes = new SDES();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sdes_decypher_tab, container, false);
        btnOpenFile = (Button)view.findViewById(R.id.btnOpenFileSDES);
        btnDescifrar = (Button)view.findViewById(R.id.btnDescifrarSDES);
        tvInput = (TextView)view.findViewById(R.id.tViewInputRSA);
        tvOutput = (TextView)view.findViewById(R.id.tViewOutputRSA);
        etNivel = (EditText)view.findViewById(R.id.eTextNivelSDES);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_STORAGE);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_STORAGE);
        }

        btnOpenFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileSearch();
            }
        });

        btnDescifrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    numLlave = Integer.valueOf(String.valueOf(etNivel.getText()));
                    StringBuilder finalResult = new StringBuilder();
                    if( numLlave < 1024) {
                        name = File.getName();
                        name = name.substring(0,name.indexOf("."));
                        sdes.getKeys(String.valueOf(numLlave));
                        int count = 0;
                        int size = line.length();
                        for(int i =0; i < size-1; i ++) {
                            StringBuilder letter = new StringBuilder();
                            for (int j = 0; j < 8; j++) {
                                letter.append(line.charAt(count));
                                count++;
                            }
                            int decimal = Integer.parseInt(sdes.Decypher(letter.toString()),2);
                            String letra = String.valueOf((char)decimal);
                            finalResult.append(letra);
                            decimal = 0;
                            tvOutput.setText(finalResult.toString());
                        }

                    }
                    else {
                        //Mensaje de error
                    }

                }
                catch (Exception e) {
                    e.getMessage();
                    // Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                    // Toast.makeText(this, "Error al almacenar el archivo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void fileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        startActivityForResult(intent,READ_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                Uri uri = data.getData();
                path = uri.getPath();
                path = path.substring(path.indexOf(":")+1);
                mainData = readText(path);
                tvInput.setText(mainData);
            }
        }
    }

    private String readText(String input) {
        File file = new File(Environment.getExternalStorageDirectory(),input);
        try {

            BufferedReader br = new BufferedReader(new FileReader(file));
            FileInputStream fileStream = new FileInputStream(file);
            byte[] values = new byte[(int)file.length()];
            fileStream.read(values);
            fileStream.close();
            String content = new String(values,"UTF-8");

            line = content;
            File = file;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }


}
