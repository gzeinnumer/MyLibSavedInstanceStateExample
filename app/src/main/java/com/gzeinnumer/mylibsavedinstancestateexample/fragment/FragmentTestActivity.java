package com.gzeinnumer.mylibsavedinstancestateexample.fragment;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gzeinnumer.mylibsavedinstancestateexample.R;

public class FragmentTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new HomeFragment();
        fragmentTransaction.replace(R.id.fr_2, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}