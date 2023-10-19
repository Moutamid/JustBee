package com.moutamid.justbee.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.material.chip.Chip;
import com.moutamid.justbee.utilis.Constants;
import com.moutamid.justbee.R;
import com.moutamid.justbee.ui.AddColonyActivity;
import com.moutamid.justbee.ui.ManageLocationsActivity;
import com.moutamid.justbee.databinding.FragmentAdminBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AdminFragment extends Fragment {
    FragmentAdminBinding binding;
    ArrayAdapter<String> lossList;
    List<String> loss;

    public AdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminBinding.inflate(getLayoutInflater(), container, false);

        binding.location.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), ManageLocationsActivity.class));
        });
        binding.colony.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), AddColonyActivity.class));
        });

        return binding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        Constants.initDialog(requireContext());
    }
}