package com.moutamid.justbee.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.justbee.models.HistoryModel;
import com.moutamid.justbee.models.LocationModel;
import com.moutamid.justbee.utilis.Constants;
import com.moutamid.justbee.R;
import com.moutamid.justbee.databinding.ActivityAddColonyBinding;
import com.moutamid.justbee.models.ColonyModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class AddColonyActivity extends AppCompatActivity {
    ActivityAddColonyBinding binding;
    ArrayList<ColonyModel> colonyList;
    ColonyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddColonyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Constants.initDialog(this);

        colonyList = new ArrayList<>();
        binding.counter.setText("Total Colony : " + colonyList.size());

        binding.add.setOnClickListener(v -> startActivity(new Intent(this, NewColonyActivity.class)));

        binding.colonyRC.setHasFixedSize(false);
        binding.colonyRC.setLayoutManager(new LinearLayoutManager(this));

        Constants.showDialog();
        Constants.databaseReference().child(Constants.COLONY)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Constants.dismissDialog();
                                colonyList.clear();
                                if (snapshot.exists()) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                        ColonyModel model = dataSnapshot.getValue(ColonyModel.class);
                                        colonyList.add(model);
                                    }
                                    colonyList.sort(Comparator.comparing(ColonyModel::getName));
                                }
                                adapter = new ColonyAdapter(colonyList);
                                binding.colonyRC.setAdapter(adapter);
                                Stash.put(Constants.COLONY, colonyList);
                                binding.counter.setText("Total Colony : " + colonyList.size());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Constants.dismissDialog();
                                Toast.makeText(AddColonyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
        binding.toolbar.title.setText("Colony List");

    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    private class ColonyAdapter extends RecyclerView.Adapter<ColonyAdapter.ColonyVH> {
        ArrayList<ColonyModel> list;

        public ColonyAdapter(ArrayList<ColonyModel> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public ColonyAdapter.ColonyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ColonyAdapter.ColonyVH(LayoutInflater.from(AddColonyActivity.this).inflate(R.layout.colony_card, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ColonyAdapter.ColonyVH holder, int position) {
            ColonyModel model = list.get(holder.getAdapterPosition());
            holder.name.setText(model.getName());
            holder.location.setText(model.getLocation());
            holder.origin.setText(model.getColonyOrigin());
            holder.ID.setText(model.getId());

            String date = Constants.getFormattedDate(model.getDate());
            holder.date.setText(date);

            holder.delete.setOnClickListener(v -> {
                Constants.databaseReference().child(Constants.COLONY).child(model.getId())
                        .removeValue().addOnFailureListener(e -> {
                            Toast.makeText(AddColonyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }).addOnSuccessListener(unused -> {
                            String time = Constants.getFormattedDate(new Date().getTime());
                            String event = "Removed " + model.getName() + ", " + model.getQueenOrigin() + ", " + model.getColonyOrigin();
                            HistoryModel history = new HistoryModel(model.getId(), time, event);
                            Constants.databaseReference().child(Constants.ColonyAnalysis).child(model.getId()).push().setValue(history);
                            Toast.makeText(AddColonyActivity.this, "Colony Removed", Toast.LENGTH_SHORT).show();
                        });
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        private class ColonyVH extends RecyclerView.ViewHolder {
            TextView name, location, origin, date, ID;
            MaterialCardView delete;

            public ColonyVH(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                location = itemView.findViewById(R.id.location);
                origin = itemView.findViewById(R.id.origin);
                date = itemView.findViewById(R.id.date);
                delete = itemView.findViewById(R.id.delete);
                ID = itemView.findViewById(R.id.ID);
            }
        }

    }

}