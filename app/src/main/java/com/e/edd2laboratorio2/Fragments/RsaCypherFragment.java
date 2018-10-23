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

import com.e.edd2laboratorio2.Classes.CifradoZigzag;
import com.e.edd2laboratorio2.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class RsaCypherFragment extends Fragment {
    private static final String TAG = "CypherFragment";
    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 42;
    Button btnOpenFile, btnCifrar;
    TextView tvInput, tvOutput;
    EditText etNivel;
    String mainData,path;
    java.io.File File;
    int nivel;
    String name;
    CifradoZigzag zigzag = new  CifradoZigzag();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cypher_tab, container, false);
        btnOpenFile = (Button)view.findViewById(R.id.btnReadFile);
        btnCifrar = (Button)view.findViewById(R.id.btnDescifrar);
        tvInput = (TextView)view.findViewById(R.id.tViewInputSDES);
        tvOutput = (TextView)view.findViewById(R.id.tViewOutputSDES);
        etNivel = (EditText)view.findViewById(R.id.etNivelSDES);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

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

        btnCifrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    nivel = Integer.valueOf(String.valueOf(etNivel.getText()));
                    name = File.getName();
                    name = name.substring(0,name.indexOf("."));
                    tvOutput.setText(zigzag.Cifrar(mainData,nivel, name));
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
        StringBuilder text  = new StringBuilder();
        try {

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
            File = file;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }


}
