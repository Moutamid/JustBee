package com.moutamid.justbee.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.firebase.database.DataSnapshot;
import com.moutamid.justbee.R;
import com.moutamid.justbee.adapters.QueenSourceAdapter;
import com.moutamid.justbee.databinding.ActivityColonyAnalysisBinding;
import com.moutamid.justbee.models.ColonyModel;
import com.moutamid.justbee.models.HistoryModel;
import com.moutamid.justbee.models.QueenPerformance;
import com.moutamid.justbee.models.QueenSourceModel;
import com.moutamid.justbee.utilis.Constants;

import java.util.ArrayList;
import java.util.List;

public class ColonyAnalysisActivity extends AppCompatActivity {
    ActivityColonyAnalysisBinding binding;
    ArrayList<QueenSourceModel> colonyList;
    ArrayList<QueenPerformance> list;
    ArrayAdapter<String> idList;
    String selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityColonyAnalysisBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Constants.initDialog(this);
        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
        binding.toolbar.title.setText("Colony Analysis");

        colonyList = new ArrayList<>();
        list = new ArrayList<>();

        binding.colonyRC.setHasFixedSize(false);
        binding.colonyRC.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<ColonyModel> colonies = Stash.getArrayList(Constants.COLONY, ColonyModel.class);

        List<String> colonyID = new ArrayList<>();
        for (ColonyModel model : colonies) {
            colonyID.add(model.getName());
        }
        idList = new ArrayAdapter<>(ColonyAnalysisActivity.this, android.R.layout.simple_spinner_dropdown_item, colonyID);
        binding.colonyIdLIst.setAdapter(idList);

        binding.search.setOnClickListener(v ->  getData(binding.colonyID.getEditText().getText().toString()));

        binding.colonyIdLIst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedID = (String) parent.getItemAtPosition(position);

                getData(selectedID);
            }
        });
    }

    private void getData(String selectedID) {
        Constants.showDialog();
        Constants.databaseReference().child(Constants.ColonyAnalysis).child(selectedID)
                .get().addOnFailureListener(e -> {
                    Constants.dismissDialog();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }).addOnSuccessListener(dataSnapshot -> {
                    Constants.dismissDialog();
                    if (dataSnapshot.exists()) {
                        colonyList.clear();
                        QueenSourceModel model = new QueenSourceModel();
                        model.setQueenSource("Colony # " + selectedID);
                        model.setTitle1("Date");
                        model.setTitle2("Event");
                        list = new ArrayList<>();
                        for (DataSnapshot values : dataSnapshot.getChildren()) {
                            HistoryModel history = values.getValue(HistoryModel.class);
                            list.add(new QueenPerformance(history.getDate(), history.getEvent()));
                            model.setList(list);
                        }
                        colonyList.add(model);

                        QueenSourceAdapter adapter = new QueenSourceAdapter(this, colonyList);
                        binding.colonyRC.setAdapter(adapter);

                    }
                });
    }
}