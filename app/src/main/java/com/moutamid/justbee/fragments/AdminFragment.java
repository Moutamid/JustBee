package com.moutamid.justbee.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fxn.stash.Stash;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.moutamid.justbee.Constants;
import com.moutamid.justbee.R;
import com.moutamid.justbee.admin_ui.QueenSourceActivity;
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

        binding.queenSource.setOnClickListener(v -> {
            int vis = binding.queenSourceChipGroup.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            binding.queenSourceChipGroup.setVisibility(vis);
        });

        binding.feed.setOnClickListener(v -> {
            int vis = binding.feedList.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            binding.feedList.setVisibility(vis);
        });

        binding.colonyLoss.setOnClickListener(v -> {
            int vis = binding.loss.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            binding.loss.setVisibility(vis);
        });

        binding.treatments.setOnClickListener(v -> {
            int vis = binding.treat.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            binding.treat.setVisibility(vis);
        });

        loss = new ArrayList<>();
        String[] stringArray = getResources().getStringArray(R.array.my_loss_array);
        loss.addAll(Arrays.asList(stringArray));
        lossList = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, loss);
        binding.addContext.setAdapter(lossList);

        binding.queenSourceChipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            for (int i = 0; i < binding.queenSourceChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) binding.queenSourceChipGroup.getChildAt(i);
                if (chip.isChecked()){
                    Stash.put(Constants.QUEEN_SOURCE, chip.getText().toString());
                }
            }
        });

        binding.feedChipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            for (int i = 0; i < binding.feedChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) binding.feedChipGroup.getChildAt(i);
                if (chip.isChecked()) {
                //    Stash.put(Constants.QUEEN_SOURCE, chip.getText().toString());
                }
            }
        });

        binding.treatsChipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            for (int i = 0; i < binding.treatsChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) binding.treatsChipGroup.getChildAt(i);
                if (chip.isChecked()) {
                  //  Stash.put(Constants.QUEEN_SOURCE, chip.getText().toString());
                }
            }
        });

        return binding.getRoot();

    }
}