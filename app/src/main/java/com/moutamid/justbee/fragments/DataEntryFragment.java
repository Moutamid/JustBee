package com.moutamid.justbee.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moutamid.justbee.utilis.Constants;
import com.moutamid.justbee.utilis.Types;
import com.moutamid.justbee.databinding.FragmentDataEntryBinding;
import com.moutamid.justbee.ui.ChangeAllActivity;

public class DataEntryFragment extends Fragment {
    FragmentDataEntryBinding binding;
    public DataEntryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDataEntryBinding.inflate(getLayoutInflater() , container, false);


        binding.changeLocationAll.setOnClickListener(v -> {
            Constants.types = Types.CHANGE_LOCATION_ALL;
            startActivity(new Intent(requireContext(), ChangeAllActivity.class));
        });
        binding.feedAll.setOnClickListener(v -> {
            Constants.types = Types.CHANGE_FEED_ALL;
            startActivity(new Intent(requireContext(), ChangeAllActivity.class));
        });
        binding.treatmentAll.setOnClickListener(v -> {
            Constants.types = Types.CHANGE_TREAT_ALL;
            startActivity(new Intent(requireContext(), ChangeAllActivity.class));
        });

        return binding.getRoot();
    }
}