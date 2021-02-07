package com.gzeinnumer.mylibsavedinstancestateexample.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gzeinnumer.mylibsavedinstancestate.StateUI;
import com.gzeinnumer.mylibsavedinstancestate.StateUIBuilder;
import com.gzeinnumer.mylibsavedinstancestateexample.MenuActivity;
import com.gzeinnumer.mylibsavedinstancestateexample.databinding.FragmentHomeBinding;
import com.gzeinnumer.mylibsavedinstancestateexample.utils.CustomToastDown;
import com.gzeinnumer.mylibsavedinstancestateexample.utils.CustomToastUp;

public class HomeFragment extends Fragment {
    public static final String TAG = "State_UI";

    private FragmentHomeBinding binding;

    private StateUI stateUI;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stateUI = StateUIBuilder.Build(HomeFragment.class, requireContext());

        binding.btnClearBack.setOnClickListener(v -> {
            stateUI.clearState();
            startActivity(new Intent(requireContext(), MenuActivity.class));
        });

        binding.btnSaveBack.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), MenuActivity.class));
            requireActivity().finish();
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        new CustomToastUp(requireContext(), "Fragment_onPause\nData Save To State");

        stateUI.addView("binding.edUsername", binding.edUsername.getText().toString());
        stateUI.addView("binding.edPass", binding.edPass.getText().toString());
        stateUI.saveState();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (stateUI.getState()) {
            new CustomToastDown(requireContext(), "Fragment_onResume\nData Loaded From State");

            String userName = stateUI.getValue("binding.edUsername");
            binding.edUsername.setText(userName);
            String pass = stateUI.getValue("binding.edPass");
            binding.edPass.setText(pass);
        }
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        stateUI.clearState();
//    }
}