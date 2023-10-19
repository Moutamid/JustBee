package com.moutamid.justbee.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.justbee.models.LocationModel;
import com.moutamid.justbee.utilis.Constants;
import com.moutamid.justbee.R;
import com.moutamid.justbee.databinding.ActivityManageLocationsBinding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;

public class ManageLocationsActivity extends AppCompatActivity {
    ActivityManageLocationsBinding binding;
    ArrayList<LocationModel> locations;
    LocationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageLocationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Constants.initDialog(this);

        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
        binding.toolbar.title.setText("Manage Locations");

        locations = new ArrayList<>();

        binding.counter.setText("Total Locations : " + locations.size() + "/40");

        binding.locRC.setHasFixedSize(false);
        binding.locRC.setLayoutManager(new LinearLayoutManager(this));

        Constants.showDialog();
        Constants.databaseReference().child(Constants.LOCATIONS_LIST)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            locations.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                LocationModel model = dataSnapshot.getValue(LocationModel.class);
                                locations.add(model);
                            }

                            locations.sort(Comparator.comparing(LocationModel::getName));
                            binding.counter.setText("Total Locations : " + locations.size() + "/40");
                            adapter = new LocationAdapter(locations);
                            binding.locRC.setAdapter(adapter);
                            Stash.put(Constants.LOCATIONS_LIST, locations);
                        }
                        Constants.dismissDialog();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Constants.dismissDialog();
                        Toast.makeText(ManageLocationsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        binding.add.setOnClickListener(v -> {
            showDialog();
        });

    }

    private void showDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_location);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        MaterialButton add = dialog.findViewById(R.id.add);
        TextInputLayout name = dialog.findViewById(R.id.name);

        add.setOnClickListener(v -> {
            if (name.getEditText().getText().toString().isEmpty()) {
                name.setErrorEnabled(true);
                name.setError("Name is Empty");
            } else {
                dialog.dismiss();
                Constants.showDialog();
                String id = UUID.randomUUID().toString();
                LocationModel loc = new LocationModel(id, name.getEditText().getText().toString());
                Constants.databaseReference().child(Constants.LOCATIONS_LIST).child(id).setValue(loc)
                                .addOnSuccessListener(unused -> {
                                    Constants.dismissDialog();
                                    Toast.makeText(this, "Location Added", Toast.LENGTH_SHORT).show();
                                }).addOnFailureListener(e -> {
                            Constants.dismissDialog();
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

    }

    private class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationVH> {
        ArrayList<LocationModel> locations;

        public LocationAdapter(ArrayList<LocationModel> locations) {
            this.locations = locations;
        }

        @NonNull
        @Override
        public LocationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new LocationVH(LayoutInflater.from(ManageLocationsActivity.this).inflate(R.layout.location_card, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull LocationVH holder, int position) {
            LocationModel name = locations.get(holder.getAdapterPosition());
            holder.name.setText(name.getName());

            holder.remove.setOnClickListener(v -> {
                Constants.databaseReference().child(Constants.LOCATIONS_LIST).child(name.getID()).removeValue()
                        .addOnFailureListener(e -> {
                            Toast.makeText(ManageLocationsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }).addOnSuccessListener(unused -> {
                            Toast.makeText(ManageLocationsActivity.this, "Location Removed", Toast.LENGTH_SHORT).show();
                        });
            });
        }

        @Override
        public int getItemCount() {
            return locations.size();
        }

        private class LocationVH extends RecyclerView.ViewHolder {
            TextView name;
            ImageView remove;

            public LocationVH(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                remove = itemView.findViewById(R.id.remove);
            }
        }

    }

}