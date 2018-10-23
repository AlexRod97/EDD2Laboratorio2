package com.e.edd2laboratorio2;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.e.edd2laboratorio2.Fragments.CypherFragment;
import com.e.edd2laboratorio2.Fragments.DecypherFragment;
import com.e.edd2laboratorio2.Fragments.RsaCypherFragment;
import com.e.edd2laboratorio2.Fragments.RsaDecypherFragment;
import com.e.edd2laboratorio2.Fragments.SectionsPageAdapter;

public class Rsa extends AppCompatActivity {

    private static final String TAG = "RSA";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa);
        Log.d(TAG, "onCreate: Starting.");

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new RsaCypherFragment(), "Cifrar");
        adapter.addFragment(new RsaDecypherFragment(), "Descifrar");
        viewPager.setAdapter(adapter);
    }

}
