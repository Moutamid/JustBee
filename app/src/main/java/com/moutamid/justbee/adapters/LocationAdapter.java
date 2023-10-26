package com.moutamid.justbee.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.justbee.LocationClick;
import com.moutamid.justbee.R;
import com.moutamid.justbee.models.LocationModel;
import com.moutamid.justbee.models.QueenPerformance;

import java.util.ArrayList;
import java.util.Collection;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationVH> implements Filterable {
    Context context;
    public ArrayList<LocationModel> list;
    ArrayList<LocationModel> listAll;
    LocationClick locationClick;

    public LocationAdapter(Context context, ArrayList<LocationModel> list, LocationClick locationClick) {
        this.context = context;
        this.list = list;
        this.listAll = new ArrayList<>(list);
        this.locationClick = locationClick;
    }

    @NonNull
    @Override
    public LocationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LocationVH(LayoutInflater.from(context).inflate(R.layout.location_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LocationVH holder, int position) {
        LocationModel model = list.get(holder.getAdapterPosition());

        holder.name.setText(model.getName());

        holder.itemView.setOnClickListener(v -> locationClick.onClick(list.get(holder.getAdapterPosition())));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<LocationModel> filterList = new ArrayList<>();
            if (constraint.toString().isEmpty()){
                filterList.addAll(listAll);
            } else {
                for (LocationModel listModel : listAll){
                    if (listModel.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filterList.add(listModel);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((Collection<? extends LocationModel>) results.values);
            notifyDataSetChanged();
        }
    };

    public class LocationVH extends RecyclerView.ViewHolder {
        TextView name;

        public LocationVH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }

}
