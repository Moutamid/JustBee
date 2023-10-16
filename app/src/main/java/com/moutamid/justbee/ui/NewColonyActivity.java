package com.moutamid.justbee.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.material.chip.Chip;
import com.moutamid.justbee.utilis.Constants;
import com.moutamid.justbee.databinding.ActivityNewColonyBinding;
import com.moutamid.justbee.models.ColonyModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NewColonyActivity extends AppCompatActivity {
    ActivityNewColonyBinding binding;
    ArrayList<ColonyModel> colonyList;
    ArrayAdapter<String> locList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewColonyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
        binding.toolbar.title.setText("Add New Colony");

        colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);

        List<String> loc = Stash.getArrayList(Constants.LOCATIONS_LIST, String.class);
        locList = new ArrayAdapter<>(NewColonyActivity.this, android.R.layout.simple_spinner_dropdown_item, loc);
        binding.locationList.setAdapter(locList);

        binding.add.setOnClickListener(v -> {
            ColonyModel colonyModel = getColonyData();
            colonyList.add(colonyModel);
            Stash.put(Constants.COLONY, colonyList);
            Toast.makeText(this, "Colony Added", Toast.LENGTH_SHORT).show();
            onBackPressed();
        });

    }

    private ColonyModel getColonyData() {
        ColonyModel colonyModel = new ColonyModel();

        String queenOrigin = "";
        for (int i = 0; i < binding.queenSourceChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.queenSourceChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                queenOrigin = chip.getText().toString();
            }
        }

        String colonyOrigin = "";
        for (int i = 0; i < binding.colonyOriginChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.colonyOriginChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                colonyOrigin = chip.getText().toString();
            }
        }

        String brood = "";
        for (int i = 0; i < binding.addingBroodsChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.addingBroodsChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                brood = chip.getText().toString();
            }
        }

        String treat = "";
        for (int i = 0; i < binding.treatsChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.treatsChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                treat += chip.getText().toString() + ", ";
            }
        }

        String diseases = "";
        for (int i = 0; i < binding.diseasesChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.diseasesChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                diseases += chip.getText().toString() + ", ";
            }
        }

        String feed = "";
        for (int i = 0; i < binding.feedChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.feedChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                feed += chip.getText().toString() + ", ";
            }
        }

        colonyModel.setId(UUID.randomUUID().toString());
        colonyModel.setName(binding.name.getEditText().getText().toString());
        colonyModel.setLocation(binding.location.getEditText().getText().toString());
        colonyModel.setQueenOrigin(queenOrigin);
        colonyModel.setColonyOrigin(colonyOrigin);
        colonyModel.setBrood(brood);
        colonyModel.setTreatment(treat);
        colonyModel.setPests(diseases);
        colonyModel.setFeed(feed);
        colonyModel.setColonyLoss("");
        colonyModel.setHoneyProduction(Double.parseDouble(binding.honeyProduction.getEditText().getText().toString()));
        colonyModel.setDate(new Date().getTime());

        return colonyModel;
    }
}