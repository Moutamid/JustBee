package com.moutamid.justbee.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moutamid.justbee.ui.ChangeIndividualActivity;
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
    public void onResume() {
        super.onResume();
        Constants.initDialog(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDataEntryBinding.inflate(getLayoutInflater() , container, false);


        binding.changeLocationAll.setOnClickListener(v -> {
            Constants.types = Types.CHANGE_LOCATION_ALL;
            Constants.ACTIVITY_NAME = "Change Location";
            startActivity(new Intent(requireContext(), ChangeAllActivity.class));
        });
        binding.feedAll.setOnClickListener(v -> {
            Constants.types = Types.CHANGE_FEED_ALL;
            Constants.ACTIVITY_NAME = "Feed / Medicate";
            startActivity(new Intent(requireContext(), ChangeAllActivity.class));
        });
        binding.treatmentAll.setOnClickListener(v -> {
            Constants.types = Types.CHANGE_TREAT_ALL;
            Constants.ACTIVITY_NAME = "Colony Treatment";
            startActivity(new Intent(requireContext(), ChangeAllActivity.class));
        });

        binding.changeLocation.setOnClickListener(v -> {
            Constants.types = Types.CHANGE_LOCATION;
            Constants.ACTIVITY_NAME = "Change Location";
            startActivity(new Intent(requireContext(), ChangeIndividualActivity.class));
        });

        binding.feed.setOnClickListener(v -> {
            Constants.types = Types.CHANGE_FEED;
            Constants.ACTIVITY_NAME = "Feed / Medicate";
            startActivity(new Intent(requireContext(), ChangeIndividualActivity.class));
        });

        binding.pests.setOnClickListener(v -> {
            Constants.types = Types.CHANGE_PEST;
            Constants.ACTIVITY_NAME = "Diseases / Pests";
            startActivity(new Intent(requireContext(), ChangeIndividualActivity.class));
        });

        binding.newQueen.setOnClickListener(v -> {
            Constants.types = Types.CHANGE_NEW_QUEEN;
            Constants.ACTIVITY_NAME = "New Queen";
            startActivity(new Intent(requireContext(), ChangeIndividualActivity.class));
        });

        binding.treatment.setOnClickListener(v -> {
            Constants.types = Types.CHANGE_TREAT;
            Constants.ACTIVITY_NAME = "Colony Treatment";
            startActivity(new Intent(requireContext(), ChangeIndividualActivity.class));
        });

        binding.broodAdd.setOnClickListener(v -> {
            Constants.types = Types.CHANGE_BROOD_ADD;
            Constants.ACTIVITY_NAME = "Brood Add";
            startActivity(new Intent(requireContext(), ChangeIndividualActivity.class));
        });

        binding.broodRemove.setOnClickListener(v -> {
            Constants.types = Types.CHANGE_BROOD_REMOVE;
            Constants.ACTIVITY_NAME = "Brood Remove";
            startActivity(new Intent(requireContext(), ChangeIndividualActivity.class));
        });

        binding.production.setOnClickListener(v -> {
            Constants.types = Types.CHANGE_HONEY_PRODUCTION;
            Constants.ACTIVITY_NAME = "Honey Production";
            startActivity(new Intent(requireContext(), ChangeIndividualActivity.class));
        });
        binding.colonyAdd.setOnClickListener(v -> {
            Constants.types = Types.CHANGE_COLONY_ADD;
            Constants.ACTIVITY_NAME = "Colony Add";
            startActivity(new Intent(requireContext(), ChangeIndividualActivity.class));
        });

        binding.colonyRemove.setOnClickListener(v -> {
            Constants.types = Types.CHANGE_COLONY_REMOVE;
            Constants.ACTIVITY_NAME = "Colony Remove";
            startActivity(new Intent(requireContext(), ChangeIndividualActivity.class));
        });

        return binding.getRoot();
    }
}