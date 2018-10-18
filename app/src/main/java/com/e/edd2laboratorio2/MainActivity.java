package com.e.edd2laboratorio2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnZigzag, btnSdes, btnRsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnZigzag = findViewById(R.id.btnZigzag);
        btnSdes = findViewById(R.id.btnSdes);

        btnZigzag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent zigzag = new Intent(MainActivity.this,Zigzag.class);
                startActivity(zigzag);
            }
        });

        btnSdes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Sdes = new Intent(MainActivity.this,Sdes.class);
                startActivity(Sdes);
            }
        });
    }
}
