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
import android.widget.Toast;

import com.e.edd2laboratorio2.Classes.CifradoZigzag;
import com.e.edd2laboratorio2.Classes.RSA;
import com.e.edd2laboratorio2.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static android.widget.Toast.makeText;

public class RsaCypherFragment extends Fragment {
    private static final String TAG = "RSACypherFragment";
    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 42;
    Button btnOpenFile,btnOpenFile2, btnLlaves,btnLeerLlave, btnCifrar;
    TextView tvInput, tvOutput;
    EditText etP, etQ;
    String mainData,path;
    java.io.File File;
    int p,q;
    String name;
    RSA rsa = new RSA();
    String[] split;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rsa_cypher_tab, container, false);

        btnOpenFile = (Button)view.findViewById(R.id.btnReadFileRSA);
        btnOpenFile2 = (Button)view.findViewById(R.id.btnReadFileRSA2);

        btnCifrar = (Button)view.findViewById(R.id.btnCifrarRSA);
        tvInput = (TextView)view.findViewById(R.id.tViewInputRSA);
        tvOutput = (TextView)view.findViewById(R.id.tViewOutputRSA);
        etP = (EditText)view.findViewById(R.id.etP);
        etQ = (EditText)view.findViewById(R.id.etQ);
        btnLlaves = (Button)view.findViewById(R.id.btnLlaves);
        btnLeerLlave = (Button)view.findViewById(R.id.btnOperarLlaveP);
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

        btnLeerLlave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readKeys(path);
            }
        });

        btnOpenFile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileSearch();
            }
        });

        btnLlaves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p = Integer.valueOf(String.valueOf(etP.getText()));
                q = Integer.valueOf(String.valueOf(etQ.getText()));
                boolean resultP = rsa.esPrimo(p);
                boolean resultQ = rsa.esPrimo(q);

                if(resultP & resultQ) {
                    rsa.generarLlaves(p,q);
                }
                else {
                     //makeText(this, "Error al almacenar el archivo", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnCifrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                   readText(path);
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
            }
        }
    }

    private String[] readKeys(String input) {
        File file = new File(Environment.getExternalStorageDirectory(),input);
        StringBuilder text  = new StringBuilder();
        String result = "";
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
        result = text.toString();
        split = result.split("\\,");
        return split;
    }

    private String readText(String input) {
        File file = new File(Environment.getExternalStorageDirectory(),input);
        StringBuilder text  = new StringBuilder();
        String result = "";
        String name = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            FileInputStream fileStream = new FileInputStream(file);
            name = file.getName();
            name = name.substring(0,name.indexOf("."));

            int element;
            while ((element = br.read()) != -1) {
                text.append(rsa.Cifrar(element,Integer.valueOf(split[0]), Integer.valueOf(split[1]),name));
            }
            result = text.toString();
            br.close();
            File = file;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }




}
