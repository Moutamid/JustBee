package com.moutamid.justbee.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.material.chip.Chip;
import com.moutamid.justbee.R;
import com.moutamid.justbee.databinding.ActivityChangeIndividualBinding;
import com.moutamid.justbee.models.ColonyModel;
import com.moutamid.justbee.utilis.Constants;
import com.moutamid.justbee.utilis.Types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChangeIndividualActivity extends AppCompatActivity {
    ActivityChangeIndividualBinding binding;
    ArrayAdapter<String> locList;
    ArrayAdapter<String> idList;
    ArrayAdapter<String> lossList;
    List<String> loss;
    String selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeIndividualBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
        binding.toolbar.title.setText(Constants.ACTIVITY_NAME);

        List<String> loc = Stash.getArrayList(Constants.LOCATIONS_LIST, String.class);
        ArrayList<ColonyModel> colonies = Stash.getArrayList(Constants.COLONY, ColonyModel.class);

        List<String> colonyID = new ArrayList<>();
        for (ColonyModel model : colonies) {
            colonyID.add(model.getId());
        }
        locList = new ArrayAdapter<>(ChangeIndividualActivity.this, android.R.layout.simple_spinner_dropdown_item, loc);
        idList = new ArrayAdapter<>(ChangeIndividualActivity.this, android.R.layout.simple_spinner_dropdown_item, colonyID);
        binding.locationList.setAdapter(locList);
        binding.colonyIdLIst.setAdapter(idList);

        loss = new ArrayList<>();
        String[] stringArray = getResources().getStringArray(R.array.my_loss_array);
        loss.addAll(Arrays.asList(stringArray));
        lossList = new ArrayAdapter<>(ChangeIndividualActivity.this, android.R.layout.simple_spinner_dropdown_item, loss);
        binding.lossList.setAdapter(lossList);

        updateUI();

        binding.colonyIdLIst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedID = (String) parent.getItemAtPosition(position);
                updateUI(selectedID);
            }
        });

        binding.change.setOnClickListener(v -> {
            if (Constants.types == Types.CHANGE_LOCATION) {
                ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
                for (ColonyModel model : colonyList) {
                    if (model.getId().equals(selectedID)) {
                        model.setLocation(binding.location.getEditText().getText().toString());
                    }
                }
                Stash.put(Constants.COLONY, colonyList);
                Toast.makeText(this, "Location Changed", Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else if (Constants.types == Types.CHANGE_FEED) {
                String feed = "";
                for (int i = 0; i < binding.feedChipGroup.getChildCount(); i++) {
                    Chip chip = (Chip) binding.feedChipGroup.getChildAt(i);
                    if (chip.isChecked()) {
                        feed += chip.getText().toString() + ", ";
                    }
                }
                ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
                for (ColonyModel model : colonyList) {
                    if (model.getId().equals(selectedID)) {
                        model.setFeed(feed);
                    }
                }
                Stash.put(Constants.COLONY, colonyList);
                Toast.makeText(this, "Feed Changed", Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else if (Constants.types == Types.CHANGE_PEST) {
                String diseases = "";
                for (int i = 0; i < binding.diseasesChipGroup.getChildCount(); i++) {
                    Chip chip = (Chip) binding.diseasesChipGroup.getChildAt(i);
                    if (chip.isChecked()) {
                        diseases += chip.getText().toString() + ", ";
                    }
                }

                ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
                for (ColonyModel model : colonyList) {
                    if (model.getId().equals(selectedID)) {
                        model.setPests(diseases);
                    }
                }
                Stash.put(Constants.COLONY, colonyList);
                Toast.makeText(this, "Pest Changed", Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else if (Constants.types == Types.CHANGE_TREAT) {
                String treat = "";
                for (int i = 0; i < binding.treatsChipGroup.getChildCount(); i++) {
                    Chip chip = (Chip) binding.treatsChipGroup.getChildAt(i);
                    if (chip.isChecked()) {
                        treat += chip.getText().toString() + ", ";
                    }
                }

                ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
                for (ColonyModel model : colonyList) {
                    if (model.getId().equals(selectedID)) {
                        model.setTreatment(treat);
                    }
                }
                Stash.put(Constants.COLONY, colonyList);
                Toast.makeText(this, "Treatment Changed", Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else if (Constants.types == Types.CHANGE_NEW_QUEEN) {
                String queenOrigin = "";
                for (int i = 0; i < binding.queenSourceChipGroup.getChildCount(); i++) {
                    Chip chip = (Chip) binding.queenSourceChipGroup.getChildAt(i);
                    if (chip.isChecked()) {
                        queenOrigin = chip.getText().toString();
                    }
                }

                ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
                for (ColonyModel model : colonyList) {
                    if (model.getId().equals(selectedID)) {
                        model.setQueenOrigin(queenOrigin);
                    }
                }
                Stash.put(Constants.COLONY, colonyList);
                Toast.makeText(this, "Queen Origin Changed", Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else if (Constants.types == Types.CHANGE_BROOD_ADD) {
                String brood = "";
                for (int i = 0; i < binding.addingBroodsChipGroup.getChildCount(); i++) {
                    Chip chip = (Chip) binding.addingBroodsChipGroup.getChildAt(i);
                    if (chip.isChecked()) {
                        brood = chip.getText().toString();
                    }
                }

                ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
                for (ColonyModel model : colonyList) {
                    if (model.getId().equals(selectedID)) {
                        model.setBrood(brood);
                    }
                }
                Stash.put(Constants.COLONY, colonyList);
                Toast.makeText(this, "Brood Changed", Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else if (Constants.types == Types.CHANGE_BROOD_REMOVE) {
                String brood = "";
                for (int i = 0; i < binding.addingBroodsChipGroup.getChildCount(); i++) {
                    Chip chip = (Chip) binding.addingBroodsChipGroup.getChildAt(i);
                    if (chip.isChecked()) {
                        brood = chip.getText().toString();
                    }
                }

                ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
                for (ColonyModel model : colonyList) {
                    if (model.getId().equals(selectedID)) {
                        model.setBrood(brood);
                    }
                }
                Stash.put(Constants.COLONY, colonyList);
                Toast.makeText(this, "Brood Changed", Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else if (Constants.types == Types.CHANGE_HONEY_PRODUCTION) {
                ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
                for (ColonyModel model : colonyList) {
                    if (model.getId().equals(selectedID)) {
                        model.setHoneyProduction(Double.parseDouble(binding.honeyProduction.getEditText().getText().toString()));
                    }
                }
                Stash.put(Constants.COLONY, colonyList);
                Toast.makeText(this, "Honey Production Changed", Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else if (Constants.types == Types.CHANGE_COLONY_ADD) {

                String colonyOrigin = "";
                for (int i = 0; i < binding.colonyOriginChipGroup.getChildCount(); i++) {
                    Chip chip = (Chip) binding.colonyOriginChipGroup.getChildAt(i);
                    if (chip.isChecked()) {
                        colonyOrigin = chip.getText().toString();
                    }
                }

                ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
                for (ColonyModel model : colonyList) {
                    if (model.getId().equals(selectedID)) {
                        model.setColonyOrigin(colonyOrigin);
                    }
                }
                Stash.put(Constants.COLONY, colonyList);
                Toast.makeText(this, "Colony Origin Changed", Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else if (Constants.types == Types.CHANGE_COLONY_REMOVE) {
                ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
                for (ColonyModel model : colonyList) {
                    if (model.getId().equals(selectedID)) {
                        model.setColonyLoss(binding.colonyLoss.getEditText().getText().toString());
                    }
                }
                Stash.put(Constants.COLONY, colonyList);
                Toast.makeText(this, "Colony loss Changed", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

    }

    private void updateUI(String selectedID) {

        ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
        ColonyModel colonyModel = null;

        for (ColonyModel colony : colonyList) {
            if (colony.getId().equals(selectedID)) {
                colonyModel = colony;
                break;
            }
        }

        if (colonyModel != null) {
            if (Constants.types == Types.CHANGE_LOCATION) {
                binding.location.getEditText().setText(colonyModel.getLocation());
            } else if (Constants.types == Types.CHANGE_FEED) {
                String[] feedList = colonyModel.getFeed().split(", ");
                for (String f : feedList) {
                    for (int i = 0; i < binding.feedChipGroup.getChildCount(); i++) {
                        Chip chip = (Chip) binding.feedChipGroup.getChildAt(i);
                        if (f.equals(chip.getText().toString())) {
                            chip.setChecked(true);
                        }
                    }
                }
            } else if (Constants.types == Types.CHANGE_PEST) {
                String[] diseases = colonyModel.getPests().split(", ");
                for (String f : diseases) {
                    for (int i = 0; i < binding.diseasesChipGroup.getChildCount(); i++) {
                        Chip chip = (Chip) binding.diseasesChipGroup.getChildAt(i);
                        if (f.equals(chip.getText().toString())) {
                            chip.setChecked(true);
                        }
                    }
                }
            } else if (Constants.types == Types.CHANGE_TREAT) {
                String[] treat = colonyModel.getTreatment().split(", ");
                for (String f : treat) {
                    for (int i = 0; i < binding.treatsChipGroup.getChildCount(); i++) {
                        Chip chip = (Chip) binding.treatsChipGroup.getChildAt(i);
                        if (f.equals(chip.getText().toString())) {
                            chip.setChecked(true);
                        }
                    }
                }
            } else if (Constants.types == Types.CHANGE_NEW_QUEEN) {
                String queen = colonyModel.getQueenOrigin();
                for (int i = 0; i < binding.queenSourceChipGroup.getChildCount(); i++) {
                    Chip chip = (Chip) binding.queenSourceChipGroup.getChildAt(i);
                    if (queen.equals(chip.getText().toString())) {
                        chip.setChecked(true);
                    }
                }
            } else if (Constants.types == Types.CHANGE_BROOD_ADD) {
                String brood = colonyModel.getBrood();
                for (int i = 0; i < binding.addingBroodsChipGroup.getChildCount(); i++) {
                    Chip chip = (Chip) binding.addingBroodsChipGroup.getChildAt(i);
                    if (brood.equals(chip.getText().toString())) {
                        chip.setChecked(true);
                    }
                }
            } else if (Constants.types == Types.CHANGE_BROOD_REMOVE) {
                String brood = colonyModel.getBrood();
                for (int i = 0; i < binding.removingBroodsChipGroup.getChildCount(); i++) {
                    Chip chip = (Chip) binding.removingBroodsChipGroup.getChildAt(i);
                    if (brood.equals(chip.getText().toString())) {
                        chip.setChecked(true);
                    }
                }
            } else if (Constants.types == Types.CHANGE_HONEY_PRODUCTION) {
                binding.honeyProduction.getEditText().setText(colonyModel.getHoneyProduction() + "");
            } else if (Constants.types == Types.CHANGE_COLONY_ADD) {
                String origin = colonyModel.getColonyOrigin();
                for (int i = 0; i < binding.colonyOriginChipGroup.getChildCount(); i++) {
                    Chip chip = (Chip) binding.colonyOriginChipGroup.getChildAt(i);
                    if (origin.equals(chip.getText().toString())) {
                        chip.setChecked(true);
                    }
                }
            } else if (Constants.types == Types.CHANGE_COLONY_REMOVE) {
                binding.colonyLoss.getEditText().setText(colonyModel.getColonyLoss());
            }
        }
    }

    private void updateUI() {
        if (Constants.types == Types.CHANGE_LOCATION) {
            binding.header.setText("Change individual colony location");
            binding.location.setVisibility(View.VISIBLE);
        } else if (Constants.types == Types.CHANGE_FEED) {
            binding.header.setText("Change individual colony Feed");
            binding.feedUI.setVisibility(View.VISIBLE);
        } else if (Constants.types == Types.CHANGE_PEST) {
            binding.header.setText("Change individual colony Pest");
            binding.pestUI.setVisibility(View.VISIBLE);
        } else if (Constants.types == Types.CHANGE_TREAT) {
            binding.header.setText("Change individual colony Treatment");
            binding.treatUI.setVisibility(View.VISIBLE);
        } else if (Constants.types == Types.CHANGE_NEW_QUEEN) {
            binding.header.setText("Change individual colony Queen Origin");
            binding.queenUI.setVisibility(View.VISIBLE);
        } else if (Constants.types == Types.CHANGE_BROOD_ADD) {
            binding.header.setText("Add Brood into individual colony");
            binding.broodAddUI.setVisibility(View.VISIBLE);
        } else if (Constants.types == Types.CHANGE_BROOD_REMOVE) {
            binding.header.setText("Remove Brood from individual colony");
            binding.broodRemoveUI.setVisibility(View.VISIBLE);
        } else if (Constants.types == Types.CHANGE_HONEY_PRODUCTION) {
            binding.header.setText("Change Honey Production for individual colony:");
            binding.productionUI.setVisibility(View.VISIBLE);
        } else if (Constants.types == Types.CHANGE_COLONY_ADD) {
            binding.header.setText("Change individual colony origin:");
            binding.colonyOriginUI.setVisibility(View.VISIBLE);
        } else if (Constants.types == Types.CHANGE_COLONY_REMOVE) {
            binding.header.setText("Change individual colony loss:");
            binding.colonyLossUI.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constants.types = Types.NULL;
    }
}