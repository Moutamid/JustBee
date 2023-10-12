package com.moutamid.justbee.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fxn.stash.Stash;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.moutamid.justbee.R;
import com.moutamid.justbee.utilis.Constants;
import com.moutamid.justbee.databinding.FragmentHomeBinding;
import com.moutamid.justbee.models.ColonyModel;
import com.moutamid.justbee.utilis.LocationAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);

        List<BarEntry> barEntries = new ArrayList<>();

        ArrayList<String> locationList = Stash.getArrayList(Constants.LOCATIONS_LIST, String.class);
        ArrayList<ColonyModel> coloniesList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);

        for (int i = 0; i < locationList.size(); i++) {
            // Count how many colonies are in the specific location
            int coloniesCount = 0;
            for (ColonyModel colony : coloniesList) {
                if (colony.getLocation().equals(locationList.get(i))) {
                    coloniesCount++;
                }
            }
            barEntries.add(new BarEntry(i, coloniesCount));
        }

        // Create a BarDataSet
        BarDataSet barDataSet = new BarDataSet(barEntries, "Colony Count");
        barDataSet.setColor(getResources().getColor(R.color.green));

        // Create a BarData and set the BarDataSet
        BarData barData = new BarData(barDataSet);

        // Set custom labels for the x-axis
        XAxis xAxis = binding.colonyBC.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return locationList.get((int) value);
            }
        });

        binding.colonyBC.setData(barData);
        binding.colonyBC.setHorizontalScrollBarEnabled(true);
//        xAxis.setScrollable(true);
        binding.colonyBC.setFitBars(true);
        binding.colonyBC.invalidate();

        return binding.getRoot();
    }
}