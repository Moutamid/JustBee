package com.moutamid.justbee.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.moutamid.justbee.utilis.Constants;
import com.moutamid.justbee.utilis.Types;
import com.moutamid.justbee.databinding.ActivityChangeAllBinding;

public class ChangeAllActivity extends AppCompatActivity {
    ActivityChangeAllBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeAllBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
        binding.toolbar.title.setText("Data Entry");

        if (Constants.types == Types.CHANGE_LOCATION_ALL){
            locationUI();
        } else if (Constants.types == Types.CHANGE_FEED_ALL){
            feedUI();
        } else if (Constants.types == Types.CHANGE_TREAT_ALL){
            treatUI();
        }

    }

    private void treatUI() {

    }

    private void feedUI() {

    }

    private void locationUI() {

    }
}