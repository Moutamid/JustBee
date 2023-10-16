package com.moutamid.justbee.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputLayout;
import com.moutamid.justbee.models.ColonyModel;
import com.moutamid.justbee.utilis.Constants;
import com.moutamid.justbee.utilis.Types;
import com.moutamid.justbee.databinding.ActivityChangeAllBinding;

import java.util.ArrayList;
import java.util.List;

public class ChangeAllActivity extends AppCompatActivity {
    ActivityChangeAllBinding binding;
    ArrayAdapter<String> locList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeAllBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
        binding.toolbar.title.setText(Constants.ACTIVITY_NAME);

        if (Constants.types == Types.CHANGE_LOCATION_ALL) {
            locationUI();
        } else if (Constants.types == Types.CHANGE_FEED_ALL) {
            feedUI();
        } else if (Constants.types == Types.CHANGE_TREAT_ALL) {
            treatUI();
        }

        List<String> loc = Stash.getArrayList(Constants.LOCATIONS_LIST, String.class);
        locList = new ArrayAdapter<>(ChangeAllActivity.this, android.R.layout.simple_spinner_dropdown_item, loc);
        binding.locationListCurrent.setAdapter(locList);
        binding.locationListCurrentFeed.setAdapter(locList);
        binding.locationListCurrentTreat.setAdapter(locList);
        binding.locationListNew.setAdapter(locList);

        binding.changeLocation.setOnClickListener(v -> changeLocation());
        binding.changeFeed.setOnClickListener(v -> changeFeed());
        binding.changeTreat.setOnClickListener(v -> changeTreat());

    }

    private void changeTreat() {
        if (locationValid() && treatValid()) {
            String treat = getTreat();
            ArrayList<ColonyModel>  colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
            for (ColonyModel model : colonyList) {
                if (model.getLocation().equalsIgnoreCase(binding.locationCurrentTreat.getEditText().getText().toString())) {
                    model.setTreatment(treat);
                }
            }
            Stash.put(Constants.COLONY, colonyList);
            Toast.makeText(this, "Treatments Changed", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    private String getTreat() {
        String treat = "";
        for (int i = 0; i < binding.treatsChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.treatsChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                treat += chip.getText().toString() + ", ";
            }
        }
        return treat;
    }

    private void changeFeed() {
        if (locationValid() && feedValid()) {
            String feed = getFeed();
            ArrayList<ColonyModel>  colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
            for (ColonyModel model : colonyList) {
                if (model.getLocation().equalsIgnoreCase(binding.locationCurrentFeed.getEditText().getText().toString())) {
                    model.setFeed(feed);
                }
            }
            Stash.put(Constants.COLONY, colonyList);
            Toast.makeText(this, "Feed Changed", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    private String getFeed() {
        String feed = "";
        for (int i = 0; i < binding.feedChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.feedChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                feed += chip.getText().toString() + ", ";
            }
        }
        return feed;
    }


    private void changeLocation() {
        if (locationValid() && newLocationValid()) {
            ArrayList<ColonyModel>  colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
            for (ColonyModel model : colonyList) {
                if (model.getLocation().equalsIgnoreCase(binding.locationCurrent.getEditText().getText().toString())) {
                    model.setLocation(binding.locationNew.getEditText().getText().toString());
                }
            }
            Stash.put(Constants.COLONY, colonyList);
            Toast.makeText(this, "Location Changed", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    private boolean locationValid() {
        if (Constants.types == Types.CHANGE_LOCATION_ALL) {
            return checkLocation(binding.locationCurrent);
        } else if (Constants.types == Types.CHANGE_FEED_ALL) {
            return checkLocation(binding.locationCurrentFeed);
        } else if (Constants.types == Types.CHANGE_TREAT_ALL) {
            return checkLocation(binding.locationCurrentTreat);
        }
        return true;
    }

    private boolean checkLocation(TextInputLayout locationCurrent) {
        if (locationCurrent.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(this, "Current Location is empty", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            boolean pass = false;
            List<String> loc = Stash.getArrayList(Constants.LOCATIONS_LIST, String.class);
            for (String s : loc) {
                if (s.equalsIgnoreCase(locationCurrent.getEditText().getText().toString())) {
                    pass = true;
                    break;
                }
            }
            if (!pass) {
                Toast.makeText(this, "Current location not found", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private boolean feedValid() {
        boolean feed = false;
        for (int i = 0; i < binding.feedChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.feedChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                feed = true;
                break;
            }
        }
        if (!feed){
            Toast.makeText(this, "Select at least 1 feed", Toast.LENGTH_SHORT).show();
        }

        return feed;
    }

    private boolean treatValid() {
        boolean treat = false;
        for (int i = 0; i < binding.treatsChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.treatsChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                treat = true;
                break;
            }
        }

        if (!treat){
            Toast.makeText(this, "Select at least 1 treatment", Toast.LENGTH_SHORT).show();
        }

        return treat;
    }

    private boolean newLocationValid() {
        if (binding.locationNew.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(this, "New Location is empty", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            boolean pass = false;
            List<String> loc = Stash.getArrayList(Constants.LOCATIONS_LIST, String.class);
            for (String s : loc) {
                if (s.equalsIgnoreCase(binding.locationNew.getEditText().getText().toString())) {
                    pass = true;
                    break;
                }
            }
            if (!pass) {
                Toast.makeText(this, "New location not found", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void treatUI() {
        binding.treatUI.setVisibility(View.VISIBLE);
    }

    private void feedUI() {
        binding.feedUI.setVisibility(View.VISIBLE);
    }

    private void locationUI() {
        binding.locationUI.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constants.types = Types.NULL;
        finish();
    }
}