package com.moutamid.justbee.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.fxn.stash.Stash;
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
                String selectedID = (String) parent.getItemAtPosition(position);
                updateUI(selectedID);
            }
        });

        binding.change.setOnClickListener(v -> {

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

            } else if (Constants.types == Types.CHANGE_PEST) {


            } else if (Constants.types == Types.CHANGE_TREAT) {


            } else if (Constants.types == Types.CHANGE_NEW_QUEEN) {


            } else if (Constants.types == Types.CHANGE_BROOD_ADD) {


            } else if (Constants.types == Types.CHANGE_BROOD_REMOVE) {


            } else if (Constants.types == Types.CHANGE_HONEY_PRODUCTION) {


            } else if (Constants.types == Types.CHANGE_COLONY_ADD) {


            } else if (Constants.types == Types.CHANGE_COLONY_REMOVE) {


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