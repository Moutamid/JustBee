package com.moutamid.justbee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.moutamid.justbee.databinding.ActivityMainBinding;
import com.moutamid.justbee.fragments.AdminFragment;
import com.moutamid.justbee.fragments.AnalyticsFragment;
import com.moutamid.justbee.fragments.DataEntryFragment;
import com.moutamid.justbee.fragments.HomeFragment;
import com.moutamid.justbee.models.ColonyModel;
import com.moutamid.justbee.ui.AddColonyActivity;
import com.moutamid.justbee.utilis.Constants;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding binding;
    ArrayList<ColonyModel> colonyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Constants.checkApp(this);
        Constants.initDialog(this);

        binding.bottomNav.setItemActiveIndicatorColor(ColorStateList.valueOf(getResources().getColor(R.color.brown)));
        binding.bottomNav.setOnNavigationItemSelectedListener(this);

        colonyList = new ArrayList<>();

        Constants.showDialog();
        Constants.databaseReference().child(Constants.COLONY)
                .get()
                .addOnSuccessListener(dataSnapshot -> {
                    Constants.dismissDialog();
                    if (dataSnapshot.exists()) {
                        colonyList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            ColonyModel model = snapshot.getValue(ColonyModel.class);
                            colonyList.add(model);
                        }
                        Stash.put(Constants.COLONY, colonyList);
                        binding.bottomNav.setSelectedItemId(R.id.nav_home);
                    } else {
                        binding.bottomNav.setSelectedItemId(R.id.nav_home);
                    }
                }).addOnFailureListener(error -> {
                    Constants.dismissDialog();
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();
            return true;
        } else if (item.getItemId() == R.id.nav_data) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new DataEntryFragment()).commit();
            return true;
        } else if (item.getItemId() == R.id.nav_analytics) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new AnalyticsFragment()).commit();
            return true;
        } else if (item.getItemId() == R.id.nav_admin) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new AdminFragment()).commit();
            return true;
        }
        return false;
    }
}