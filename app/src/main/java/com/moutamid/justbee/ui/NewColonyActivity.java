package com.moutamid.justbee.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.material.chip.Chip;
import com.moutamid.justbee.models.HistoryModel;
import com.moutamid.justbee.utilis.Constants;
import com.moutamid.justbee.databinding.ActivityNewColonyBinding;
import com.moutamid.justbee.models.ColonyModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class NewColonyActivity extends AppCompatActivity {
    ActivityNewColonyBinding binding;
    ArrayList<ColonyModel> colonyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewColonyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Constants.initDialog(this);

        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
        binding.toolbar.title.setText("Add New Colony");

        colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);

        binding.add.setOnClickListener(v -> {
            Constants.showDialog();
            ColonyModel colonyModel = getColonyData();
            Constants.databaseReference().child(Constants.COLONY).child(colonyModel.getId()).setValue(colonyModel)
                    .addOnSuccessListener(unused -> {
                        String date = Constants.getFormattedDate(new Date().getTime());
                        String event = "Added " + colonyModel.getName() + " " + colonyModel.getQueenOrigin() + ", " + colonyModel.getColonyOrigin();
                        HistoryModel history = new HistoryModel(colonyModel.getId(), date, event);
                        Constants.databaseReference().child(Constants.ColonyAnalysis).child(colonyModel.getId()).push().setValue(history);
                        Constants.dismissDialog();
                        colonyList.add(colonyModel);
                        Stash.put(Constants.COLONY, colonyList);
                        Toast.makeText(this, "Colony Added", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }).addOnFailureListener(e -> {
                        Constants.dismissDialog();
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
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

        Random random = new Random();
        int min = 000000;
        int max = 999999;
        int random6DigitNumber = random.nextInt(max - min + 1) + min;
        colonyModel.setId(String.valueOf(random6DigitNumber));
        colonyModel.setName(binding.name.getEditText().getText().toString());
        colonyModel.setLocation("");
        colonyModel.setQueenOrigin(queenOrigin);
        colonyModel.setColonyOrigin(colonyOrigin);
        colonyModel.setBrood("");
        colonyModel.setTreatment("");
        colonyModel.setPests("");
        colonyModel.setFeed("");
        colonyModel.setColonyLoss("");
        colonyModel.setHoneyProduction(0);
        colonyModel.setDate(new Date().getTime());

        return colonyModel;
    }
}