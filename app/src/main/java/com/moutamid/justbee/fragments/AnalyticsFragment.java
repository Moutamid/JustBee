package com.moutamid.justbee.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moutamid.justbee.R;
import com.moutamid.justbee.databinding.FragmentAnalyticsBinding;

public class AnalyticsFragment extends Fragment {
    FragmentAnalyticsBinding binding;
    public AnalyticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAnalyticsBinding.inflate(getLayoutInflater(), container, false);

        return binding.getRoot();
    }
}