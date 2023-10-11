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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.moutamid.justbee.Constants;
import com.moutamid.justbee.R;
import com.moutamid.justbee.databinding.ActivityManageLocationsBinding;

import java.util.ArrayList;

public class ManageLocationsActivity extends AppCompatActivity {
    ActivityManageLocationsBinding binding;
    ArrayList<String> locations;
    LocationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageLocationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
        binding.toolbar.title.setText("Manage Locations");

        locations = Stash.getArrayList(Constants.LOCATIONS_LIST, String.class);

        binding.counter.setText("Total Locations : " + locations.size() + "/40");

        binding.locRC.setHasFixedSize(false);
        binding.locRC.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LocationAdapter(locations);
        binding.locRC.setAdapter(adapter);

        binding.add.setOnClickListener(v -> {
            if (locations.size() < 40)
                showDialog();
            else
                Toast.makeText(this, "You can only add locations up-to 40", Toast.LENGTH_SHORT).show();
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
                locations.add(name.getEditText().getText().toString());
                update();
                Stash.put(Constants.LOCATIONS_LIST, locations);
            }
        });

    }

    private void update() {
        binding.counter.setText("Total Locations : " + locations.size() + "/40");
        adapter.notifyDataSetChanged();
    }

    private class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationVH> {
        ArrayList<String> locations;

        public LocationAdapter(ArrayList<String> locations) {
            this.locations = locations;
        }

        @NonNull
        @Override
        public LocationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new LocationVH(LayoutInflater.from(ManageLocationsActivity.this).inflate(R.layout.location_card, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull LocationVH holder, int position) {
            String name = locations.get(holder.getAdapterPosition());
            holder.name.setText(name);

            holder.remove.setOnClickListener(v -> {
                locations.remove(name);
                ManageLocationsActivity.this.locations.remove(name);
                Stash.put(Constants.LOCATIONS_LIST, locations);
                notifyDataSetChanged();
                ManageLocationsActivity.this.update();
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