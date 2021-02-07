package com.gzeinnumer.mylibsavedinstancestateexample.activity;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gzeinnumer.mylibsavedinstancestate.StateUI;
import com.gzeinnumer.mylibsavedinstancestate.StateUIBuilder;
import com.gzeinnumer.mylibsavedinstancestateexample.MenuActivity;
import com.gzeinnumer.mylibsavedinstancestateexample.databinding.ActivityMainBinding;
import com.gzeinnumer.mylibsavedinstancestateexample.utils.CustomToastDown;
import com.gzeinnumer.mylibsavedinstancestateexample.utils.CustomToastUp;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "State_UI";

    private StateUI stateUI;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        stateUI = StateUIBuilder.Build(MainActivity.class, getApplicationContext());

        binding.btnClearBack.setOnClickListener(v -> {
            stateUI.clearState();
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
        });

        binding.btnSaveBack.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            finish();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        new CustomToastUp(getApplicationContext(), "Activity_onPause\nData Save To State");

        stateUI.addView("binding.edUsername", binding.edUsername.getText().toString());
        stateUI.addView("binding.edPass", binding.edPass.getText().toString());
        stateUI.saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (stateUI.getState()) {
            new CustomToastDown(getApplicationContext(), "Activity_onResume\nData Loaded From State");

            String userName = stateUI.getValue("binding.edUsername");
            binding.edUsername.setText(userName);
            String pass = stateUI.getValue("binding.edPass");
            binding.edPass.setText(pass);
        }
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        stateUI.clearState();
//    }
}