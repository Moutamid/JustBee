package com.moutamid.justbee.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fxn.stash.Stash;
import com.moutamid.justbee.adapters.TableAdapter;
import com.moutamid.justbee.models.ColonyModel;
import com.moutamid.justbee.models.LocationModel;
import com.moutamid.justbee.models.QueenPerformance;
import com.moutamid.justbee.utilis.Constants;
import com.moutamid.justbee.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    TableAdapter adapter;
    ArrayList<QueenPerformance> table;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        Constants.initDialog(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);

        table = getTableData();
        int count = 0;
        for (QueenPerformance tt : table) {
            count += Integer.parseInt(tt.getColumn2());
        }
        binding.total.setText(count + "");
        binding.LocationRC.setHasFixedSize(false);
        binding.LocationRC.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new TableAdapter(requireContext(), table);
        binding.LocationRC.setAdapter(adapter);

        return binding.getRoot();
    }

    private ArrayList<QueenPerformance> getTableData() {
        ArrayList<QueenPerformance> list = new ArrayList<>();
        ArrayList<LocationModel> locationList = Stash.getArrayList(Constants.LOCATIONS_LIST, LocationModel.class);
        ArrayList<ColonyModel> coloniesList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);

        Map<String, Integer> locationColonyCount = new HashMap<>();

        // Initialize the count for each location to 0
        for (LocationModel location : locationList) {
            locationColonyCount.put(location.getName(), 0);
        }
        for (ColonyModel colony : coloniesList) {
            String colonyLocation = colony.getLocation();
            if (!colonyLocation.isEmpty()) {
                locationColonyCount.put(colonyLocation, locationColonyCount.get(colonyLocation) + 1);
            }
        }

        for (LocationModel location : locationList) {
            if (locationColonyCount.get(location.getName()) != 0) {
                list.add(new QueenPerformance(location.getName(), String.valueOf(locationColonyCount.get(location.getName()))));
            }
        }

        return list;
    }
}