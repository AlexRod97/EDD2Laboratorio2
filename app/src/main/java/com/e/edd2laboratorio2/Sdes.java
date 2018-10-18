package com.e.edd2laboratorio2;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.e.edd2laboratorio2.Fragments.CypherFragment;
import com.e.edd2laboratorio2.Fragments.DecypherFragment;
import com.e.edd2laboratorio2.Fragments.SdesCypherFragment;
import com.e.edd2laboratorio2.Fragments.SdesDecypherFragment;
import com.e.edd2laboratorio2.Fragments.SectionsPageAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Sdes extends AppCompatActivity {

    private static final String TAG = "Sdes";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdes);
        Log.d(TAG, "onCreate: Starting.");

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.SdesContainer);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.SdesTabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new SdesCypherFragment(), "Cifrar");
        adapter.addFragment(new SdesDecypherFragment(), "Descifrar");
        viewPager.setAdapter(adapter);
    }

}
