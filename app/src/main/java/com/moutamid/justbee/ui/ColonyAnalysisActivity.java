package com.moutamid.justbee.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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

public class ColonyAnalysisActivity extends AppCompatActivity {
    ActivityColonyAnalysisBinding binding;
    ArrayList<QueenSourceModel> colonyList;
    ArrayList<QueenPerformance> list;

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
        Constants.showDialog();
        Constants.databaseReference().child(Constants.ColonyAnalysis)
                .get().addOnFailureListener(e -> {
                    Constants.dismissDialog();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }).addOnSuccessListener(dataSnapshot -> {
                    Constants.dismissDialog();
                    if (dataSnapshot.exists()) {
                        colonyList.clear();
                        for (DataSnapshot keys : dataSnapshot.getChildren()) {
                            QueenSourceModel model = new QueenSourceModel();
                            model.setQueenSource("Colony # " + keys.getKey());
                            model.setTitle1("Date");
                            model.setTitle2("Event");
                            list = new ArrayList<>();
                            for (DataSnapshot values : keys.getChildren()) {
                                HistoryModel history = values.getValue(HistoryModel.class);
                                list.add(new QueenPerformance(history.getDate(), history.getEvent()));
                                model.setList(list);
                            }
                            colonyList.add(model);
                        }

                        QueenSourceAdapter adapter = new QueenSourceAdapter(this, colonyList);
                        binding.colonyRC.setAdapter(adapter);

                    }
                });
    }
}