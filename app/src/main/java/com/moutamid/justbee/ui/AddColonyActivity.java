package com.moutamid.justbee.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxn.stash.Stash;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.moutamid.justbee.Constants;
import com.moutamid.justbee.R;
import com.moutamid.justbee.databinding.ActivityAddColonyBinding;
import com.moutamid.justbee.models.ColonyModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AddColonyActivity extends AppCompatActivity {
    ActivityAddColonyBinding binding;
    ArrayList<ColonyModel> colonyList;
    ArrayAdapter<String> locList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddColonyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
        binding.toolbar.title.setText("Colony List");

        binding.add.setOnClickListener(v -> showDialog());

    }

    private void showDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_colony);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        MaterialButton add = dialog.findViewById(R.id.add);
        TextInputLayout name = dialog.findViewById(R.id.name);
        TextInputLayout location = dialog.findViewById(R.id.location);
        AutoCompleteTextView locationList = dialog.findViewById(R.id.locationList);
        ChipGroup colonyOriginChipGroup = dialog.findViewById(R.id.colonyOriginChipGroup);

        List<String> loc = Stash.getArrayList(Constants.LOCATIONS_LIST, String.class);
        locList = new ArrayAdapter<>(AddColonyActivity.this, android.R.layout.simple_spinner_dropdown_item, loc);
        locationList.setAdapter(locList);

        add.setOnClickListener(v -> {
            String diseases = "";
            for (int i = 0; i < colonyOriginChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) colonyOriginChipGroup.getChildAt(i);
                if (chip.isChecked()) {
                    diseases = chip.getText().toString();
                }
            }
           ColonyModel colonyModel = new ColonyModel(UUID.randomUUID().toString(),
                   name.getEditText().getText().toString(),
                   location.getEditText().getText().toString(),
                   diseases,
                   new Date().getTime()
           );
            colonyList.add(colonyModel);
            Stash.put(Constants.COLONY, colonyList);
            dialog.dismiss();
        });

    }

    private void update() {
        binding.counter.setText("Total Colony : " + colonyList.size());
      // adapter.notifyDataSetChanged();
    }
    private class ColonyAdapter extends RecyclerView.Adapter<ColonyAdapter.ColonyVH> {
        ArrayList<ColonyModel> list;

        public ColonyAdapter(ArrayList<ColonyModel> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public ColonyAdapter.ColonyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ColonyAdapter.ColonyVH(LayoutInflater.from(AddColonyActivity.this).inflate(R.layout.location_card, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ColonyAdapter.ColonyVH holder, int position) {
            ColonyModel model = list.get(holder.getAdapterPosition());
            holder.name.setText(model.getName());

            holder.remove.setOnClickListener(v -> {

            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        private class ColonyVH extends RecyclerView.ViewHolder {
            TextView name;
            ImageView remove;

            public ColonyVH(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                remove = itemView.findViewById(R.id.remove);
            }
        }

    }

}