package com.moutamid.justbee.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.moutamid.justbee.R;
import com.moutamid.justbee.databinding.ActivityChangeIndividualBinding;

public class ChangeIndividualActivity extends AppCompatActivity {
    ActivityChangeIndividualBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeIndividualBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}