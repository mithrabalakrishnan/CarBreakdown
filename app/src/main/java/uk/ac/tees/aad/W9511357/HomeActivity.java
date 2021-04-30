package uk.ac.tees.aad.W9511357;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends FragmentActivity /*implements OnMapReadyCallback*/ implements  BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView mBtmView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentManager fragmentManager = getSupportFragmentManager();
        androidx.fragment.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.map2,new MapFragment());
        fragmentTransaction.commit();
       /* SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);*/


     /*   findViewById(R.id.faqbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Faq.class));
            }
        });*/
       /* findViewById(R.id.helpBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Help.class));
            }
        });*/
        mBtmView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        mBtmView.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        finish();
        finishAffinity();
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.page_1: {
                FragmentManager fragmentManager = getSupportFragmentManager();
                androidx.fragment.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.map2,new MapFragment());
                fragmentTransaction.commit();
                break;
            }
            case R.id.page_2: {
                FragmentManager fragmentManager = getSupportFragmentManager();
                androidx.fragment.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.map2,new ServiceFragment());
                fragmentTransaction.commit();
                break;
            }

            case R.id.page_4: {
                FAQFragment   faqFragment =  new FAQFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.map2, faqFragment);
                transaction.commit();
                break;
            }
        }
        return true;
    }
}
