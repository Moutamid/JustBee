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
import com.moutamid.justbee.models.HistoryModel;
import com.moutamid.justbee.models.LocationModel;
import com.moutamid.justbee.utilis.Constants;
import com.moutamid.justbee.utilis.Types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Constants.initDialog(this);

        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
        binding.toolbar.title.setText(Constants.ACTIVITY_NAME);

        List<LocationModel> loc = Stash.getArrayList(Constants.LOCATIONS_LIST, LocationModel.class);
        List<String> locc = new ArrayList<>();

        for (LocationModel l : loc){
            locc.add(l.getName());
        }
        ArrayList<ColonyModel> colonies = Stash.getArrayList(Constants.COLONY, ColonyModel.class);

        List<String> colonyID = new ArrayList<>();
        for (ColonyModel model : colonies) {
            colonyID.add(model.getName());
        }
        locList = new ArrayAdapter<>(ChangeIndividualActivity.this, android.R.layout.simple_spinner_dropdown_item, locc);
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
                updateLocation();
            } else if (Constants.types == Types.CHANGE_FEED) {
                updateFeed();
            } else if (Constants.types == Types.CHANGE_PEST) {
                updatePest();
            } else if (Constants.types == Types.CHANGE_TREAT) {
                updateTreat();
            } else if (Constants.types == Types.CHANGE_NEW_QUEEN) {
                updateQueenOrigin();
            } else if (Constants.types == Types.CHANGE_BROOD_ADD) {
                updateBrood();
            } else if (Constants.types == Types.CHANGE_BROOD_REMOVE) {
                updateBrood();
            } else if (Constants.types == Types.CHANGE_HONEY_PRODUCTION) {
                updateProduction();
            } else if (Constants.types == Types.CHANGE_COLONY_ADD) {
                updateColonyOrigin();
            } else if (Constants.types == Types.CHANGE_COLONY_REMOVE) {
                updateLoss();
            }
        });

    }
    private void updatePest() {
        String diseases = "";
        for (int i = 0; i < binding.diseasesChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.diseasesChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                diseases += chip.getText().toString() + ", ";
            }
        }

        Constants.showDialog();
        Map<String, Object> map = new HashMap<>();
        map.put("pests", diseases);
        String finalFeed = diseases;
        Constants.databaseReference().child(Constants.COLONY)
                .child(selectedID).updateChildren(map)
                .addOnFailureListener(e -> {
                    Constants.dismissDialog();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }).addOnSuccessListener(unused -> {
                    String time = Constants.getFormattedDate(new Date().getTime());
                    String event = "Change pest to " + finalFeed;
                    HistoryModel history = new HistoryModel(selectedID, time, event);
                    Constants.databaseReference().child(Constants.ColonyAnalysis).child(selectedID).push().setValue(history);
                    Constants.dismissDialog();
                    ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
                    for (ColonyModel model : colonyList) {
                        if (model.getName().equals(selectedID)) {
                            model.setPests(finalFeed);
                        }
                    }
                    Stash.put(Constants.COLONY, colonyList);
                    Toast.makeText(this, "Pest Changed", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                });
    }
    private void updateTreat() {

        String treat = "";
        for (int i = 0; i < binding.treatsChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.treatsChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                treat += chip.getText().toString() + ", ";
            }
        }

        Constants.showDialog();
        Map<String, Object> map = new HashMap<>();
        map.put("treatment", treat);
        String finalFeed = treat;
        Constants.databaseReference().child(Constants.COLONY)
                .child(selectedID).updateChildren(map)
                .addOnFailureListener(e -> {
                    Constants.dismissDialog();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }).addOnSuccessListener(unused -> {
                    Constants.dismissDialog();

                    String time = Constants.getFormattedDate(new Date().getTime());
                    String event = "Change treatment to " + finalFeed;
                    HistoryModel history = new HistoryModel(selectedID, time, event);
                    Constants.databaseReference().child(Constants.ColonyAnalysis).child(selectedID).push().setValue(history);

                    ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
                    for (ColonyModel model : colonyList) {
                        if (model.getName().equals(selectedID)) {
                            model.setTreatment(finalFeed);
                        }
                    }
                    Stash.put(Constants.COLONY, colonyList);
                    Toast.makeText(this, "Treatment Changed", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                });
    }
    private void updateQueenOrigin() {

        String queenOrigin = "";
        for (int i = 0; i < binding.queenSourceChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.queenSourceChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                queenOrigin = chip.getText().toString();
            }
        }

        Constants.showDialog();
        Map<String, Object> map = new HashMap<>();
        map.put("queenOrigin", queenOrigin);
        String finalFeed = queenOrigin;
        Constants.databaseReference().child(Constants.COLONY)
                .child(selectedID).updateChildren(map)
                .addOnFailureListener(e -> {
                    Constants.dismissDialog();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }).addOnSuccessListener(unused -> {
                    Constants.dismissDialog();

                    String time = Constants.getFormattedDate(new Date().getTime());
                    String event = "Change Queen Origin to " + finalFeed;
                    HistoryModel history = new HistoryModel(selectedID, time, event);
                    Constants.databaseReference().child(Constants.ColonyAnalysis).child(selectedID).push().setValue(history);

                    ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
                    for (ColonyModel model : colonyList) {
                        if (model.getName().equals(selectedID)) {
                            model.setQueenOrigin(finalFeed);
                        }
                    }
                    Stash.put(Constants.COLONY, colonyList);
                    Toast.makeText(this, "Queen Origin Changed", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                });
    }
    private void updateBrood() {

        String broodAdd = "";
        String broodRemove = "";
        String brood = "";
        for (int i = 0; i < binding.addingBroodsChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.addingBroodsChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                broodAdd = chip.getText().toString();
            }
        }
        for (int i = 0; i < binding.removingBroodsChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.removingBroodsChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                broodRemove = chip.getText().toString();
            }
        }
        brood = broodAdd.isEmpty() ? broodRemove : broodAdd;
        Constants.showDialog();
        Map<String, Object> map = new HashMap<>();
        map.put("brood", brood);
        String finalFeed = brood;
        Constants.databaseReference().child(Constants.COLONY)
                .child(selectedID).updateChildren(map)
                .addOnFailureListener(e -> {
                    Constants.dismissDialog();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }).addOnSuccessListener(unused -> {
                    Constants.dismissDialog();

                    String time = Constants.getFormattedDate(new Date().getTime());
                    String event = "Change Brood to " + finalFeed;
                    HistoryModel history = new HistoryModel(selectedID, time, event);
                    Constants.databaseReference().child(Constants.ColonyAnalysis).child(selectedID).push().setValue(history);

                    ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
                    for (ColonyModel model : colonyList) {
                        if (model.getName().equals(selectedID)) {
                            model.setBrood(finalFeed);
                        }
                    }
                    Stash.put(Constants.COLONY, colonyList);
                    Toast.makeText(this, "Brood Changed", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                });
    }

    private void updateProduction() {
        double production = Double.parseDouble(binding.honeyProduction.getEditText().getText().toString());
        Constants.showDialog();
        Map<String, Object> map = new HashMap<>();
        map.put("honeyProduction", production);
        double finalFeed = production;
        Constants.databaseReference().child(Constants.COLONY)
                .child(selectedID).updateChildren(map)
                .addOnFailureListener(e -> {
                    Constants.dismissDialog();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }).addOnSuccessListener(unused -> {
                    Constants.dismissDialog();

                    String time = Constants.getFormattedDate(new Date().getTime());
                    String event = "Change Honey Production to " + finalFeed;
                    HistoryModel history = new HistoryModel(selectedID, time, event);
                    Constants.databaseReference().child(Constants.ColonyAnalysis).child(selectedID).push().setValue(history);

                    ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
                    for (ColonyModel model : colonyList) {
                        if (model.getName().equals(selectedID)) {
                            model.setHoneyProduction(finalFeed);
                        }
                    }
                    Stash.put(Constants.COLONY, colonyList);
                    Toast.makeText(this, "Honey Production Changed", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                });
    }

    private void updateColonyOrigin() {

        String colonyOrigin = "";
        for (int i = 0; i < binding.colonyOriginChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.colonyOriginChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                colonyOrigin = chip.getText().toString();
            }
        }

        Constants.showDialog();
        Map<String, Object> map = new HashMap<>();
        map.put("colonyOrigin", colonyOrigin);
        String finalFeed = colonyOrigin;
        Constants.databaseReference().child(Constants.COLONY)
                .child(selectedID).updateChildren(map)
                .addOnFailureListener(e -> {
                    Constants.dismissDialog();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }).addOnSuccessListener(unused -> {
                    Constants.dismissDialog();

                    String time = Constants.getFormattedDate(new Date().getTime());
                    String event = "Change Colony Origin to " + finalFeed;
                    HistoryModel history = new HistoryModel(selectedID, time, event);
                    Constants.databaseReference().child(Constants.ColonyAnalysis).child(selectedID).push().setValue(history);

                    ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
                    for (ColonyModel model : colonyList) {
                        if (model.getName().equals(selectedID)) {
                            model.setColonyOrigin(finalFeed);
                        }
                    }
                    Stash.put(Constants.COLONY, colonyList);
                    Toast.makeText(this, "Colony Origin Changed", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                });
    }

    private void updateLoss() {
        Constants.showDialog();
        Map<String, Object> map = new HashMap<>();
        String loss = binding.colonyLoss.getEditText().getText().toString();
        map.put("colonyLoss", loss);
        Constants.databaseReference().child(Constants.COLONY)
                .child(selectedID).updateChildren(map)
                .addOnFailureListener(e -> {
                    Constants.dismissDialog();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }).addOnSuccessListener(unused -> {
                    Constants.dismissDialog();

                    String time = Constants.getFormattedDate(new Date().getTime());
                    String event = "Change Colony Loss to " + loss;
                    HistoryModel history = new HistoryModel(selectedID, time, event);
                    Constants.databaseReference().child(Constants.ColonyAnalysis).child(selectedID).push().setValue(history);

                    ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
                    for (ColonyModel model : colonyList) {
                        if (model.getName().equals(selectedID)) {
                            model.setColonyLoss(loss);
                        }
                    }
                    Stash.put(Constants.COLONY, colonyList);
                    Toast.makeText(this, "Colony loss Changed", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                });
    }

    private void updateFeed() {
        String feed = "";
        for (int i = 0; i < binding.feedChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.feedChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                feed += chip.getText().toString() + ", ";
            }
        }

        Constants.showDialog();
        Map<String, Object> map = new HashMap<>();
        map.put("feed", feed);
        String finalFeed = feed;
        Constants.databaseReference().child(Constants.COLONY)
                .child(selectedID).updateChildren(map)
                .addOnFailureListener(e -> {
                    Constants.dismissDialog();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }).addOnSuccessListener(unused -> {
                    Constants.dismissDialog();

                    String time = Constants.getFormattedDate(new Date().getTime());
                    String event = "Change Feed to " + finalFeed;
                    HistoryModel history = new HistoryModel(selectedID, time, event);
                    Constants.databaseReference().child(Constants.ColonyAnalysis).child(selectedID).push().setValue(history);

                    ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
                    for (ColonyModel model : colonyList) {
                        if (model.getName().equals(selectedID)) {
                            model.setFeed(finalFeed);
                        }
                    }
                    Stash.put(Constants.COLONY, colonyList);
                    Toast.makeText(this, "Feed Changed", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                });
    }

    private void updateLocation() {
        Constants.showDialog();
        Map<String, Object> map = new HashMap<>();
        String location = binding.location.getEditText().getText().toString();
        map.put("location", location);
        Constants.databaseReference().child(Constants.COLONY)
                .child(selectedID).updateChildren(map)
                .addOnFailureListener(e -> {
                    Constants.dismissDialog();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }).addOnSuccessListener(unused -> {
                    Constants.dismissDialog();

                    String time = Constants.getFormattedDate(new Date().getTime());
                    String event = "Change Location to " + location;
                    HistoryModel history = new HistoryModel(selectedID, time, event);
                    Constants.databaseReference().child(Constants.ColonyAnalysis).child(selectedID).push().setValue(history);

                    ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
                    for (ColonyModel model : colonyList) {
                        if (model.getName().equals(selectedID)) {
                            model.setLocation(location);
                        }
                    }
                    Stash.put(Constants.COLONY, colonyList);
                    Toast.makeText(this, "Location Changed", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                });
    }

    private void updateUI(String selectedID) {

        ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);
        ColonyModel colonyModel = null;

        for (ColonyModel colony : colonyList) {
            if (colony.getName().equals(selectedID)) {
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