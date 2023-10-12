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

        binding.queenSource.setOnClickListener(v -> {
            int vis = binding.queenSourceChipGroup.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            binding.queenSourceChipGroup.setVisibility(vis);
        });

        binding.feed.setOnClickListener(v -> {
            int vis = binding.feedList.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            binding.feedList.setVisibility(vis);
        });


        binding.location.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), ManageLocationsActivity.class));
        });
        binding.colony.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), AddColonyActivity.class));
        });

        binding.colonyLoss.setOnClickListener(v -> {
            int vis = binding.loss.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            binding.loss.setVisibility(vis);
        });

        binding.treatments.setOnClickListener(v -> {
            int vis = binding.treat.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            binding.treat.setVisibility(vis);
        });

        binding.pests.setOnClickListener(v -> {
            int vis = binding.diseases.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            binding.diseases.setVisibility(vis);
        });
        binding.addColony.setOnClickListener(v -> {
            int vis = binding.manageColony.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            binding.manageColony.setVisibility(vis);
        });

        loss = new ArrayList<>();
        String[] stringArray = getResources().getStringArray(R.array.my_loss_array);
        loss.addAll(Arrays.asList(stringArray));
        lossList = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, loss);
        binding.addContext.setAdapter(lossList);

        binding.save.setOnClickListener(v -> {
            if (binding.context.getEditText().getText().toString().isEmpty()){
                binding.context.setErrorEnabled(true);
                binding.context.setError("Data is empty");
            } else {
                Stash.put(Constants.COLONY_LOSS, binding.context.getEditText().getText().toString());
                Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        });

        binding.queenSourceChipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            for (int i = 0; i < binding.queenSourceChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) binding.queenSourceChipGroup.getChildAt(i);
                if (chip.isChecked()) {
                    Stash.put(Constants.QUEEN_SOURCE, chip.getText().toString());
                }
            }
        });
        binding.removingBroodsChipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            for (int i = 0; i < binding.removingBroodsChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) binding.removingBroodsChipGroup.getChildAt(i);
                if (chip.isChecked()) {
                    Stash.put(Constants.BROOD_REMOVE, chip.getText().toString());
                }
            }
        });
        binding.addingBroodsChipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            for (int i = 0; i < binding.addingBroodsChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) binding.addingBroodsChipGroup.getChildAt(i);
                if (chip.isChecked()) {
                    Stash.put(Constants.BROOD_ADD, chip.getText().toString());
                }
            }
        });

        binding.feedChipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            String feed = "";
            for (int i = 0; i < binding.feedChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) binding.feedChipGroup.getChildAt(i);
                if (chip.isChecked()) {
                    feed += chip.getText().toString() + ", ";
                    Stash.put(Constants.FEED_LIST, feed);
                }
            }
        });

        binding.treatsChipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            String treat = "";
            for (int i = 0; i < binding.treatsChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) binding.treatsChipGroup.getChildAt(i);
                if (chip.isChecked()) {
                    treat += chip.getText().toString() + ", ";
                    Stash.put(Constants.TREAT_LIST, treat);
                }
            }
        });

        binding.diseasesChipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            String diseases = "";
            for (int i = 0; i < binding.diseasesChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) binding.diseasesChipGroup.getChildAt(i);
                if (chip.isChecked()) {
                    diseases += chip.getText().toString() + ", ";
                    Stash.put(Constants.DISEASES_LIST, diseases);
                }
            }
        });

        return binding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        String queen = Stash.getString(Constants.QUEEN_SOURCE, "");
        for (int i = 0; i < binding.queenSourceChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.queenSourceChipGroup.getChildAt(i);
            if (queen.equals(chip.getText().toString())) {
                chip.setChecked(true);
            }
        }

        String remove_brood = Stash.getString(Constants.BROOD_REMOVE, "");
        for (int i = 0; i < binding.removingBroodsChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.removingBroodsChipGroup.getChildAt(i);
            if (remove_brood.equals(chip.getText().toString())) {
                chip.setChecked(true);
            }
        }

        String add_brood = Stash.getString(Constants.BROOD_ADD, "");
        for (int i = 0; i < binding.addingBroodsChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.addingBroodsChipGroup.getChildAt(i);
            if (add_brood.equals(chip.getText().toString())) {
                chip.setChecked(true);
            }
        }

        String[] feed = Stash.getString(Constants.FEED_LIST, "").split(", ");
        for (String f : feed) {
            for (int i = 0; i < binding.feedChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) binding.feedChipGroup.getChildAt(i);
                if (f.equals(chip.getText().toString())) {
                    chip.setChecked(true);
                }
            }
        }

        String[] treat = Stash.getString(Constants.TREAT_LIST, "").split(", ");
        for (String f : treat) {
            for (int i = 0; i < binding.treatsChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) binding.treatsChipGroup.getChildAt(i);
                if (f.equals(chip.getText().toString())) {
                    chip.setChecked(true);
                }
            }
        }

        String[] diseases = Stash.getString(Constants.DISEASES_LIST, "").split(", ");
        for (String f : diseases) {
            for (int i = 0; i < binding.diseasesChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) binding.diseasesChipGroup.getChildAt(i);
                if (f.equals(chip.getText().toString())) {
                    chip.setChecked(true);
                }
            }
        }

        binding.context.getEditText().setText(Stash.getString(Constants.COLONY_LOSS, ""));

    }
}