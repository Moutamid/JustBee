package com.moutamid.justbee.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fxn.stash.Stash;
import com.moutamid.justbee.R;
import com.moutamid.justbee.adapters.QueenSourceAdapter;
import com.moutamid.justbee.adapters.TableAdapter;
import com.moutamid.justbee.databinding.FragmentAnalyticsBinding;
import com.moutamid.justbee.models.ColonyModel;
import com.moutamid.justbee.models.QueenPerformance;
import com.moutamid.justbee.models.QueenSourceModel;
import com.moutamid.justbee.utilis.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnalyticsFragment extends Fragment {
    FragmentAnalyticsBinding binding;
    QueenSourceAdapter queenSourceAdapter;
    TableAdapter locationAdapter, pestAdapter, lossAdapter;
    public ArrayList<QueenSourceModel> queenSourceList;
    public ArrayList<QueenSourceModel> queenPestList;
    public ArrayList<QueenSourceModel> queenLossList;
    public ArrayList<QueenPerformance> locationList;
    public ArrayList<QueenPerformance> pestList;
    public ArrayList<QueenPerformance> lossList;

    public AnalyticsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAnalyticsBinding.inflate(getLayoutInflater(), container, false);

        binding.queenSourceRC.setHasFixedSize(false);
        binding.queenSourceRC.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.queenPestRC.setHasFixedSize(false);
        binding.queenPestRC.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.queenColonyLossRC.setHasFixedSize(false);
        binding.queenColonyLossRC.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.LocationRC.setHasFixedSize(false);
        binding.LocationRC.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.pestRC.setHasFixedSize(false);
        binding.pestRC.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.lossRC.setHasFixedSize(false);
        binding.lossRC.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.productionBtn.setOnClickListener(v -> {
            updateProBtn();

            queenSourceList = getQueenSourceData();
            queenSourceAdapter = new QueenSourceAdapter(requireContext(), queenSourceList);
            binding.queenSourceRC.setAdapter(queenSourceAdapter);
        });
        binding.lossBtn.setOnClickListener(v -> {
            updateLossBtn();

            queenLossList = getQueenLossData();
            queenSourceAdapter = new QueenSourceAdapter(requireContext(), queenLossList);
            binding.queenColonyLossRC.setAdapter(queenSourceAdapter);

        });
        binding.pestBtn.setOnClickListener(v -> {
            updatePestBtn();

            queenPestList = getQueenPestData();
            queenSourceAdapter = new QueenSourceAdapter(requireContext(), queenPestList);
            binding.queenPestRC.setAdapter(queenSourceAdapter);

        });

        queenSourceList = getQueenSourceData();
        queenSourceAdapter = new QueenSourceAdapter(requireContext(), queenSourceList);
        binding.queenSourceRC.setAdapter(queenSourceAdapter);

        locationList = getLocationProduction();
        locationAdapter = new TableAdapter(requireContext(), locationList);
        binding.LocationRC.setAdapter(locationAdapter);

        pestList = getPestProduction();
        pestAdapter = new TableAdapter(requireContext(), pestList);
        binding.pestRC.setAdapter(pestAdapter);

        lossList = getLossProduction();
        lossAdapter = new TableAdapter(requireContext(), lossList);
        binding.lossRC.setAdapter(lossAdapter);

        return binding.getRoot();
    }
    private void updateProBtn() {
        binding.productionBtn.setCardBackgroundColor(getResources().getColor(R.color.green));
        binding.pestBtn.setCardBackgroundColor(getResources().getColor(R.color.grey));
        binding.lossBtn.setCardBackgroundColor(getResources().getColor(R.color.grey));

        binding.producionText.setTextColor(getResources().getColor(R.color.background));
        binding.pestText.setTextColor(getResources().getColor(R.color.green));
        binding.lossText.setTextColor(getResources().getColor(R.color.green));

        binding.queenSourceRC.setVisibility(View.VISIBLE);
        binding.queenPestRC.setVisibility(View.GONE);
        binding.queenColonyLossRC.setVisibility(View.GONE);
    }
    private void updatePestBtn() {
        binding.productionBtn.setCardBackgroundColor(getResources().getColor(R.color.grey));
        binding.pestBtn.setCardBackgroundColor(getResources().getColor(R.color.green));
        binding.lossBtn.setCardBackgroundColor(getResources().getColor(R.color.grey));

        binding.producionText.setTextColor(getResources().getColor(R.color.green));
        binding.pestText.setTextColor(getResources().getColor(R.color.background));
        binding.lossText.setTextColor(getResources().getColor(R.color.green));

        binding.queenSourceRC.setVisibility(View.GONE);
        binding.queenPestRC.setVisibility(View.VISIBLE);
        binding.queenColonyLossRC.setVisibility(View.GONE);
    }
    private void updateLossBtn() {
        binding.productionBtn.setCardBackgroundColor(getResources().getColor(R.color.grey));
        binding.pestBtn.setCardBackgroundColor(getResources().getColor(R.color.grey));
        binding.lossBtn.setCardBackgroundColor(getResources().getColor(R.color.green));

        binding.producionText.setTextColor(getResources().getColor(R.color.green));
        binding.pestText.setTextColor(getResources().getColor(R.color.green));
        binding.lossText.setTextColor(getResources().getColor(R.color.background));

        binding.queenSourceRC.setVisibility(View.GONE);
        binding.queenPestRC.setVisibility(View.GONE);
        binding.queenColonyLossRC.setVisibility(View.VISIBLE);
    }
    private ArrayList<QueenPerformance> getLossProduction(){
        ArrayList<QueenPerformance> locationList  = new ArrayList<>();
        ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);

        Map<String, ArrayList<ColonyModel>> locationMap = new HashMap<>();

        // Iterate through the colonyList and group colonies by location
        for (ColonyModel colony : colonyList) {
            String loss = colony.getColonyLoss().isEmpty() ? "No Loss" : colony.getColonyLoss();
            if (!locationMap.containsKey(loss)) {
                locationMap.put(loss, new ArrayList<>());
            }
            locationMap.get(loss).add(colony);
        }

        for (Map.Entry<String, ArrayList<ColonyModel>> entry : locationMap.entrySet()) {
            String loss = entry.getKey();
            ArrayList<ColonyModel> coloniesInLocation = entry.getValue();

            locationList.add(new QueenPerformance(loss, String.valueOf(coloniesInLocation.size())));
        }


        return locationList;
    }
    private ArrayList<QueenPerformance> getPestProduction(){
        ArrayList<QueenPerformance> locationList  = new ArrayList<>();
        ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);

        Map<String, ArrayList<ColonyModel>> locationMap = new HashMap<>();

        // Iterate through the colonyList and group colonies by location
        for (ColonyModel colony : colonyList) {
            String location = colony.getPests().replaceAll("[,\\s]+$", "");
            location = location.isEmpty() ? "No Pest" : location;
            if (!locationMap.containsKey(location)) {
                locationMap.put(location, new ArrayList<>());
            }
            locationMap.get(location).add(colony);
        }

        for (Map.Entry<String, ArrayList<ColonyModel>> entry : locationMap.entrySet()) {
            String location = entry.getKey();
            ArrayList<ColonyModel> coloniesInLocation = entry.getValue();
            locationList.add(new QueenPerformance(location, String.valueOf(coloniesInLocation.size())));
        }


        return locationList;
    }
    private ArrayList<QueenPerformance> getLocationProduction(){
        ArrayList<QueenPerformance> locationList  = new ArrayList<>();
        ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);

        Map<String, ArrayList<ColonyModel>> locationMap = new HashMap<>();

        // Iterate through the colonyList and group colonies by location
        for (ColonyModel colony : colonyList) {
            String location = colony.getLocation();
            if (!locationMap.containsKey(location)) {
                locationMap.put(location, new ArrayList<>());
            }
            locationMap.get(location).add(colony);
        }

        for (Map.Entry<String, ArrayList<ColonyModel>> entry : locationMap.entrySet()) {
            String location = entry.getKey();
            ArrayList<ColonyModel> coloniesInLocation = entry.getValue();

            double totalHoneyProduction = coloniesInLocation.stream().mapToDouble(ColonyModel::getHoneyProduction).sum();
            String formattedTotal = String.format("%.2f", totalHoneyProduction);
            locationList.add(new QueenPerformance(location, formattedTotal));
        }

        double total =0;
        for (QueenPerformance loc : locationList) {
            total += Double.parseDouble(loc.getColumn2());
        }
        locationList.add(new QueenPerformance("Total", String.valueOf(total)));
        return locationList;
    }
    private ArrayList<QueenSourceModel> getQueenPestData() {
        ArrayList<QueenSourceModel> queenPestList = new ArrayList<>();
        ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);

        Map<String, ArrayList<ColonyModel>> queenSourceMap = new HashMap<>();

        // Iterate through the colonyList and group colonies by location
        for (ColonyModel colony : colonyList) {
            String queenSource = colony.getQueenOrigin();
            if (!queenSourceMap.containsKey(queenSource)) {
                queenSourceMap.put(queenSource, new ArrayList<>());
            }
            queenSourceMap.get(queenSource).add(colony);
        }

        for (Map.Entry<String, ArrayList<ColonyModel>> entry : queenSourceMap.entrySet()) {
            String location = entry.getKey();
            ArrayList<ColonyModel> coloniesInLocation = entry.getValue();
            ArrayList<QueenPerformance> subList = new ArrayList<>();

            QueenSourceModel model = new QueenSourceModel();
            model.setTitle1("Colony Number");
            model.setTitle2("Disease");
            model.setQueenSource("Queens from " + location);

            for (ColonyModel colony : coloniesInLocation) {
                String pest = colony.getPests().replaceAll("[,\\s]+$", "");
                String dd = pest.isEmpty() ? "No Pest" : pest;
                subList.add(new QueenPerformance(colony.getId(), dd));
                model.setList(subList);
            }

            queenPestList.add(model);
        }

        return queenPestList;
    }
    private ArrayList<QueenSourceModel> getQueenLossData() {
        ArrayList<QueenSourceModel> queenLossList = new ArrayList<>();
        ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);

        Map<String, ArrayList<ColonyModel>> queenSourceMap = new HashMap<>();

        // Iterate through the colonyList and group colonies by location
        for (ColonyModel colony : colonyList) {
            String queenSource = colony.getQueenOrigin();
            if (!queenSourceMap.containsKey(queenSource)) {
                queenSourceMap.put(queenSource, new ArrayList<>());
            }
            queenSourceMap.get(queenSource).add(colony);
        }

        for (Map.Entry<String, ArrayList<ColonyModel>> entry : queenSourceMap.entrySet()) {
            String location = entry.getKey();
            ArrayList<ColonyModel> coloniesInLocation = entry.getValue();
            ArrayList<QueenPerformance> subList = new ArrayList<>();

            QueenSourceModel model = new QueenSourceModel();
            model.setTitle1("Colony Number");
            model.setTitle2("Colony Loss");
            model.setQueenSource("Queens from " + location);

            for (ColonyModel colony : coloniesInLocation) {

                String loss;
                if (colony.getColonyLoss() != null){
                    loss = colony.getColonyLoss().isEmpty() ? "No Loss" : colony.getColonyLoss();
                } else {
                    loss = "No Loss";
                }

                subList.add(new QueenPerformance(colony.getId(), loss));
                model.setList(subList);
            }

            queenLossList.add(model);
        }

        return queenLossList;
    }
    private ArrayList<QueenSourceModel> getQueenSourceData() {
        ArrayList<QueenSourceModel> queenSourceList = new ArrayList<>();
        ArrayList<ColonyModel> colonyList = Stash.getArrayList(Constants.COLONY, ColonyModel.class);

        Map<String, ArrayList<ColonyModel>> queenSourceMap = new HashMap<>();

        // Iterate through the colonyList and group colonies by location
        for (ColonyModel colony : colonyList) {
            String queenSource = colony.getQueenOrigin();
            if (!queenSourceMap.containsKey(queenSource)) {
                queenSourceMap.put(queenSource, new ArrayList<>());
            }
            queenSourceMap.get(queenSource).add(colony);
        }

        for (Map.Entry<String, ArrayList<ColonyModel>> entry : queenSourceMap.entrySet()) {
            String location = entry.getKey();
            ArrayList<ColonyModel> coloniesInLocation = entry.getValue();
            ArrayList<QueenPerformance> subList = new ArrayList<>();

            QueenSourceModel model = new QueenSourceModel();
            model.setTitle1("Colony Number");
            model.setTitle2("Production (Kg)");
            model.setQueenSource("Queens from " + location);

            double totalHoneyProduction = 0;

            for (ColonyModel colony : coloniesInLocation) {
                subList.add(new QueenPerformance(colony.getId(), String.valueOf(colony.getHoneyProduction())));
                model.setList(subList);
            }

            for (QueenPerformance performance : subList){
                totalHoneyProduction += Double.parseDouble(performance.getColumn2());
            }

            subList.add(new QueenPerformance("Total", String.valueOf(totalHoneyProduction)));
            model.setList(subList);
            queenSourceList.add(model);
        }

        return queenSourceList;
    }

    @Override
    public void onResume() {
        super.onResume();
        Constants.initDialog(requireContext());
    }
}